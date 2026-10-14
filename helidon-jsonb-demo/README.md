# JSON-B Person example

## What this demonstrates

This project demonstrates Jakarta JSON-B object binding in Helidon SE. The
same `Person` record is used for both `GET /person` and `POST /person`.

## How it works

`PersonService` returns a `Person` object for GET. For POST, Helidon's JSON-B
HTTP Media integration reads the request body as `Person.class`, and the
service sends that object back. The provider handles the Java-to-JSON and
JSON-to-Java conversion.

## Important files

- `pom.xml`: Helidon WebServer and `helidon-http-media-jsonb`.
- `Person.java`: the record with `name`, `age`, and `birthDate`.
- `PersonService.java`: the GET and POST object-binding endpoints.
- `Main.java`: application startup and `/person` routing.
- `src/test`: HTTP contract test.

## Run it

```bash
cd helidon-jsonb-demo
mvn package
java -jar target/helidon-jsonb-demo.jar
```

## Try it

```bash
curl -i http://localhost:8080/person
curl -i -X POST -H 'Content-Type: application/json' \
  -d '{"name":"Jesus","age":31,"birthDate":"1995-05-10"}' \
  http://localhost:8080/person
```

## Expected response

The response is semantically equivalent to:

```json
{"name":"Jesus","age":31,"birthDate":"1995-05-10"}
```

Property order is not part of the contract.

## Validate it

`mvn test` verifies the HTTP status and parses the response JSON to check the
name, numeric age, and ISO date value.

## What to notice

The service does not manually map fields. JSON-B provides standards-based
Jakarta object binding, while Helidon HTTP Media connects that provider to the
HTTP response.

## References

- [Helidon HTTP Media JSON-B](https://helidon.io/docs/latest/apidocs/io.helidon.http.media.jsonb/module-summary.html)
- [Jakarta JSON Binding](https://jakarta.ee/specifications/jsonb/)
