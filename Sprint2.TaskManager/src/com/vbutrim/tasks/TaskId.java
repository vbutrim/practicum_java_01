package com.vbutrim.tasks;

import java.util.Objects;

/**
 * @author butrim
 */
public class TaskId {
    private final int id;

    TaskId(int id) {
        this.id = id;
    }

    public static TaskId from(int id) {
        return new TaskId(id);
    }

    public int asInteger() {
        return id;
    }

    public String asString() {
        return String.valueOf(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskId taskId = (TaskId) o;
        return id == taskId.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return asString();
    }
}
