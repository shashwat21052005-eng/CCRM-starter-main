package edu.ccrm.domain;

import java.time.LocalDateTime;

public class Enrollment {
    private final String studentId;
    private final String courseCode;
    private final Semester semester;
    private Double marks; // nullable until recorded
    private Grade letterGrade;
    private final LocalDateTime enrolledAt = LocalDateTime.now();

    public Enrollment(String studentId, String courseCode, Semester semester) {
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.semester = semester;
    }

    public String getStudentId() { return studentId; }
    public String getCourseCode() { return courseCode; }
    public Semester getSemester() { return semester; }
    public Double getMarks() { return marks; }
    public void setMarks(Double marks) {
        this.marks = marks;
        this.letterGrade = Grade.fromScore(marks == null ? 0 : marks);
    }
    public Grade getLetterGrade() { return letterGrade; }
    public LocalDateTime getEnrolledAt() { return enrolledAt; }

    @Override public String toString() {
        return courseCode + "@" + semester + " marks=" + marks + " grade=" + letterGrade;
    }
}
