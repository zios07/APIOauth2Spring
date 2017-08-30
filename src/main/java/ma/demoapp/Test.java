package ma.demoapp;

import java.util.ArrayList;
import java.util.List;

import ma.demoapp.entities.Role;
import ma.demoapp.entities.User;
import ma.demoapp.metier.UserService;

public class Test {

	public static void main(String[] args) {
		
		UserService service = new UserService();
		
		User user = new User();
		
		user.setUsername("zios");
		user.setPassword("zios");
		
		List<Role> roles = new ArrayList<>();
		
		Role role1 = new Role();
		Role role2 = new Role();
		
		role1.setRole("ADMIN");
		role2.setRole("TRUSTED_CLIENT");
		
		roles.add(role1);
		roles.add(role2);
		
		user.setRoles(roles);
		
		service.addUser(user);
		
		
	}

}
