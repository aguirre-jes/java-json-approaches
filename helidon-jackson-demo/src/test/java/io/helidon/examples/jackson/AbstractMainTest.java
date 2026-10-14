package io.helidon.examples.jackson;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.helidon.http.Status;
import io.helidon.webclient.api.ClientResponseTyped;
import io.helidon.webclient.http1.Http1Client;
import io.helidon.webserver.http.HttpRouting;
import io.helidon.webserver.testing.junit5.SetUpRoute;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

abstract class AbstractMainTest {
    private final Http1Client client;

    AbstractMainTest(Http1Client client) {
        this.client = client;
    }

    @SetUpRoute
    static void routing(HttpRouting.Builder builder) {
        Main.routing(builder);
    }

    @Test
    void returnsPersonAsJson() throws Exception {
        ClientResponseTyped<String> response = client.get("/person").request(String.class);
        JsonNode person = new ObjectMapper().readTree(response.entity());

        assertThat(response.status(), is(Status.OK_200));
        assertThat(person.get("name").textValue(), is("Jesus"));
        assertThat(person.get("age").intValue(), is(31));
        assertThat(person.get("birthDate").toString(), is("[1995,5,10]"));
    }
}
