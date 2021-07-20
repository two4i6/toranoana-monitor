package com.luobo.toranoana_monitor.framework;

import java.util.Collection;
import java.util.Optional;

public interface Dao<T> {
    Optional<T> get(int index);
    Collection<T> getAll();
    Optional<T> add(int id);
    void update(T t);
    void delete(T t);
    int size();
    boolean allValid();
    boolean isEmpty();
}
