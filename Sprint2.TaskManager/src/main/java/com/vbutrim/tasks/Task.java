package com.vbutrim.tasks;

/**
 * @author butrim
 */
public abstract class Task {
    private final TaskId id;
    private String name;
    private String description;
    private TaskStatus status;

    Task(TaskId id, String name, String description, TaskStatus status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public TaskId getId() {
        return id;
    }

    public TaskType getType() {
        return TaskType.SUB;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public abstract String toString();
}
