package de.mathema.cdc.helloworld;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.loader.PactUrl;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URL;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@PactUrl(urls = "pacts/HelloWorldConsumer-HelloWorldProvider.json")
@Provider("HelloWorldProvider")
class ProviderVerification {

	@LocalServerPort
	int randomServerPort;

	@TestTemplate
	@ExtendWith(PactVerificationInvocationContextProvider.class)
	void pactVerificationTestTemplate(PactVerificationContext context) {
		context.verifyInteraction();
	}

	@BeforeEach
	void before(PactVerificationContext context) throws Exception {
		context.setTarget(HttpTestTarget.fromUrl(new URL("http", "localhost", randomServerPort, "")));
	}
}
