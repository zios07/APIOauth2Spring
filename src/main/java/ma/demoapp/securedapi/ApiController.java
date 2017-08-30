package ma.demoapp.securedapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ma.demoapp.entities.User;
import ma.demoapp.metier.UserService;

@RestController
public class ApiController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping(value="/")
	public String publicArea() {
		return "Public";
	}
	
	@GetMapping(value="private")
	public String privateArea() {
		return "Private";
	}
	
	@RequestMapping(method = RequestMethod.POST,value = "/users/add" , produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public User addUser(@RequestBody User user) {
		System.out.println(user.getUsername()+" "+user.getPassword());
		userService.addUser(user);
		return user;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/users/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public User getUser(@PathVariable String username) {
		return userService.getUser(username);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/users", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<User> getUser() {
		return userService.getUsers();
	}
}
