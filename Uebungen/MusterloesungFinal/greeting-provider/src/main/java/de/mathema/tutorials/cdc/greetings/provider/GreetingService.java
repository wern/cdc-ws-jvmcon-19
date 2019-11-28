package de.mathema.tutorials.cdc.greetings.provider;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
        return Collections.singletonMap("greetings", greetings.values());
    }

    @GetMapping("/greetings/{type}")
    public Greeting getGreeting(@PathVariable("type") String type) {
        if (greetings.containsKey(type)) {
            return greetings.get(type);
        } else {
            throw new NoSuchGreetingException();
        }
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