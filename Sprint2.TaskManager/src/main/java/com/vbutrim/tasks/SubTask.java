package com.vbutrim.tasks;

/**
 * @author butrim
 */
public class SubTask extends Task {
    private final TaskId epicTaskId;

    SubTask(TaskId id, TaskId epicTaskId, String name, String description, TaskStatus status) {
        super(id, name, description, status);
        this.epicTaskId = epicTaskId;
    }

    public static SubTask consNew(TaskId id, TaskId epicTaskId, String name, String description) {
        return new SubTask(id, epicTaskId, name, description, TaskStatus.NEW);
    }

    public TaskId getEpicTaskId() {
        return epicTaskId;
    }

    @Override
    public TaskType getType() {
        return TaskType.SUB;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "id=" + getId() +
                ", epicTaskId='" + getEpicTaskId() + '\'' +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status='" + getStatus() + '\'' +
                '}';
    }
}
