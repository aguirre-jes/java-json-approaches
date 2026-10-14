# JSON-B with Helidon

## What this example demonstrates

`GET /customers/42` returns a Java `Customer`. The
`helidon-http-media-jsonb` module uses JSON-B to serialize it to JSON.

## How it works

Helidon routes the request to `CustomerService`; the service creates
`new Customer(42, "Jordan Lee")`; JSON-B HTTP Media converts that object into
the `application/json` body.

## Important files

- `pom.xml`: Helidon and the JSON-B integration.
- `Customer.java`: the domain record.
- `CustomerService.java`: the endpoint that returns the object.
- `Main.java`: application startup and routing.
- `src/test`: test for the status and JSON fields.

## Run it

```bash
cd helidon-jsonb-demo
mvn test
java -jar target/helidon-jsonb-demo.jar
```

## Try it

```bash
curl -i -H 'Accept: application/json' http://localhost:8080/customers/42
```

## Expected response

```json
{"id":42,"name":"Jordan Lee"}
```

## Validate it

`mvn test` runs the HTTP test and verifies `200`, JSON, integer `id`, and
`name`, without depending on property order.

## What to notice

The service works with a Java object; JSON-B and its provider perform the
binding. The API is Jakarta, and Helidon connects the provider through HTTP
Media.

## References

- [Helidon HTTP Media JSON-B](https://helidon.io/docs/latest/apidocs/io.helidon.http.media.jsonb/module-summary.html)
- [Jakarta JSON Binding](https://jakarta.ee/specifications/jsonb/)
