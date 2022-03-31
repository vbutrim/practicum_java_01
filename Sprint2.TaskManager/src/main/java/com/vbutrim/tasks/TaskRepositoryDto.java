package com.vbutrim.tasks;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author butrim
 */
public class TaskRepositoryDto implements Serializable {
    private static final String FIELDS_DELIMITER = ";";
    private static final String NULL_STRING = "null";

    private final int id;
    private final TaskType type;
    private final String name;
    private final String description;
    private final TaskStatus status;
    private final List<Integer> subTaskIds; // can be null
    private final Integer epicTaskId; // can be null

    private TaskRepositoryDto(
            int id,
            TaskType type,
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

    private static TaskRepositoryDto cons(
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

        return new TaskRepositoryDto(
                id.asInteger(),
                type,
                name,
                description,
                status,
                subTaskIdsAsInts,
                epicTaskIdsAsInt
        );
    }

    public static TaskRepositoryDto from(Task task) {
        switch (task.getType()) {
            case EPIC:
                return TaskRepositoryDto.cons(
                        task.getId(),
                        task.getType(),
                        task.getName(),
                        task.getDescription(),
                        task.getStatus(),
                        ((EpicTask) task).getSubTaskIds(),
                        null
                );
            case SUB:
                return TaskRepositoryDto.cons(
                        task.getId(),
                        task.getType(),
                        task.getName(),
                        task.getDescription(),
                        task.getStatus(),
                        null,
                        ((SubTask) task).getEpicTaskId()
                );
            case SINGLE:
                return TaskRepositoryDto.cons(
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

    public static TaskRepositoryDto from(String task) {
        String[] fields = task.split(FIELDS_DELIMITER);
        return new TaskRepositoryDto(
                Integer.parseInt(fields[0]),
                TaskType.byValueOrThrow(fields[1]),
                NULL_STRING.equals(fields[2]) ? "" : fields[2],
                NULL_STRING.equals(fields[3]) ? "" : fields[3],
                TaskStatus.byValueOrThrow(fields[4]),
                StringUtils.parseIntegerList(fields[5]),
                NULL_STRING.equals(fields[6]) ? null : Integer.parseInt(fields[6])
        );
    }

    public String asString() {
        return String.format(
                "%s;%s;%s;%s;%s;%s;%s",
                id,
                type.value(),
                name.isEmpty() ? NULL_STRING : name,
                description.isEmpty() ? NULL_STRING : description,
                status.value(),
                StringUtils.toString(subTaskIds),
                epicTaskId != null ? Integer.toString(epicTaskId) : NULL_STRING
        );
    }
}
