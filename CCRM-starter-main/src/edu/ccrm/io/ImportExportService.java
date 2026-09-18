package edu.ccrm.io;

import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.*;
import edu.ccrm.service.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class ImportExportService {
    private final AppConfig cfg = AppConfig.getInstance();
    private final StudentService studentService;
    private final CourseService courseService;

    public ImportExportService(StudentService ss, CourseService cs) {
        this.studentService = ss;
        this.courseService = cs;
    }

    public void importStudents(Path csv) throws IOException {
        try (var lines = Files.lines(csv, StandardCharsets.UTF_8)) {
            lines.skip(1).forEach(line -> {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    var s = new Student(parts[0], parts[1], parts[2], parts[3], parts[4]);
                    studentService.add(s);
                }
            });
        }
    }

    public void importCourses(Path csv) throws IOException {
        try (var lines = Files.lines(csv, StandardCharsets.UTF_8)) {
            lines.skip(1).forEach(line -> {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    var c = new Course.Builder()
                        .code(parts[0]).title(parts[1]).credits(Integer.parseInt(parts[2]))
                        .instructor(parts[3]).semester(Semester.valueOf(parts[4])).department(parts[5])
                        .build();
                    courseService.add(c);
                }
            });
        }
    }

    public void exportAll() throws IOException {
        Path outDir = cfg.dataDir().resolve("export");
        Files.createDirectories(outDir);
        exportStudents(outDir.resolve("students_export.csv"));
        exportCourses(outDir.resolve("courses_export.csv"));
        exportEnrollments(outDir.resolve("enrollments_export.csv"));
    }

    private void exportStudents(Path target) throws IOException {
        try (BufferedWriter w = Files.newBufferedWriter(target, StandardCharsets.UTF_8)) {
            w.write("id,regNo,fullName,email,status\n");
            for (var s : new StudentService().all()) {
                w.write(String.join(",", s.getId(), s.getRegNo(), s.getFullName(), s.getEmail(), s.getStatus()));
                w.write("\n");
            }
        }
    }

    private void exportCourses(Path target) throws IOException {
        try (BufferedWriter w = Files.newBufferedWriter(target, StandardCharsets.UTF_8)) {
            w.write("code,title,credits,instructor,semester,department\n");
            for (var c : new CourseService().all()) {
                w.write(String.join(",", c.getCode().value(), c.getTitle(), String.valueOf(c.getCredits()),
                        String.valueOf(c.getInstructor()), String.valueOf(c.getSemester()), String.valueOf(c.getDepartment())));
                w.write("\n");
            }
        }
    }

    private void exportEnrollments(Path target) throws IOException {
        try (BufferedWriter w = Files.newBufferedWriter(target, StandardCharsets.UTF_8)) {
            w.write("studentId,courseCode,semester,marks,grade\n");
            for (var e : DataStore.instance().enrollments()) {
                w.write(String.join(",", e.getStudentId(), e.getCourseCode(), String.valueOf(e.getSemester()),
                        String.valueOf(e.getMarks()), String.valueOf(e.getLetterGrade())));
                w.write("\n");
            }
        }
    }
}
