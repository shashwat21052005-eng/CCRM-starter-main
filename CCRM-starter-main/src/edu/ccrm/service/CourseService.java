package edu.ccrm.service;

import edu.ccrm.domain.*;
import java.util.*;
import java.util.stream.Collectors;

public class CourseService implements Searchable<Course> {
    private final DataStore ds = DataStore.instance();

    public void add(Course c) { ds.courses().put(c.getCode().value(), c); }
    public Optional<Course> byCode(String code) { return Optional.ofNullable(ds.courses().get(code)); }
    public List<Course> all() { return new ArrayList<>(ds.courses().values()); }

    // Stream-based filters
    public List<Course> byInstructor(String name) {
        return all().stream().filter(c -> c.getInstructor()!=null && c.getInstructor().equalsIgnoreCase(name)).collect(Collectors.toList());
    }
    public List<Course> byDepartment(String dept) {
        return all().stream().filter(c -> c.getDepartment()!=null && c.getDepartment().equalsIgnoreCase(dept)).collect(Collectors.toList());
    }
    public List<Course> bySemester(Semester sem) {
        return all().stream().filter(c -> c.getSemester()==sem).collect(Collectors.toList());
    }
}
