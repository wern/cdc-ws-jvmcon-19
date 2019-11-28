package de.mathema.tutorials.cdc.greetings.consumer;

import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactFolder;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.model.RequestResponsePact;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "GreetingProvider")
@PactFolder("pacts")
public class GreetingConsumerTest {

	@Pact(provider="GreetingProvider", consumer = "GreetingConsumer")
	public RequestResponsePact createPact(PactDslWithProvider builder) {
		return builder
				.uponReceiving("Abfrage aller Gruesse mit vorhandenen Daten")
				.path("/greetings")
				.method("GET")
				.willRespondWith()
				.status(200)
				.body(new PactDslJsonBody()
						.array("greetings")
							.object()
								.stringValue("type", "formal")
								.stringValue("phrase", "Good morning")
								.closeObject()
							.object()
								.stringValue("type", "casual")
								.stringValue("phrase", "Hey")
								.closeObject()
						)
				
				.uponReceiving("Abfrage eines existierenden Grusses")
				.path("/greetings/casual")
				.method("GET")
				.willRespondWith()
				.status(200)
				.body(new PactDslJsonBody()
						.object("greeting")
							.stringValue("type", "casual")
							.stringValue("phrase", "Hey")
							.closeObject()
						)
				
				.uponReceiving("Abfrage eines nicht existierenden Grusses")
				.path("/greetings/formal")
				.method("GET")
				.willRespondWith()
				.status(404)

				.uponReceiving("Speichern eines neuen Grusses")
				.path("/greetings/formal")
				.method("PUT")
				.headers("Content-type", ContentType.APPLICATION_JSON.toString())
				.body(new PactDslJsonBody()
						.object("greeting")
							.stringValue("type", "formal")
							.stringValue("phrase", "Good morning")
							.closeObject()
						)
				.willRespondWith()
				.status(200)

				.uponReceiving("Loeschen eines Grusses")
				.path("/greetings/formal")
				.method("DELETE")
				.willRespondWith()
				.status(200)
				
				.toPact();		
	}
	
	@Test
	void testAbfrageAllerGruesse(MockServer mockServer) throws Exception {
		HttpResponse response = Request.Get(mockServer.getUrl()+"/greetings").execute().returnResponse();
		Assertions.assertEquals(200, response.getStatusLine().getStatusCode());
		
		response = Request.Get(mockServer.getUrl()+"/greetings/casual").execute().returnResponse();
		Assertions.assertEquals(200, response.getStatusLine().getStatusCode());
		
		response = Request.Get(mockServer.getUrl()+"/greetings/formal").execute().returnResponse();
		Assertions.assertEquals(404, response.getStatusLine().getStatusCode());

		response = Request.Put(mockServer.getUrl()+"/greetings/formal").bodyString("{\"greeting\":{\"type\":\"formal\",\"phrase\":\"Good morning\"}}", ContentType.APPLICATION_JSON).execute().returnResponse();
		Assertions.assertEquals(200, response.getStatusLine().getStatusCode());

		response = Request.Delete(mockServer.getUrl()+"/greetings/formal").execute().returnResponse();
		Assertions.assertEquals(200, response.getStatusLine().getStatusCode());
	}
}
