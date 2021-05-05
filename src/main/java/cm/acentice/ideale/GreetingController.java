package cm.acentice.ideale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	
	@GetMapping("/greet/{user}")
	public String greet(@PathVariable("user") String user) {
		String prefix = System.getenv().getOrDefault("GREETING_PREFIX", "Hi");
		if (prefix == null) {
			prefix = "Hello!";
		}
		
		return "hello world";
	}
}
