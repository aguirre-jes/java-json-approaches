package io.helidon.examples.jsonb;

import java.time.LocalDate;

public record Person(String name, int age, LocalDate birthDate) {
}
