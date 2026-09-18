package edu.ccrm.domain;

import java.time.LocalDateTime;

public abstract class Person {
    private String id;
    private String regNo;
    private String fullName;
    private String email;
    private String status;
    private final LocalDateTime createdAt;

    protected Person(String id, String regNo, String fullName, String email, String status) {
        this.id = id;
        this.regNo = regNo;
        this.fullName = fullName;
        this.email = email;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }

    // Abstract method (Abstraction)
    public abstract String summary();

    // Encapsulation
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getRegNo() { return regNo; }
    public void setRegNo(String regNo) { this.regNo = regNo; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    @Override public String toString() {
        return summary();
    }
}
