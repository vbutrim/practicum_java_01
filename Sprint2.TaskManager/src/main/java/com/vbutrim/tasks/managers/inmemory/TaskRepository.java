package com.vbutrim.tasks.managers.inmemory;

import com.vbutrim.tasks.*;
import com.vbutrim.tasks.managers.TaskRepositoryDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author butrim
 */
public class TaskRepository {
    protected final Map<TaskId, TaskRepositoryDto> tasks;

    public TaskRepository() {
        this.tasks = new HashMap<>();
    }

    public int getTasksCount() {
        return tasks.size();
    }

    public Optional<Task> getTaskByIdO(TaskId taskId) {
        if (!tasks.containsKey(taskId)) {
            return Optional.empty();
        }

        return Optional.of(tasks.get(taskId).asTask());
    }

    public Task getTaskByIdOrThrow(TaskId taskId) throws TaskNotFoundException {
        return getTaskByIdO(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
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

    public List<Task> getAllTasks() {
        return tasks
                .values()
                .stream()
                .map(TaskRepositoryDto::asTask)
                .toList();
    }
}
