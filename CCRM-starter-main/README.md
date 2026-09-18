# Campus Course & Records Manager (CCRM)

A console-based Java SE application to manage Students, Courses, Enrollments/Grades, and File operations (import/export/backup).  
**Main class:** `edu.ccrm.cli.CCRMApp`

> This codebase is original and handcrafted for this assignment. It intentionally includes small comments and demonstrations for many syllabus topics (operators, loops, arrays, Streams, NIO.2, exceptions, OOP pillars, Builder/Singleton patterns, enums, lambdas, recursion, nested/inner classes, anonymous classes, etc.).

---

## How to Run

### Prerequisites
- JDK 17+ (tested with 17)
- Any editor/IDE (Eclipse, IntelliJ, VS Code) or just the command line.

### Compile & Run (CLI)
```bash
# from the project root
javac -d out $(find src -name "*.java")
java -ea -cp out edu.ccrm.cli.CCRMApp
```
- The flag `-ea` enables **assertions** (used for invariants).

### Sample Data
Importable CSVs live in `test-data/`:
- `students.csv`
- `courses.csv`

---

## Minimum Demo Flow
1. On start, `AppConfig` (Singleton) loads a default data folder under `./data` (created on demand).
2. CLI menu allows managing Students/Courses/Enrollment/Grades, Import/Export, Backup, Reports, and Exit.
3. Enroll a student, record marks, print transcript (polymorphic `toString()` used).
4. Export data and run Backup (creates a timestamped folder).
5. Program prints a short platform note summarizing Java SE vs ME vs EE.

---

## Evolution of Java (very short bullets)
- 1995: Java 1.0 – write once, run anywhere, JVM.
- 2004–2011: Generics, Enums, Annotations (Java 5/6), improved concurrency.
- 2014–2017: Lambdas/Streams (Java 8), Date/Time API.
- 2018–2021: Local-variable type inference (`var`), modules, switch improvements.
- 2022–2025: Records, pattern matching (instanceof), sealed classes (preview), virtual threads (Loom).

---

## Java ME vs Java SE vs Java EE (Jakarta EE)

| Feature | Java ME | Java SE | Java EE (Jakarta EE) |
|---|---|---|---|
| Target | Embedded/mobile & constrained devices | Desktop/server general-purpose | Enterprise apps (web, transactions, messaging) |
| APIs | Subset, device profiles | Core language + standard libraries | Adds enterprise APIs (Servlets/JAX-RS/JPA/JMS) |
| Packaging | MIDlets | JARs | WAR/EAR |
| Footprint | Small | Medium | Larger |
| Typical Use | Feature phones/IoT | CLI apps, libraries | Enterprise backends |

**Platform note (SE vs ME vs EE):** CCRM is a **Java SE** console app. It does not rely on app servers or enterprise containers.

---

## JDK, JRE, JVM: What/How
- **JVM**: The virtual machine that executes bytecode.
- **JRE**: JVM + standard libraries to *run* Java apps (deprecated as a separate download in modern JDKs).
- **JDK**: JRE + compiler/tools to *develop* Java apps.
- Relationship: Source → `javac` → bytecode (class files) → executed by JVM.

---

## Install & Configure Java on Windows
1. Download and install a JDK (e.g., Adoptium Temurin 17).
2. Add `JAVA_HOME` and update `PATH` to include `bin`.
3. Verify:
   ```bash
   java -version
   javac -version
   ```
4. **Screenshot to provide:** place your verification in `screenshots/` (see placeholders).

### Using Eclipse (quick)
1. File → New → Java Project → Name: `ccrm` → Finish.
2. Create source folders matching `src/` and packages.
3. Add a Run Configuration with Main class `edu.ccrm.cli.CCRMApp`.
4. **Screenshots to add:** Project setup, first run.

---

## Mapping: Syllabus Topic → Where in Code
- Encapsulation: `edu.ccrm.domain.*` fields + getters/setters.
- Inheritance/Abstraction: `Person` (abstract) → `Student`, `Instructor`.
- Polymorphism: `TranscriptService` usage and `toString()` overrides.
- Interfaces: `Persistable`, `Searchable<T>`.
- Diamond resolution: default methods in `Searchable` + explicit override in services.
- Enums (constructors/fields): `Semester`, `Grade` (with points).
- Immutability: `CourseCode` (final, defensive checks).
- Nested classes: `Course.Builder` (static nested).
- Inner class: `Transcript.InnerLine` inside `Transcript`.
- Anonymous inner class: used in CLI confirmation callback.
- Lambdas & Streams: filters/sorts in services; GPA distribution report.
- Design patterns: `AppConfig` (Singleton), `Course.Builder` (Builder).
- Date/Time API: timestamps in enrollments, backup folder names.
- NIO.2: `ImportExportService`, `BackupService`, `FileUtils`.
- Exceptions (checked/unchecked) & custom: `DuplicateEnrollmentException`, `MaxCreditLimitExceededException`.
- Assertions: in constructors (non-null ids, credit bounds).
- Arrays & Arrays utility: `util/ArrayDemos.java`.
- Operators/precedence & bitwise: comments and examples in `ArrayDemos` and `CCRMApp`.
- Upcast/downcast/instanceof: transcript printing (`Person` ref to `Student`).

---

## USAGE (Sample Flow)
1. Start the app, choose **Import Data** to load `test-data/students.csv` and `test-data/courses.csv`.
2. **Manage Students** to list them; **Manage Courses** to list courses and try search/filter.
3. **Enrollment/Grades** → enroll, set marks, print transcript.
4. **Export Data** → write out to `data/export/`.
5. **Backup** → creates `data/backup/YYYYMMDD-HHmmss/` and prints recursive size.

---

## Acknowledgements
- Java standard docs for API references.

> **Note on Integrity:** This repository is provided solely as a learning aid/starter. Please customize heavily and add your own work, screenshots, and demonstrations per your evaluator’s policy.
