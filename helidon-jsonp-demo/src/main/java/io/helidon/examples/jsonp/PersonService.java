package io.helidon.examples.jsonp;

import io.helidon.webserver.http.HttpRules;
import io.helidon.webserver.http.HttpService;
import io.helidon.webserver.http.ServerRequest;
import io.helidon.webserver.http.ServerResponse;
import jakarta.json.Json;
import jakarta.json.JsonObject;

import java.time.LocalDate;

final class PersonService implements HttpService {
    @Override
    public void routing(HttpRules rules) {
        rules.get("/", this::getPerson)
                .post("/", this::createPerson);
    }

    private void getPerson(ServerRequest request, ServerResponse response) {
        response.send(toJson(new Person("Jesus", 31, LocalDate.of(1995, 5, 10))));
    }

    private void createPerson(ServerRequest request, ServerResponse response) {
        JsonObject json = request.content().as(JsonObject.class);
        Person person = new Person(
                json.getString("name"),
                json.getInt("age"),
                LocalDate.parse(json.getString("birthDate")));
        response.send(toJson(person));
    }

    private JsonObject toJson(Person person) {
        return Json.createObjectBuilder()
                .add("name", person.name())
                .add("age", person.age())
                .add("birthDate", person.birthDate().toString())
                .build();
    }
}
