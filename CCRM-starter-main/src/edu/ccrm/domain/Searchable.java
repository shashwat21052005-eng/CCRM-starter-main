package edu.ccrm.domain;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface Searchable<T> {
    List<T> all();
    default List<T> search(Predicate<T> predicate) {
        return all().stream().filter(predicate).collect(Collectors.toList());
    }
    default List<T> firstN(int n) {
        return all().stream().limit(n).collect(Collectors.toList());
    }
}
