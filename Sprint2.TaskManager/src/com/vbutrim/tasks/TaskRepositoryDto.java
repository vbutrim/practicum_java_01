package com.vbutrim.tasks;

import java.util.ArrayList;
import java.util.List;

/**
 * @author butrim
 */
public class TaskRepositoryDto {
    private final int id;
    private final TaskType type;
    private final String name;
    private final String description;
    private final TaskStatus status;
    private final List<Integer> subTaskIds;
    private final Integer epicTaskId;

    private TaskRepositoryDto(
            TaskId id,
            TaskType type,
            String name,
            String description,
            TaskStatus status,
            List<TaskId> subTaskIds,
            TaskId epicTaskId)
    {
        this.id = id.asInteger();
        this.type = type;
        this.name = name;
        this.description = description;
        this.status = status;

        if (subTaskIds == null) {
            this.subTaskIds = null;
        } else {
            this.subTaskIds = new ArrayList<>(subTaskIds.size());
            for (TaskId taskId : subTaskIds) {
                this.subTaskIds.add(taskId.asInteger());
            }
        }

        if (epicTaskId == null) {
            this.epicTaskId = null;
        } else {
            this.epicTaskId = epicTaskId.asInteger();
        }
    }

    public static TaskRepositoryDto from(Task task) {
        switch (task.getType()) {
            case EPIC:
                return new TaskRepositoryDto(
                        task.getId(),
                        task.getType(),
                        task.getName(),
                        task.getDescription(),
                        task.getStatus(),
                        ((EpicTask) task).getSubTaskIds(),
                        null
                );
            case SUB:
                return new TaskRepositoryDto(
                        task.getId(),
                        task.getType(),
                        task.getName(),
                        task.getDescription(),
                        task.getStatus(),
                        null,
                        ((SubTask) task).getEpicTaskId()
                );
            case SINGLE:
                return new TaskRepositoryDto(
                        task.getId(),
                        task.getType(),
                        task.getName(),
                        task.getDescription(),
                        task.getStatus(),
                        null,
                        null
                );
            default:
                throw new IllegalStateException();
        }
    }

    private TaskId getTaskId() {
        return TaskId.from(id);
    }

    public Task asTask() {
        switch (type) {
            case EPIC:
                ArrayList<TaskId> subTaskIds = new ArrayList<>(this.subTaskIds.size());
                for (int subTaskId : this.subTaskIds) {
                    subTaskIds.add(TaskId.from(subTaskId));
                }
                return new EpicTask(
                        getTaskId(),
                        name,
                        description,
                        subTaskIds,
                        status
                );
            case SUB:
                TaskId epicTaskId = TaskId.from(this.epicTaskId);
                return new SubTask(
                        getTaskId(),
                        epicTaskId,
                        name,
                        description,
                        status
                );
            case SINGLE:
                return new SingleTask(
                        getTaskId(),
                        name,
                        description,
                        status
                );
            default:
                throw new IllegalStateException();
        }
    }
}
