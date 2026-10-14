# Jackson with Helidon

## What this example demonstrates

`GET /customers/42` returns a Java `Customer`, and the
`helidon-http-media-jackson` module serializes it with Jackson.

## How it works

The routing calls `CustomerService`, which creates the record. The Jackson
HTTP Media integration uses `jackson-databind` to produce `application/json`.

## Important files

- `pom.xml`: Jackson Databind and its Helidon integration.
- `Customer.java`: record with `id` and `name`.
- `CustomerService.java`: endpoint that returns the object.
- `Main.java`: application startup and routing.
- `src/test`: HTTP contract test.

## Run it

```bash
cd helidon-jackson-demo
mvn test
java -jar target/helidon-jackson-demo.jar
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

`mvn test` verifies `200`, JSON, and both values with the correct JSON types.

## What to notice

Jackson is an implementation ecosystem, not a Jakarta specification. Here,
object-to-JSON binding occurs through Helidon's HTTP Media module; no Jackson
annotations are required for this record.

## References

- [Helidon HTTP Media Jackson](https://helidon.io/docs/latest/apidocs/io.helidon.http.media.jackson/module-summary.html)
- [Jackson documentation](https://github.com/FasterXML/jackson-docs/wiki)
