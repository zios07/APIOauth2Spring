package ma.securedapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

public class Oauh2Config {

	@Configuration
	@EnableAuthorizationServer
	public static class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

		@Autowired
		private AuthenticationManager authenticationManager;

		@Override
		public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
			security.checkTokenAccess("isAuthenticated()");
		}

		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			clients.inMemory()
					.withClient("my-trusted-client")
					.secret("secret")
					.scopes("read", "write", "trust")
					.accessTokenValiditySeconds(5000)
					.authorizedGrantTypes("authorization_code")
					.authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
					.resourceIds("oauth2-resource");
		}

		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			endpoints.authenticationManager(authenticationManager);
		}

	}

	@Configuration
	@EnableResourceServer
	public static class ResourceServer extends ResourceServerConfigurerAdapter {
		@Override
		public void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
				.antMatchers("/")
				.permitAll()
				.antMatchers("/private")
				.authenticated();
		}
	}

	public static class WebSecurity extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
				.antMatchers("/")
				.permitAll()
				.and()
				.authorizeRequests()
				.anyRequest()
				.hasAnyRole("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT");
		}
	}

}
