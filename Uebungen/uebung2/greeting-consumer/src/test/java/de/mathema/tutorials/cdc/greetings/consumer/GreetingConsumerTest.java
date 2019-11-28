package de.mathema.tutorials.cdc.greetings.consumer;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GreetingConsumerTest {

	public RequestResponsePact createPact(PactDslWithProvider builder) {
		return null;
	}
	
	@Test
	void testAbfrageAllerGruesse(MockServer mockServer) throws Exception {
		HttpResponse response = Request.Get(mockServer.getUrl()+"/greetings").execute().returnResponse();
		Assertions.assertEquals(200, response.getStatusLine().getStatusCode());
	}
}
