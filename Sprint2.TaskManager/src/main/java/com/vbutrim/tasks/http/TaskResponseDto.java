package com.vbutrim.tasks.http;

import com.vbutrim.tasks.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author butrim
 */
@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class TaskResponseDto implements Serializable {
    private final int id;
    private final String type;
    private final String name;
    private final String description;
    private final TaskStatus status;
    private final List<Integer> subTaskIds; // can be null
    private final Integer epicTaskId; // can be null

    private TaskResponseDto(
            int id,
            String type,
            String name,
            String description,
            TaskStatus status,
            List<Integer> subTaskIds,
            Integer epicTaskId)
    {
        this.id = id;
        this.type = type;
        this.name = name;
        this.description = description;
        this.status = status;
        this.subTaskIds = subTaskIds;
        this.epicTaskId = epicTaskId;
    }

    private static TaskResponseDto cons(
            TaskId id,
            TaskType type,
            String name,
            String description,
            TaskStatus status,
            List<TaskId> subTaskIds,
            TaskId epicTaskId)
    {
        List<Integer> subTaskIdsAsInts;
        if (subTaskIds == null) {
            subTaskIdsAsInts = null;
        } else {
            subTaskIdsAsInts = new ArrayList<>(subTaskIds.size());
            for (TaskId taskId : subTaskIds) {
                subTaskIdsAsInts.add(taskId.asInteger());
            }
        }

        Integer epicTaskIdsAsInt;
        if (epicTaskId == null) {
            epicTaskIdsAsInt = null;
        } else {
            epicTaskIdsAsInt = epicTaskId.asInteger();
        }

        return new TaskResponseDto(
                id.asInteger(),
                type.value(),
                name,
                description,
                status,
                subTaskIdsAsInts,
                epicTaskIdsAsInt
        );
    }

    public static TaskResponseDto from(Task task) {
        return switch (task.getType()) {
            case EPIC -> TaskResponseDto.cons(
                    task.getId(),
                    task.getType(),
                    task.getName(),
                    task.getDescription(),
                    task.getStatus(),
                    ((EpicTask) task).getSubTaskIds(),
                    null
            );
            case SUB -> TaskResponseDto.cons(
                    task.getId(),
                    task.getType(),
                    task.getName(),
                    task.getDescription(),
                    task.getStatus(),
                    null,
                    ((SubTask) task).getEpicTaskId()
            );
            case SINGLE -> TaskResponseDto.cons(
                    task.getId(),
                    task.getType(),
                    task.getName(),
                    task.getDescription(),
                    task.getStatus(),
                    null,
                    null
            );
        };
    }

    private TaskId getTaskId() {
        return TaskId.from(id);
    }
}
