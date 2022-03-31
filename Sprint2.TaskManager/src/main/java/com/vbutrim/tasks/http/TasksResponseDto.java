package com.vbutrim.tasks.http;

import com.vbutrim.tasks.Task;

import java.util.List;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class TasksResponseDto {
    private final List<TaskResponseDto> tasks;

    public TasksResponseDto(List<Task> tasks) {
        this.tasks = tasks
                .stream()
                .map(TaskResponseDto::from)
                .toList();
    }
}
