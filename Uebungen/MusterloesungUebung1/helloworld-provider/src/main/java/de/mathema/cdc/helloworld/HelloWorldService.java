package de.mathema.cdc.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldService {

    @GetMapping("/")
    public Response getHelloWorld() {
        return new Response();
    }
}
