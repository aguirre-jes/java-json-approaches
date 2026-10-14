# JSON-P Person example

## What this demonstrates

This project demonstrates direct JSON Processing with Jakarta JSON-P. It
does not present JSON-P as POJO binding: the service explicitly creates and
reads JSON structures.

## How it works

For `GET /person`, `PersonService` creates a `Person`, adds each field to a
`JsonObjectBuilder`, and sends the resulting `JsonObject`. For
`POST /person`, it reads every field from the incoming `JsonObject`, parses
the date with `LocalDate.parse`, and builds the response explicitly.

## Important files

- `pom.xml`: Jakarta JSON-P and `helidon-http-media-jsonp`.
- `Person.java`: the record with `name`, `age`, and `birthDate`.
- `PersonService.java`: explicit JSON construction and parsing.
- `Main.java`: application startup and `/person` routing.
- `src/test`: HTTP contract test.

## Run it

```bash
cd helidon-jsonp-demo
mvn package
java -jar target/helidon-jsonp-demo.jar
```

## Try it

```bash
curl -i http://localhost:8080/person
curl -i -X POST -H 'Content-Type: application/json' \
  -d '{"name":"Jesus","age":31,"birthDate":"1995-05-10"}' \
  http://localhost:8080/person
```

## Expected response

```json
{"name":"Jesus","age":31,"birthDate":"1995-05-10"}
```

## Validate it

`mvn test` verifies `200 OK` and parses the returned JSON to check all three
fields and their JSON types.

## What to notice

JSON-P gives direct control over the JSON structure. That control also means
the application owns the mapping logic and the `LocalDate` conversion.

## References

- [Helidon HTTP Media JSON-P](https://helidon.io/docs/latest/apidocs/io.helidon.http.media.jsonp/module-summary.html)
- [Jakarta JSON Processing](https://jakarta.ee/specifications/jsonp/)
