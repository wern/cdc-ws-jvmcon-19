package de.mathema.tutorials.cdc.greetings.provider;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No matching greeting found.")
public class NoSuchGreetingException extends RuntimeException {

}
