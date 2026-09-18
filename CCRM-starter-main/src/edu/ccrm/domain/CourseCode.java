package edu.ccrm.domain;

import java.util.Objects;

/** Immutable value object */
public final class CourseCode implements Comparable<CourseCode> {
    private final String value;

    public CourseCode(String value) {
        this.value = Objects.requireNonNull(value, "code");
        assert !this.value.isBlank() : "Course code must not be blank";
    }

    public String value() { return value; }

    @Override public String toString() { return value; }
    @Override public int hashCode() { return value.hashCode(); }
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CourseCode)) return false;
        return value.equals(((CourseCode)o).value);
    }
    @Override public int compareTo(CourseCode other) {
        return this.value.compareTo(other.value);
    }
}
