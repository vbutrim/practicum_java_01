package com.vbutrim.tasks;

/**
 * @author butrim
 */
public class TaskNotFoundException extends IllegalArgumentException {
    public TaskNotFoundException(TaskId taskId) {
        super("Task with id " + taskId + " not found");
    }
}
