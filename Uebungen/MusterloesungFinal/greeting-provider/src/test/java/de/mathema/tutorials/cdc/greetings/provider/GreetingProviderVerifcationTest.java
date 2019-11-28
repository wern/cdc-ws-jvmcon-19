package de.mathema.tutorials.cdc.greetings.provider;

import java.net.URL;

import au.com.dius.pact.provider.junit.loader.PactBroker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactUrl;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import de.mathema.tutorials.cdc.greetings.provider.Greeting;
import de.mathema.tutorials.cdc.greetings.provider.GreetingService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Provider("GreetingProvider")
@PactUrl(urls = "pacts/greetingconsumer-greetingprovider.json")
//@PactUrl(urls = "http://localhost:9080/pacts/provider/GreetingProvider/consumer/GreetingConsumer/latest")
//@PactBroker(host="localhost", port="9080")
public class GreetingProviderVerifcationTest {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    GreetingService greetingService;

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        System.setProperty("pact.provider.version", "1.0.0");
        System.setProperty("pact.verifier.publishResults", "true");
        context.verifyInteraction();
    }

    @BeforeEach
    void before(PactVerificationContext context) throws Exception {
        context.setTarget(HttpTestTarget.fromUrl(new URL("http", "localhost", randomServerPort, "")));
        // or something like
        // context.setTarget(new HttpTestTarget("localhost", myProviderPort, "/"));

        // reset state
        greetingService.reset();
    }

    @State({"Es gibt einen casual Gruss"})
    void setUpEinenCasualGruss() {
        greetingService.saveGreeting("casual", new Greeting("casual", "Hey"));
    }

    @State({"Es gibt einen formal Gruss"})
    void setUpEinenFormalGruss() {
        greetingService.saveGreeting("formal", new Greeting("formal", "How do you do"));
    }

    @State({"Es gibt keinen formal Gruss"})
    void setupKeinenFormalGruss() {
        // state already reseted
    }

    @State({"Es gibt keine Gruesse"})
    void setupKeineGruesse() {
        // state already reseted
    }

    @State({"Es gibt zwei Gruesse"})
    void setupZweiGruesse() {
        greetingService.saveGreeting("casual", new Greeting("casual", "Hey"));
        greetingService.saveGreeting("formal", new Greeting("formal", "Good morning"));
    }
}