package ma.securedapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
	
	@GetMapping(value="/")
	public String publicArea() {
		return "Public";
	}
	
	@GetMapping(value="private")
	public String privateArea() {
		return "Private";
	}
	
}
