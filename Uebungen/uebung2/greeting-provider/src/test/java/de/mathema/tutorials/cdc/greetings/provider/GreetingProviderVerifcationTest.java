package de.mathema.tutorials.cdc.greetings.provider;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Provider("GreetingProvider")
public class GreetingProviderVerifcationTest {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    GreetingService greetingService;

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        // TODO
    }

    @BeforeEach
    void before(PactVerificationContext context) throws Exception {
        // TODO
    }
}