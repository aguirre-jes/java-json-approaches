package io.helidon.examples.jackson;

import java.time.LocalDate;

public record Person(String name, int age, LocalDate birthDate) {
}
