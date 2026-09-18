package edu.ccrm.domain;

import java.util.*;
import java.time.LocalDateTime;

public class Student extends Person {
    private final List<String> enrolledCourses = new ArrayList<>();
    private boolean active = true;
    private final LocalDateTime lastUpdated = LocalDateTime.now();

    public Student(String id, String regNo, String fullName, String email, String status) {
        super(id, regNo, fullName, email, status);
    }

    public List<String> getEnrolledCourses() { return Collections.unmodifiableList(enrolledCourses); }
    public void enroll(String courseCode) { enrolledCourses.add(courseCode); }
    public void unenroll(String courseCode) { enrolledCourses.remove(courseCode); }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    @Override public String summary() {
        return "Student[" + getRegNo() + " - " + getFullName() + ", status=" + getStatus() + "]";
    }
}
