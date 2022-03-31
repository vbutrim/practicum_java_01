package com.vbutrim.tasks;

import java.util.ArrayList;
import java.util.List;

/**
 * @author butrim
 */
public class EpicTask extends Task {
    private final List<TaskId> subTaskIds;

    EpicTask(TaskId id, String name, String description, List<TaskId> subTaskIds, TaskStatus status) {
        super(id, name, description, status);
        this.subTaskIds = new ArrayList<>(subTaskIds.size());
        this.subTaskIds.addAll(subTaskIds);
    }

    public static EpicTask consNew(TaskId id, String name, String description) {
        return new EpicTask(id, name, description, new ArrayList<>(), TaskStatus.NEW);
    }

    @Override
    public TaskType getType() {
        return TaskType.EPIC;
    }

    public List<TaskId> getSubTaskIds() {
        return subTaskIds;
    }

    public void addSubTaskId(TaskId subTaskId) {
        if (subTaskIds.contains(subTaskId)) {
            return;
        }

        subTaskIds.add(subTaskId);
    }

    public void removeSubTaskId(TaskId subTaskId) {
        if (!subTaskIds.contains(subTaskId)) {
            return;
        }

        subTaskIds.remove(subTaskId);
    }

    @Override
    public String toString() {
        return "EpicTask{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", subTaskIds='" + subTaskIds + '\'' +
                ", status='" + getStatus() + '\'' +
                '}';
    }
}
