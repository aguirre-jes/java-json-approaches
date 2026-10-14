# JSON-P with Helidon

## What this example demonstrates

`GET /customers/42` builds a `JsonObject` directly with
`Json.createObjectBuilder()`, without converting a POJO.

## How it works

`CustomerService` creates the `id` and `name` fields; Helidon JSON-P HTTP Media
writes the structure as an `application/json` response.

## Important files

- `pom.xml`: the Jakarta JSON-P API and `helidon-http-media-jsonp`.
- `CustomerService.java`: explicit `JsonObject` construction.
- `Main.java`: application startup and routing.
- `src/test`: HTTP contract test.

## Run it

```bash
cd helidon-jsonp-demo
mvn test
java -jar target/helidon-jsonp-demo.jar
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

`mvn test` verifies the status, JSON content, and typed values of the parsed
object, rather than whitespace or property order.

## What to notice

JSON-P is for directly creating and manipulating JSON structures. Therefore,
the code adds each property explicitly: it is not POJO binding.

## References

- [Helidon HTTP Media JSON-P](https://helidon.io/docs/latest/apidocs/io.helidon.http.media.jsonp/module-summary.html)
- [Jakarta JSON Processing](https://jakarta.ee/specifications/jsonp/)
