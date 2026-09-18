package edu.ccrm.domain;

import java.util.*;

public class Transcript {
    private final String studentRegNo;
    private final List<InnerLine> lines = new ArrayList<>();

    public Transcript(String studentRegNo) { this.studentRegNo = studentRegNo; }

    // Inner (non-static) class
    public class InnerLine {
        public final String courseCode;
        public final int credits;
        public final Grade grade;
        public InnerLine(String c, int cr, Grade g) { this.courseCode = c; this.credits = cr; this.grade = g; }
        @Override public String toString() { return courseCode + " (" + credits + " cr): " + grade; }
    }

    public void addLine(String courseCode, int credits, Grade grade) {
        lines.add(new InnerLine(courseCode, credits, grade));
    }

    public double computeGPA() {
        double pts = 0, cr = 0;
        for (InnerLine l : lines) {
            pts += l.credits * l.grade.points();
            cr += l.credits;
        }
        return cr == 0 ? 0 : pts / cr;
    }

    @Override public String toString() {
        StringBuilder sb = new StringBuilder("Transcript for ").append(studentRegNo).append("\n");
        for (InnerLine l : lines) sb.append("  ").append(l).append("\n");
        sb.append("GPA: ").append(String.format("%.2f", computeGPA()));
        return sb.toString();
    }
}
