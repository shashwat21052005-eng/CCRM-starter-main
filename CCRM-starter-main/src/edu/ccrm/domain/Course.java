package edu.ccrm.domain;

import java.util.Objects;

public class Course {
    private final CourseCode code;
    private String title;
    private int credits;
    private String instructor;
    private Semester semester;
    private String department;
    private boolean active = true;

    private Course(Builder b) {
        this.code = Objects.requireNonNull(b.code);
        this.title = b.title;
        this.credits = b.credits;
        this.instructor = b.instructor;
        this.semester = b.semester;
        this.department = b.department;
        assert credits > 0 && credits <= 6 : "Credits must be 1..6";
    }

    public CourseCode getCode() { return code; }
    public String getTitle() { return title; }
    public int getCredits() { return credits; }
    public String getInstructor() { return instructor; }
    public Semester getSemester() { return semester; }
    public String getDepartment() { return department; }
    public boolean isActive() { return active; }
    public void setActive(boolean a) { this.active = a; }
    public void setTitle(String t) { this.title = t; }
    public void setInstructor(String i) { this.instructor = i; }
    public void setSemester(Semester s) { this.semester = s; }
    public void setDepartment(String d) { this.department = d; }
    public void setCredits(int c) { this.credits = c; }

    @Override public String toString() {
        return code + " - " + title + " (" + credits + " cr, " + semester + ", " + department + ", instr=" + instructor + ")";
    }

    // Static nested class (Builder pattern)
    public static class Builder {
        private CourseCode code;
        private String title;
        private int credits;
        private String instructor;
        private Semester semester;
        private String department;

        public Builder code(String v) { this.code = new CourseCode(v); return this; }
        public Builder title(String v) { this.title = v; return this; }
        public Builder credits(int v) { this.credits = v; return this; }
        public Builder instructor(String v) { this.instructor = v; return this; }
        public Builder semester(Semester v) { this.semester = v; return this; }
        public Builder department(String v) { this.department = v; return this; }
        public Course build() { return new Course(this); }
    }
}
