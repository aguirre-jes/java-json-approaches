# Java JSON approaches

This repository contains three independent Helidon SE 27.0.0 applications
that expose the same small REST example:

```text
GET  /person
POST /person
```

Each application uses the same Java record:

```java
public record Person(String name, int age, LocalDate birthDate) {}
```

The sample person is `Jesus`, age `31`, born on `1995-05-10`. The purpose is
not to benchmark libraries or declare a universal winner. It is to make the
JSON layer visible: the Java endpoint can look nearly identical while the
JSON representation and mapping responsibility change.

## The three approaches

### JSON-P: direct JSON processing

JSON-P works with JSON structures. The JSON-P service explicitly creates a
`JsonObject` with `Json.createObjectBuilder()`. For `POST /person`, it reads
each field, parses `birthDate` with `LocalDate.parse`, creates the Java record,
and builds the response object again.

The mental model is:

```text
Java values -> JSON structure
```

### JSON-B: standard object binding

JSON-B is the Jakarta standard for binding Java objects to and from JSON. The
JSON-B service can simply read `Person.class` from the request and send the
resulting `Person` object. In this project, Helidon's JSON-B HTTP Media
integration uses the Jakarta JSON-B provider (Eclipse Yasson transitively).

The mental model is:

```text
Java object <-> JSON
```

### Jackson: object binding in its own ecosystem

Jackson also maps Java objects to and from JSON, but it is a separate
ecosystem with its own modules and configuration. Helidon's Jackson HTTP Media
integration uses Jackson Databind and its Java time support.

The application code can look like JSON-B:

```java
Person person = request.content().as(Person.class);
response.send(person);
```

The provider still makes its own representation decisions. With the current
Helidon configuration, JSON-B and JSON-P represent `LocalDate` as
`"1995-05-10"`, while Jackson represents it as `[1995,5,10]`.

## Repository map

- [`helidon-jsonb-demo`](./helidon-jsonb-demo): Jakarta JSON-B binding.
- [`helidon-jsonp-demo`](./helidon-jsonp-demo): explicit JSON-P processing.
- [`helidon-jackson-demo`](./helidon-jackson-demo): Jackson object binding.

Each directory is a standalone Maven project. The projects share Helidon and
the Java baseline, but they do not depend on one another.

## Requirements and versions

- Java 27
- Maven 3.9+
- Helidon 27.0.0
- Jakarta JSON-B and provider managed by Helidon
- Jakarta JSON-P and provider managed by Helidon
- Jackson Databind and Helidon Jackson HTTP Media

No database, credentials, container, or external service is required.

## Run an example

From the selected project directory:

```bash
cd helidon-jsonb-demo
mvn test
java -jar target/helidon-jsonb-demo.jar
```

Use the equivalent commands for `helidon-jsonp-demo` or
`helidon-jackson-demo`.

In another terminal:

```bash
curl -i http://localhost:8080/person
curl -i -X POST \
  -H 'Content-Type: application/json' \
  -d '{"name":"Jesus","age":31,"birthDate":"1995-05-10"}' \
  http://localhost:8080/person
```

The GET and POST responses are semantically equivalent within each project.
The `Content-Type` is `application/json`; the `LocalDate` representation is
the intentional provider-specific difference described above.

## What the framework does

The endpoint does not write directly to a socket. Helidon WebServer routes the
request, and the matching HTTP Media integration connects the response to the
selected JSON technology:

```text
HTTP endpoint -> Helidon HTTP Media -> JSON-P / JSON-B / Jackson
```

Adding only a JSON API dependency does not configure HTTP serialization by
itself. The relevant `helidon-http-media-*` dependency is part of each
project's integration.

## Limitations and architectural lesson

The examples intentionally use one `LocalDate` field because it reveals a
decision hidden by object binding. JSON-P keeps the conversion visible in the
service. JSON-B and Jackson remove that mapping code from the application, but
the decision still exists in their providers and configuration.

That is why changing a JSON technology can affect serialization behavior,
deserialization behavior, tests, configuration, and public API compatibility.

## References

- [Helidon documentation](https://helidon.io/docs/latest/)
- [Jakarta JSON Binding](https://jakarta.ee/specifications/jsonb/)
- [Jakarta JSON Processing](https://jakarta.ee/specifications/jsonp/)
- [Jackson documentation](https://github.com/FasterXML/jackson-docs/wiki)
