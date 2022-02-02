package com.vbutrim.tasks;

import java.util.HashMap;
import java.util.Map;

/**
 * @author butrim
 */
public class TaskRepository {
    private final Map<TaskId, TaskRepositoryDto> tasks;

    public TaskRepository() {
        this.tasks = new HashMap<>();
    }

    public int getTasksCount() {
        return tasks.size();
    }

    public Task getTaskByIdOrThrow(TaskId taskId) throws TaskNotFoundException {
        if (!tasks.containsKey(taskId)) {
            throw new TaskNotFoundException(taskId);
        }

        return tasks.get(taskId).asTask();
    }

    public void saveNewTask(Task task) throws TaskWithIdAlreadyExistsException {
        if (tasks.containsKey(task.getId())) {
            throw new TaskWithIdAlreadyExistsException(task.getId());
        }
        tasks.put(task.getId(), TaskRepositoryDto.from(task));
    }

    public void updateExistingTask(Task task) throws TaskNotFoundException {
        if (!tasks.containsKey(task.getId())) {
            throw new TaskNotFoundException(task.getId());
        }
        tasks.put(task.getId(), TaskRepositoryDto.from(task));
    }

    /**
     * Nota bene: use this method carefully
     */
    public void removeExistingTask(TaskId taskId) throws TaskNotFoundException {
        if (!tasks.containsKey(taskId)) {
            throw new TaskNotFoundException(taskId);
        }
        tasks.remove(taskId);
    }
}
