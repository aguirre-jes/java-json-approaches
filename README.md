# Java JSON layer demos

Three independent Helidon SE projects show the same HTTP response using
three different abstractions:

- [`helidon-jsonb-demo`](./helidon-jsonb-demo): JSON-B binds a Java
  `Customer` object to JSON through `helidon-http-media-jsonb`.
- [`helidon-jsonp-demo`](./helidon-jsonp-demo): JSON-P explicitly builds a
  `JsonObject` through `JsonObjectBuilder`.
- [`helidon-jackson-demo`](./helidon-jackson-demo): Jackson binds the
  `Customer` record through `helidon-http-media-jackson`.

The client always receives `GET /customers/42` with `200 OK` and:

```json
{"id":42,"name":"Jordan Lee"}
```

The response can be identical, but the abstraction level is not: JSON-B and
Jackson work with objects, while JSON-P works directly with a JSON structure.
Helidon WebServer and its HTTP Media modules produce the HTTP body and
`Content-Type`.

## Requirements and versions

- Java 27
- Maven 3.9+
- Helidon 27.0.0
- Jakarta JSON-B/JSON-P and providers managed by Helidon 27.0.0
- Jackson managed by Helidon 27.0.0

No database, credentials, container, or external service is required.

## Run and validate

From each project directory:

```bash
cd helidon-jsonb-demo && mvn test && java -jar target/helidon-jsonb-demo.jar
cd ../helidon-jsonp-demo && mvn test && java -jar target/helidon-jsonp-demo.jar
cd ../helidon-jackson-demo && mvn test && java -jar target/helidon-jackson-demo.jar
```

In another terminal:

```bash
curl -i -H 'Accept: application/json' http://localhost:8080/customers/42
```

The `Content-Type` must be JSON and the body must contain numeric `id` `42`
and `name` with value `"Jordan Lee"`. Each project also includes an HTTP test
that can be run with `mvn test`.

## Comparison

| Project | Model | Response production | Note |
| --- | --- | --- | --- |
| JSON-B | Object binding ↔ JSON | Helidon JSON-B media serializes `Customer` | Jakarta API + provider |
| JSON-P | JSON structure | The service creates a `JsonObject` and Helidon writes it | No POJO binding |
| Jackson | Object binding ↔ JSON | Helidon Jackson media serializes `Customer` | Jackson ecosystem |

This is not a benchmark and does not attempt to declare a winner. These are
reproducible examples for the associated Medium article and for observing the
actual Helidon integration.

## Limitations

The `helidon-http-media-*` modules connect each API to the HTTP layer. Adding
only a JSON dependency does not configure response serialization by itself.
JSON-P requires explicitly building the structure; JSON-B and Jackson allow
the service to return the object.

## References

- [Helidon documentation](https://helidon.io/docs/latest/)
- [Jakarta JSON Binding](https://jakarta.ee/specifications/jsonb/)
- [Jakarta JSON Processing](https://jakarta.ee/specifications/jsonp/)
- [Jackson documentation](https://github.com/FasterXML/jackson-docs/wiki)
