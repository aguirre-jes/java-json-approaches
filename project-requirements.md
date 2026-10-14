# Project Requirements: Java JSON Layer Demonstrations

## 1. Project Overview

Create a public, educational repository that demonstrates three distinct
ways to produce JSON from a Java REST API:

1.  **JSON-B** --- bind Java objects to and from JSON using the Jakarta
    JSON Binding API and a compatible provider.
2.  **JSON-P** --- construct and process JSON structures directly using
    the Jakarta JSON Processing API.
3.  **Jackson** --- bind Java objects to and from JSON using the Jackson
    ecosystem.

The repository will support a Medium article about the JSON
implementation choices that can sit behind a Java backend. Its primary
goal is clarity and reproducibility, not benchmarking or declaring a
universal winner.

The three implementations must be separate, independently runnable
projects, but should expose equivalent example behavior wherever the
underlying abstraction permits it.

## 2. Learning Objectives

A reader should be able to:

-   Explain the difference between object binding and direct JSON
    processing.
-   Identify which component is responsible for producing the HTTP
    response body.
-   Run each example locally and inspect the returned JSON.
-   Compare the same response shape across the three implementations.
-   Understand that JSON-B and JSON-P are Jakarta specifications/APIs,
    while Jackson is an implementation ecosystem.
-   See the practical consequences of framework integration and
    configuration without assuming all three technologies are
    interchangeable.

## 3. Repository Structure

Use a structure similar to the following. Adjust package names or build
files only when required by the selected, documented Helidon setup.

``` text
java-json-layer-demo/
├── README.md
├── project-requirements.md
├── .gitignore
├── json-b/
│   ├── README.md
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/
│       │   └── resources/
│       └── test/
│           └── java/
├── json-p/
│   ├── README.md
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/
│       │   └── resources/
│       └── test/
│           └── java/
└── jackson/
    ├── README.md
    ├── pom.xml
    └── src/
        ├── main/
        │   ├── java/
        │   └── resources/
        └── test/
            └── java/
```

Each directory (`json-b`, `json-p`, and `jackson`) must be a standalone
Maven project that can be built and run without relying on another
example directory.

## 4. Technology and Version Policy

-   Use **Helidon** for all three projects so that the comparison stays
    focused on the JSON layer rather than comparing different web
    frameworks.
-   Select a current, stable Helidon version that officially supports
    the chosen Java version and the integrations used by each project.
-   Use the **same Helidon version and Java baseline** in all three
    projects wherever technically possible.
-   Pin dependency and plugin versions; do not use floating versions
    such as `LATEST`.
-   Verify dependencies and configuration against official Helidon and
    Jakarta documentation before implementing them.
-   Record the chosen Java version, Helidon version, JSON-B provider/API
    version, JSON-P API/provider version, Jackson version, and Maven
    version in the root README.
-   Do not assume that adding a JSON dependency automatically configures
    Helidon's HTTP response serialization. Confirm the supported
    integration and document the actual behavior.
-   If Helidon cannot support one of the three approaches through the
    same supported integration model, document the limitation accurately
    rather than inventing a configuration. Keep the examples as
    comparable as possible and explain any necessary difference.

## 5. Shared Example Contract

Each project should expose the same simple endpoints:

``` http
GET /person
POST /person
Accept: application/json
```

Use the same small domain model with these fields:

-   `name`: string value
-   `age`: integer value
-   `birthDate`: ISO date represented by `LocalDate`

The expected person response should contain the same sample values:

``` json
{
  "name": "Jesus",
  "age": 31,
  "birthDate": "1995-05-10"
}
```

Use the same sample values in all three projects where the underlying
technology produces the same representation. The Jackson example documents
its provider-specific `LocalDate` representation.

Requirements:

-   Return HTTP `200 OK` for the valid request.
-   Return a JSON content type, normally `application/json`.
-   Ensure the response contains both fields with the expected JSON
    types.
-   Do not require a database, external API, credentials, containers, or
    cloud services.
-   Keep the endpoints and domain behavior minimal so the JSON approach
    remains the focus.
-   If a framework requires a different endpoint declaration or response
    type for a particular JSON API, preserve the response contract and
    explain the difference in that project's README.

## 6. Implementation Requirements by Project

### 6.1 `json-b/` --- Object Binding with JSON-B

Demonstrate JSON-B's object-binding model.

-   Define a Java `Person` type, preferably a record if supported by the
    selected Java baseline and JSON-B provider; otherwise use a simple
    POJO.
-   Use the Jakarta JSON Binding API and a compatible provider.
-   Return a Java person object from the endpoint where the selected
    Helidon integration supports this behavior.
-   Verify which provider actually serializes the response; do not infer
    this only from the presence of an API dependency.
-   If serialization is performed explicitly rather than by Helidon's
    HTTP layer, make that explicit in code and documentation.
-   Include a test that verifies the returned JSON fields and values.

Explain in `json-b/README.md`: - What JSON-B is and what role its
API/provider plays. - How the Java object becomes JSON in this
project. - Which dependency and configuration enable the behavior. - How
to run and validate the example.

### 6.2 `json-p/` --- Direct JSON Processing with JSON-P

Demonstrate direct JSON structure creation and processing rather than
presenting JSON-P as a drop-in POJO-binding equivalent.

-   Use the Jakarta JSON Processing API and a compatible
    implementation/provider.
-   Build the person response using JSON-P facilities such as
    `JsonObjectBuilder`.
-   Return or write the resulting JSON through a supported Helidon
    response mechanism.
-   Keep the response semantically equivalent to the shared contract.
-   Include a test that verifies the response fields and values.

Explain in `json-p/README.md`: - What JSON-P is and what problem it
solves. - Why this example constructs a JSON structure explicitly rather
than returning a person POJO. - How the JSON structure is turned into
an HTTP response. - The difference between constructing JSON directly
and binding Java objects to JSON. - How to run and validate the example.

