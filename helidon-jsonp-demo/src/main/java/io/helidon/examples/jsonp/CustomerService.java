package io.helidon.examples.jsonp;

import io.helidon.webserver.http.HttpRules;
import io.helidon.webserver.http.HttpService;
import io.helidon.webserver.http.ServerRequest;
import io.helidon.webserver.http.ServerResponse;
import jakarta.json.Json;

final class CustomerService implements HttpService {
    @Override
    public void routing(HttpRules rules) {
        rules.get("/42", this::getCustomer);
    }

    private void getCustomer(ServerRequest request, ServerResponse response) {
        response.send(Json.createObjectBuilder()
                .add("id", 42)
                .add("name", "Jordan Lee")
                .build());
    }
}
