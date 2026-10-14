package io.helidon.examples.jsonp;

import io.helidon.http.Status;
import io.helidon.webclient.api.ClientResponseTyped;
import io.helidon.webclient.http1.Http1Client;
import io.helidon.webserver.http.HttpRouting;
import io.helidon.webserver.testing.junit5.SetUpRoute;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import java.io.StringReader;
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
    void returnsCustomerAsJson() {
        ClientResponseTyped<String> response = client.get("/customers/42").request(String.class);
        JsonObject customer = Json.createReader(new StringReader(response.entity())).readObject();

        assertThat(response.status(), is(Status.OK_200));
        assertThat(customer.getInt("id"), is(42));
        assertThat(customer.getString("name"), is("Jordan Lee"));
    }
}