### 6.3 `jackson/` --- Object Binding with Jackson

Demonstrate Jackson's object-binding model.

-   Use a compatible, explicitly versioned Jackson setup supported by
    the selected Helidon integration.
-   Define the same person data shape and values used in the other
    projects.
-   Return a Java person object where the selected integration
    supports this behavior.
-   Verify which Jackson components and framework integration perform
    serialization.
-   Do not add Jackson annotations unless they serve a specific purpose;
    if used, explain them.
-   Include a test that verifies the response fields and values.

Explain in `jackson/README.md`: - What Jackson is and which components
are used. - How the Java object becomes JSON in this project. - How
Jackson is integrated with Helidon. - Which dependencies and
configuration matter. - How to run and validate the example.

## 7. Root Documentation

The root `README.md` must be the reader's entry point. It should be
concise and practical, and include:

1.  **Purpose** --- what the repository demonstrates and what it does
    not attempt to prove.
2.  **Conceptual overview** --- object binding (JSON-B/Jackson) versus
    direct JSON processing (JSON-P).
3.  **Repository map** --- one-sentence explanation and link for each
    project directory.
4.  **Prerequisites** --- Java, Maven, and any required environment
    setup.
5.  **Run the examples** --- exact commands for building and running
    each project.
6.  **Validate the responses** --- copy-pasteable `curl` commands and
    expected JSON.
7.  **Comparison table** --- abstraction, primary API/model, how the
    response is produced, and notable caveats.
8.  **Version matrix** --- actual versions selected and tested.
9.  **Limitations** --- explain any differences caused by framework
    integration or API abstraction.
10. **Connection to the article** --- a short explanation that the
    repository provides reproducible examples for the related Medium
    article.

Avoid unsupported performance claims. Do not describe the repository as
a benchmark unless a separate, controlled benchmark is intentionally
added.

## 8. Documentation Requirements for Each Project

Each project README must be short enough to scan quickly and follow this
structure:

1.  **What this example demonstrates**
2.  **How it works** --- a brief request-to-response flow
3.  **Important files** --- list key source/configuration/test files and
    explain each in one sentence
4.  **Run it** --- exact commands, including the working directory
5.  **Try it** --- exact `curl` request
6.  **Expected response** --- JSON example
7.  **Validate it** --- exact test command and what the test verifies
8.  **What to notice** --- the key conceptual distinction
9.  **References** --- links to official documentation used

Documentation must explain the actual code in the repository, not a
generic description of the technology. Update documentation whenever
implementation details change.

## 9. Testing and Validation

Each project must provide automated tests and independently pass its
documented build/test workflow.

At minimum:

-   Build and test each project using Maven.
-   Test the HTTP endpoint, preferably with an integration test using
    the project's supported test facilities.
-   Verify status code, JSON content type, `id`, `name`, and JSON types.
-   Compare parsed JSON values rather than raw whitespace or
    object-property order.
-   Ensure the three examples return equivalent JSON data.
-   Ensure tests do not depend on internet access at runtime beyond
    dependency resolution.
-   Do not claim that a command was tested unless it was actually run
    successfully.

The root README must provide the commands to validate all three projects
from a clean checkout. If a root-level script is added, it must be
optional, portable, and documented; the projects must remain
independently buildable.

## 10. Agent Workflow and Acceptance Criteria

An implementation agent must follow this sequence:

1.  Inspect current official Helidon documentation and supported
    examples before selecting dependencies or configuration.
2.  Choose and record a compatible, stable version set.
3.  Implement the smallest working `json-b` project first and verify its
    serialization path.
4.  Implement `json-p` using explicit JSON construction.
5.  Implement `jackson` and verify its actual integration path.
6.  Add automated tests for each project.
7.  Run the documented build and test commands for all three projects.
8.  Review the source, tests, dependency declarations, and README files
    for consistency.
9.  Correct any mismatch between documented behavior and actual
    behavior.
10. Report the exact commands run, their outcomes, and any remaining
    limitations.

The repository is accepted only when:

-   [ ] The three project directories are independent Maven projects.
-   [ ] All three use the documented Helidon/Java baseline where
    feasible.
-   [ ] Dependency and plugin versions are pinned.
-   [ ] Each project demonstrates its intended JSON approach without
    misrepresenting the API.
-   [ ] All three expose the same documented endpoint and equivalent
    response data.
-   [ ] Each project has a concise, accurate README.
-   [ ] The root README explains the key conceptual difference and links
    to all examples.
-   [ ] Automated tests validate the HTTP contract.
-   [ ] All documented commands have actually been executed
    successfully, or failures are clearly reported.
-   [ ] No unsupported claims, fabricated test results, or undocumented
    configuration assumptions remain.

## 11. Scope Boundaries

Do not add the following unless explicitly requested:

-   A database or persistence layer.
-   Authentication, authorization, or user management.
-   A frontend or web UI.
-   Docker, Kubernetes, or cloud deployment configuration.
-   Performance benchmarks or claims that one option is faster.
-   Unrelated framework comparisons.
-   Multiple endpoints that do not help explain the JSON layer.
-   Excessive abstractions, shared libraries, or multi-module Maven
    complexity.

The priority is a small, transparent, reproducible learning repository
that a reader can clone, run, and understand quickly.

## 12. Editorial Principle

The repository should support this central observation:

> The JSON response may look identical to the client, while the Java
> API, abstraction level, and framework integration behind it differ.

Keep the implementation honest: **JSON-B, JSON-P, and Jackson are
related, but they are not three interchangeable alternatives at the same
abstraction level.** The examples should make that distinction visible
through code and tests rather than relying on prose alone.
