package com.vbutrim.tasks;

/**
 * @author butrim
 */
public class TaskWithIdAlreadyExistsException extends IllegalStateException {
    public TaskWithIdAlreadyExistsException(TaskId taskId) {
        super("Task with id " + taskId + " already exists");
    }
}
