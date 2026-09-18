package edu.ccrm.service;

import edu.ccrm.domain.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

// Singleton-like central in-memory data store (scoped via default constructor but exposed through instance())
public final class DataStore {
    private static final DataStore INSTANCE = new DataStore();
    private final Map<String, Student> students = new ConcurrentHashMap<>();
    private final Map<String, Course> courses = new ConcurrentHashMap<>();
    private final List<Enrollment> enrollments = Collections.synchronizedList(new ArrayList<>());

    private DataStore() {}

    public static DataStore instance() { return INSTANCE; }

    public Map<String, Student> students() { return students; }
    public Map<String, Course> courses() { return courses; }
    public List<Enrollment> enrollments() { return enrollments; }
}
