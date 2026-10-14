package io.helidon.examples.jackson;

import io.helidon.webserver.http.HttpRules;
import io.helidon.webserver.http.HttpService;
import io.helidon.webserver.http.ServerRequest;
import io.helidon.webserver.http.ServerResponse;

final class CustomerService implements HttpService {
    @Override
    public void routing(HttpRules rules) {
        rules.get("/42", this::getCustomer);
    }

    private void getCustomer(ServerRequest request, ServerResponse response) {
        response.send(new Customer(42, "Jordan Lee"));
    }
}
