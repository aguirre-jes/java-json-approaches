package io.helidon.examples.jsonb;

import io.helidon.webserver.http.HttpRules;
import io.helidon.webserver.http.HttpService;
import io.helidon.webserver.http.ServerRequest;
import io.helidon.webserver.http.ServerResponse;

final class PersonService implements HttpService {
    @Override
    public void routing(HttpRules rules) {
        rules.get("/", this::getPerson)
                .post("/", this::createPerson);
    }

    private void getPerson(ServerRequest request, ServerResponse response) {
        response.send(new Person("Jesus", 31, java.time.LocalDate.of(1995, 5, 10)));
    }

    private void createPerson(ServerRequest request, ServerResponse response) {
        response.send(request.content().as(Person.class));
    }
}
