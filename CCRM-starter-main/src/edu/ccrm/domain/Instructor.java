package edu.ccrm.domain;

public class Instructor extends Person {
    private String department;

    public Instructor(String id, String regNo, String fullName, String email, String status, String department) {
        super(id, regNo, fullName, email, status);
        this.department = department;
    }

    public String getDepartment() { return department; }
    public void setDepartment(String d) { this.department = d; }

    @Override public String summary() {
        return "Instructor[" + getFullName() + "@" + department + "]";
    }
}
