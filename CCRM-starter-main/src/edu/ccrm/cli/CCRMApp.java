package edu.ccrm.cli;

import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.*;
import edu.ccrm.exception.*;
import edu.ccrm.io.*;
import edu.ccrm.service.*;
import edu.ccrm.util.ArrayDemos;

import java.nio.file.*;
import java.util.*;
import java.util.function.Consumer;

public class CCRMApp {
    private final AppConfig cfg = AppConfig.getInstance();
    private final StudentService studentService = new StudentService();
    private final CourseService courseService = new CourseService();
    private final EnrollmentService enrollmentService = new EnrollmentService();
    private final TranscriptService transcriptService = new TranscriptService();

    private final ImportExportService ioService = new ImportExportService(studentService, courseService);
    private final BackupService backupService = new BackupService();
    private final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // Bitwise demo in code: simple permission flag combination
        int READ=1, WRITE=2, EXEC=4; int mask = READ | WRITE; // == 3
        if ((mask & READ) != 0) { /* has read */ }

        new CCRMApp().run();
    }

    void run() {
        printPlatformNote();

        boolean running = true;
        while (running) { // while loop
            showMainMenu();
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1" -> manageStudents();
                case "2" -> manageCourses();
                case "3" -> manageEnrollmentGrades();
                case "4" -> importExportMenu();
                case "5" -> backupMenu();
                case "6" -> reportsMenu();
                case "7" -> { running = false; System.out.println("Exiting. Bye!"); }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    void showMainMenu() {
        System.out.println("""
==== CCRM ====
1) Manage Students
2) Manage Courses
3) Enrollment & Grades
4) Import/Export
5) Backup & Show Size
6) Reports
7) Exit
> """);
    }

    void manageStudents() {
        boolean back = false;
        do { // do-while demo
            System.out.println("""
-- Students --
1) Add
2) List
3) Update status
4) Deactivate
5) Back
> """);
            String c = sc.nextLine();
            switch (c) {
                case "1" -> addStudent();
                case "2" -> listStudents();
                case "3" -> updateStudentStatus();
                case "4" -> deactivateStudent();
                case "5" -> back = true;
                default -> System.out.println("Unknown.");
            }
        } while (!back);
    }

    void addStudent() {
        System.out.print("id: "); String id = sc.nextLine();
        System.out.print("regNo: "); String reg = sc.nextLine();
        System.out.print("fullName: "); String name = sc.nextLine();
        System.out.print("email: "); String email = sc.nextLine();
        System.out.print("status: "); String status = sc.nextLine();
        studentService.add(new Student(id, reg, name, email, status));
        System.out.println("Added.");
    }

    void listStudents() {
        for (Student s : studentService.all()) { // enhanced-for
            System.out.println(s);
        }
        // Upcast/downcast/instanceof demo:
        for (Person p : studentService.all()) { // upcast Student -> Person
            if (p instanceof Student st) { // pattern matching
                // downcast to Student (implicit in pattern)
                st.getEnrolledCourses();
            }
        }
    }

    void updateStudentStatus() {
        System.out.print("id: "); String id = sc.nextLine();
        studentService.byId(id).ifPresentOrElse(s -> {
            System.out.print("new status: ");
            s.setStatus(sc.nextLine());
            System.out.println("Updated.");
        }, () -> System.out.println("Not found."));
    }

    void deactivateStudent() {
        System.out.print("id: "); String id = sc.nextLine();
        studentService.byId(id).ifPresent(s -> { s.setActive(false); s.setStatus("INACTIVE"); });
        System.out.println("Deactivated (if existed).");
    }

    void manageCourses() {
        System.out.println("""
-- Courses --
1) Add
2) List
3) Search (instructor/department/semester)
4) Back
> """);
        String c = sc.nextLine();
        switch (c) {
            case "1" -> addCourse();
            case "2" -> courseService.all().forEach(System.out::println);
            case "3" -> {
                System.out.print("Filter by (i/d/s): ");
                String f = sc.nextLine();
                switch (f) {
                    case "i" -> { System.out.print("Instructor: "); String i = sc.nextLine(); courseService.byInstructor(i).forEach(System.out::println); }
                    case "d" -> { System.out.print("Department: "); String d = sc.nextLine(); courseService.byDepartment(d).forEach(System.out::println); }
                    case "s" -> { System.out.print("Semester (SPRING/SUMMER/FALL): "); Semester s = Semester.valueOf(sc.nextLine()); courseService.bySemester(s).forEach(System.out::println); }
                    default -> System.out.println("Unknown.");
                }
            }
            default -> {}
        }
    }

    void addCourse() {
        System.out.print("code: "); String code = sc.nextLine();
        System.out.print("title: "); String title = sc.nextLine();
        System.out.print("credits: "); int cr = Integer.parseInt(sc.nextLine());
        System.out.print("instructor: "); String instr = sc.nextLine();
        System.out.print("semester (SPRING/SUMMER/FALL): "); Semester sem = Semester.valueOf(sc.nextLine());
        System.out.print("department: "); String dept = sc.nextLine();
        var c = new Course.Builder().code(code).title(title).credits(cr).instructor(instr).semester(sem).department(dept).build();
        courseService.add(c);
        System.out.println("Added " + c);
    }

    void manageEnrollmentGrades() {
        System.out.println("""
-- Enrollment & Grades --
1) Enroll
2) Unenroll
3) Record Marks
4) Print Transcript
5) Back
> """);
        String c = sc.nextLine();
        try {
            switch (c) {
                case "1" -> {
                    System.out.print("studentId: "); String sid = sc.nextLine();
                    System.out.print("courseCode: "); String cc = sc.nextLine();
                    System.out.print("semester (SPRING/SUMMER/FALL): "); Semester sem = Semester.valueOf(sc.nextLine());
                    enrollmentService.enroll(sid, cc, sem);
                    System.out.println("Enrolled.");
                }
                case "2" -> {
                    System.out.print("studentId: "); String sid = sc.nextLine();
                    System.out.print("courseCode: "); String cc = sc.nextLine();
                    System.out.print("semester (SPRING/SUMMER/FALL): "); Semester sem = Semester.valueOf(sc.nextLine());
                    enrollmentService.unenroll(sid, cc, sem);
                    System.out.println("Unenrolled.");
                }
                case "3" -> {
                    System.out.print("studentId: "); String sid = sc.nextLine();
                    System.out.print("courseCode: "); String cc = sc.nextLine();
                    System.out.print("semester (SPRING/SUMMER/FALL): "); Semester sem = Semester.valueOf(sc.nextLine());
                    System.out.print("marks (0-100): "); double m = Double.parseDouble(sc.nextLine());
                    enrollmentService.recordMarks(sid, cc, sem, m);
                    System.out.println("Recorded.");
                }
                case "4" -> {
                    System.out.print("studentId: "); String sid = sc.nextLine();
                    Transcript t = transcriptService.buildTranscript(sid);
                    System.out.println(t);
                }
                default -> {}
            }
        } catch (DuplicateEnrollmentException | MaxCreditLimitExceededException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    void importExportMenu() {
        System.out.println("""
-- Import/Export --
1) Import Students (test-data/students.csv)
2) Import Courses (test-data/courses.csv)
3) Export All
4) Arrays Demo
5) Back
> """);
        String c = sc.nextLine();
        switch (c) {
            case "1" -> {
                try { ioService.importStudents(Paths.get("test-data/students.csv")); System.out.println("Imported students."); }
                catch (Exception e) { System.out.println("Failed: " + e.getMessage()); }
            }
            case "2" -> {
                try { ioService.importCourses(Paths.get("test-data/courses.csv")); System.out.println("Imported courses."); }
                catch (Exception e) { System.out.println("Failed: " + e.getMessage()); }
            }
            case "3" -> {
                try { ioService.exportAll(); System.out.println("Exported."); }
                catch (Exception e) { System.out.println("Failed: " + e.getMessage()); }
            }
            case "4" -> ArrayDemos.run();
            default -> {}
        }
    }

    void backupMenu() {
        // Anonymous inner class demo: tiny callback/confirmation
        Consumer<String> confirm = new Consumer<>() {
            @Override public void accept(String label) {
                System.out.print("Confirm '" + label + "'? (y/n): ");
                String a = sc.nextLine();
                if (!"y".equalsIgnoreCase(a)) throw new RuntimeException("User aborted.");
            }
        };
        try {
            confirm.accept("backup exports");
            backupService.backupExports();
        } catch (Exception e) {
            System.out.println("Backup failed: " + e.getMessage());
        }
    }

    void reportsMenu() {
        System.out.println("""
-- Reports --
1) GPA Distribution
2) Back
> """);
        String c = sc.nextLine();
        if ("1".equals(c)) {
            var dist = transcriptService.gpaDistributionBuckets();
            dist.forEach((k,v) -> System.out.println(k + " -> " + v));
        }
    }

    void printPlatformNote() {
        System.out.println("""
[Platform] Java ME vs SE vs EE
- This app is Java SE (console), not ME (embedded/mobile) or EE (enterprise container).
""");
    }
}
