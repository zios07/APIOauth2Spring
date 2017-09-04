package ma.demoapp.securedapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

import ma.demoapp.metier.UserService;

public class Oauh2Config {

	@Configuration
	@EnableAuthorizationServer
	public static class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

		@Autowired
		private AuthenticationManager authenticationManager;

		@Autowired
		private UserService userService;

		@Override
		public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
			security.checkTokenAccess("isAuthenticated()");
		}

		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			clients.inMemory()
				.withClient("client")
				.secret("secret")
				.scopes("read", "write", "trust")
				.accessTokenValiditySeconds(5000)
				.authorizedGrantTypes("authorization_code")
				.authorities("ROLE_ADMIN","ROLE_TRUSTED_CLIENT","ROLE_USER");
		}

		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			endpoints.userDetailsService(userService)
				.authenticationManager(authenticationManager);
		}

	}

	@Configuration
	@EnableResourceServer
	public static class ResourceServer extends ResourceServerConfigurerAdapter {
		@Override
		public void configure(HttpSecurity http) throws Exception {
			
			http.authorizeRequests()
				.antMatchers("/private")
				.hasRole("ADMIN")
				.and()
				.formLogin()
				.loginPage("/login");
			
//			http.authorizeRequests()
//				.antMatchers("/")
//				.permitAll()
//				.and()
//				.authorizeRequests()
//				.antMatchers("/private")
//				.authenticated()
//				.and()
//				.authorizeRequests()
//				.antMatchers("/users/**")
//				.authenticated();
		}
	}
	
	@Configuration
	@EnableWebSecurity
	public static class WebSecurity extends WebSecurityConfigurerAdapter {
		 
		public static final String REDIRECT_URI = "http://localhost:8080/oauth/token?client_id=client&secret=secret&"
				+ "grant_type=authorization_code&redirect_uri=localhost:8080&code=xx";
		public static final String SUCCESS_URI = "/oauth/authorize?client_id=client&secret=secret&response_type=code&"
				+ "redirect_uri=";
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
            .formLogin()
            .loginPage("/login")
            .usernameParameter("username")
            .passwordParameter("password")
            .successForwardUrl(SUCCESS_URI+REDIRECT_URI)
            .failureUrl("/login?error")
            .permitAll()
            .and()
            .authorizeRequests()
            .anyRequest().authenticated()
		    .and()
		    .csrf().disable()
		    .logout().logoutSuccessUrl("/login").permitAll();
		}
		
	}

}
