package ma.demoapp.metier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ma.demoapp.entities.User;

@Service
public class UserService implements UserDetailsService{
	
	@Autowired
	UserRepository userRepository;
	
	public User addUser(User user) {
		userRepository.save(user);
		return user;
	}
	
	public User getUser(long id) {
		return userRepository.findOne(id);
	}
	
	public User getUser(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if(user!=null) {
			return new UserDetails() {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = -5354150305192281097L;

				@Override
				public boolean isEnabled() {
					return true;
				}
				
				@Override
				public boolean isCredentialsNonExpired() {
					return true;
				}
				
				@Override
				public boolean isAccountNonLocked() {
					return true;
				}
				
				@Override
				public boolean isAccountNonExpired() {
					return true;
				}
				
				@Override
				public String getUsername() {
					return user.getUsername();
				}
				
				@Override
				public String getPassword() {
					return user.getPassword();
				}
				
				@Override
				public Collection<? extends GrantedAuthority> getAuthorities() {
					List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
					authorities.add(new SimpleGrantedAuthority("ROLE_TRUSTED_CLIENT"));
					authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
					authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
					return authorities;
				}
			};
		}
		throw new UsernameNotFoundException(username+" Not found");
	}

	public List<User> getUsers() {
		return userRepository.findAll();
	}
}
