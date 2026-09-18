package edu.ccrm.service;

import edu.ccrm.domain.*;
import java.util.*;
import java.util.stream.Collectors;

public class TranscriptService {
    private final DataStore ds = DataStore.instance();

    public Transcript buildTranscript(String studentId) {
        var studentOpt = Optional.ofNullable(ds.students().get(studentId));
        if (studentOpt.isEmpty()) throw new IllegalArgumentException("Unknown student: " + studentId);

        var student = studentOpt.get();
        Transcript t = new Transcript(student.getRegNo());

        var mapCourse = ds.courses();
        for (Enrollment e : ds.enrollments()) {
            if (e.getStudentId().equals(studentId) && e.getMarks() != null) {
                int credits = Optional.ofNullable(mapCourse.get(e.getCourseCode())).map(Course::getCredits).orElse(0);
                t.addLine(e.getCourseCode(), credits, e.getLetterGrade());
            }
        }
        return t;
    }

    public Map<String, Long> gpaDistributionBuckets() {
        // Stream aggregation demo
        return ds.students().values().stream()
            .collect(Collectors.groupingBy(
                s -> {
                    Transcript t = buildTranscript(s.getId());
                    double gpa = t.computeGPA();
                    if (gpa >= 9) return "9-10";
                    if (gpa >= 8) return "8-9";
                    if (gpa >= 7) return "7-8";
                    if (gpa >= 6) return "6-7";
                    return "<6";
                },
                Collectors.counting()
            ));
    }
}
