package de.mathema.tutorials.cdc.greetings.provider;

import java.util.*;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class GreetingService {

    private static final Map<String, Greeting> greetings = new HashMap<>();

    @GetMapping("/greetings")
    public Map<String, Collection<Greeting>> listGreetings() {
        return Collections.singletonMap("greetings", Arrays.asList(new Greeting("servant", "Yes sir"),
                                                            new Greeting("military", "Sir, yes, sir!")));
    }

    @GetMapping("/greetings/{type}")
    public Greeting getGreeting(@PathVariable("type") String type) {
        return new Greeting("robin", "Gooooood moooooorning Vietnam!");
    }

    @PutMapping("/greetings/{type}")
    public void saveGreeting(@PathVariable("type") String type, @RequestBody Greeting greeting) {
        greetings.put(type, greeting);
    }

    @DeleteMapping("/greetings/{type}")
    public void deleteGreeting(@PathVariable("type") String type) {
        greetings.remove(type);
    }

	public void reset() {
    	greetings.clear();
	}
}