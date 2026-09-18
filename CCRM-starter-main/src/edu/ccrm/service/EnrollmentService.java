package edu.ccrm.service;

import edu.ccrm.domain.*;
import edu.ccrm.exception.*;
import java.util.*;
import java.util.stream.Collectors;

public class EnrollmentService {
    private final DataStore ds = DataStore.instance();
    private final int MAX_CREDITS_PER_SEM = 20;

    public void enroll(String studentId, String courseCode, Semester sem) throws DuplicateEnrollmentException, MaxCreditLimitExceededException {
        // Duplicate?
        boolean exists = ds.enrollments().stream().anyMatch(e -> e.getStudentId().equals(studentId) && e.getCourseCode().equals(courseCode) && e.getSemester()==sem);
        if (exists) throw new DuplicateEnrollmentException("Already enrolled.");

        int currentCredits = ds.enrollments().stream()
                .filter(e -> e.getStudentId().equals(studentId) && e.getSemester()==sem)
                .mapToInt(e -> ds.courses().get(e.getCourseCode()).getCredits())
                .sum();
        int newCredits = currentCredits + ds.courses().get(courseCode).getCredits();
        if (newCredits > MAX_CREDITS_PER_SEM) throw new MaxCreditLimitExceededException("Credit limit exceeded: " + newCredits);

        ds.enrollments().add(new Enrollment(studentId, courseCode, sem));
        Optional.ofNullable(ds.students().get(studentId)).ifPresent(s -> s.enroll(courseCode));
    }

    public void unenroll(String studentId, String courseCode, Semester sem) {
        ds.enrollments().removeIf(e -> e.getStudentId().equals(studentId) && e.getCourseCode().equals(courseCode) && e.getSemester()==sem);
        Optional.ofNullable(ds.students().get(studentId)).ifPresent(s -> s.unenroll(courseCode));
    }

    public void recordMarks(String studentId, String courseCode, Semester sem, double marks) {
        ds.enrollments().stream()
          .filter(e -> e.getStudentId().equals(studentId) && e.getCourseCode().equals(courseCode) && e.getSemester()==sem)
          .findFirst().ifPresent(e -> e.setMarks(marks));
    }

    public List<Enrollment> forStudent(String studentId) {
        return ds.enrollments().stream().filter(e -> e.getStudentId().equals(studentId)).collect(Collectors.toList());
    }
}
