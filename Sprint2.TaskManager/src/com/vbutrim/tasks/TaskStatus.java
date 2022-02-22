package com.vbutrim.tasks;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author butrim
 */
@SuppressWarnings("unused")
public enum TaskStatus implements StringEnum {
    NEW("new"),
    IN_PROGRESS("in_progress"),
    DONE("done")
    ;

    private static final Map<String, TaskStatus> BY_VALUE = Arrays
            .stream(TaskStatus.values())
            .collect(Collectors.toMap(TaskStatus::value, x -> x));

    private final String value;

    TaskStatus(String value) {
        this.value = value;
    }

    public static TaskStatus byValueOrThrow(String value) {
        if (!BY_VALUE.containsKey(value)) {
            throw new IllegalArgumentException("unknown TaskType: " + value);
        }
        return BY_VALUE.get(value);
    }

    @Override
    public String value() {
        return value;
    }
}
