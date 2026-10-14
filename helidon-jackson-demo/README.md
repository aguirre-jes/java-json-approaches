# Jackson Person example

## What this demonstrates

This project demonstrates Jackson object binding in Helidon SE. It exposes
`GET /person` and `POST /person` using the same `Person` record as the JSON-B
and JSON-P projects.

## How it works

The service returns a `Person` for GET. For POST, it reads the request body as
`Person.class` and sends the object back. Helidon's Jackson HTTP Media module
connects the endpoint to Jackson Databind.

## Important files

- `pom.xml`: Jackson Databind and `helidon-http-media-jackson`.
- `Person.java`: the record with `name`, `age`, and `birthDate`.
- `PersonService.java`: the GET and POST object-binding endpoints.
- `Main.java`: application startup and `/person` routing.
- `src/test`: HTTP contract test.

## Run it

```bash
cd helidon-jackson-demo
mvn package
java -jar target/helidon-jackson-demo.jar
```

## Try it

```bash
curl -i http://localhost:8080/person
curl -i -X POST -H 'Content-Type: application/json' \
  -d '{"name":"Jesus","age":31,"birthDate":"1995-05-10"}' \
  http://localhost:8080/person
```

## Expected response

With the current Helidon/Jackson configuration, the date is represented as a
JSON array:

```json
{"name":"Jesus","age":31,"birthDate":[1995,5,10]}
```

This differs from the JSON-B and JSON-P examples, which use the ISO string
`"1995-05-10"`.

## Validate it

`mvn test` verifies `200 OK`, the person name, the numeric age, and Jackson's
current array representation of `LocalDate`.

## What to notice

Jackson and JSON-B both provide object binding, so the endpoint code looks
similar. They belong to different ecosystems and can make different
serialization decisions for the same Java type.

## References

- [Helidon HTTP Media Jackson](https://helidon.io/docs/latest/apidocs/io.helidon.http.media.jackson/module-summary.html)
- [Jackson documentation](https://github.com/FasterXML/jackson-docs/wiki)
