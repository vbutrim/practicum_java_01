package com.vbutrim.tasks;

/**
 * @author butrim
 */
public class SingleTask extends Task {

    public SingleTask(TaskId id, String name, String description, TaskStatus status) {
        super(id, name, description, status);
    }

    public static SingleTask consNew(TaskId id, String name, String description) {
        return new SingleTask(id, name, description, TaskStatus.NEW);
    }

    @Override
    public TaskType getType() {
        return TaskType.SINGLE;
    }

    @Override
    public String toString() {
        return "SingleTask{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status='" + getStatus() + '\'' +
                '}';
    }
}
