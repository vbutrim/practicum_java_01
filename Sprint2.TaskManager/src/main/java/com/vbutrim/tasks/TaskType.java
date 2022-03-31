package com.vbutrim.tasks;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author butrim
 */
public enum TaskType implements StringEnum {
    EPIC("epic"),
    SUB("sub"),
    SINGLE("single")
    ;

    private static final Map<String, TaskType> BY_VALUE = Arrays
            .stream(TaskType.values())
            .collect(Collectors.toMap(TaskType::value, x -> x));

    private final String value;

    TaskType(String value) {
        this.value = value;
    }

    public static TaskType byValueOrThrow(String value) {
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
