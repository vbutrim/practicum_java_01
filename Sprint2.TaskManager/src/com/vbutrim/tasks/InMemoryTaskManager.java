package com.vbutrim.tasks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author butrim
 */
public class InMemoryTaskManager implements TaskManager {
    private final TaskIdGenerator taskIdGenerator;
    private final TaskRepository taskRepository;
    private final RecentTasksManager recentTasksManager;

    public InMemoryTaskManager(
            TaskIdGenerator taskIdGenerator,
            TaskRepository taskRepository,
            RecentTasksManager recentTasksManager)
    {
        this.taskIdGenerator = taskIdGenerator;
        this.taskRepository = taskRepository;
        this.recentTasksManager = recentTasksManager;
    }

    @Override
    public EpicTask createNewEpicTask(String name, String description) {
        EpicTask epicTask = EpicTask.consNew(
                taskIdGenerator.getNextFreeTaskId(),
                name,
                description
        );

        taskRepository.saveNewTask(epicTask);

        recentTasksManager.push(epicTask.getId());

        return epicTask;
    }

    @Override
    public SingleTask createNewSingleTask(String name, String description) {
        SingleTask singleTask = SingleTask.consNew(
                taskIdGenerator.getNextFreeTaskId(),
                name,
                description
        );

        taskRepository.saveNewTask(singleTask);

        recentTasksManager.push(singleTask.getId());

        return singleTask;
    }

    @Override
    public SubTask createNewSubTask(TaskId epicTaskId, String name, String description) {
        // 1: find
        Task possibleEpicTask = taskRepository.getTaskByIdOrThrow(epicTaskId);

        if (possibleEpicTask.getType() != TaskType.EPIC) {
            throw new TaskNotFoundException(epicTaskId);
        }

        EpicTask epicTask = (EpicTask) possibleEpicTask;

        // 2: create
        SubTask subTask = SubTask.consNew(
                taskIdGenerator.getNextFreeTaskId(),
                epicTaskId,
                name,
                description
        );

        epicTask.addSubTaskId(subTask.getId());

        taskRepository.updateExistingTask(epicTask);
        taskRepository.saveNewTask(subTask);

        recentTasksManager.push(subTask.getId());

        return subTask;
    }

    @Override
    public void updateTaskName(TaskId taskId, String newName) {
        Task taskToUpdate = taskRepository.getTaskByIdOrThrow(taskId);
        taskToUpdate.setName(newName);
        taskRepository.updateExistingTask(taskToUpdate);

        recentTasksManager.push(taskId);
    }

    @Override
    public void updateTaskDescription(TaskId taskId, String newDescription) {
        Task taskToUpdate = taskRepository.getTaskByIdOrThrow(taskId);
        taskToUpdate.setName(newDescription);
        taskRepository.updateExistingTask(taskToUpdate);

        recentTasksManager.push(taskId);
    }

    @Override
    public void updateTaskStatus(TaskId taskId, TaskStatus taskStatusToSet) {
        Task taskToUpdate = taskRepository.getTaskByIdOrThrow(taskId);
        taskToUpdate.setStatus(taskStatusToSet);
        taskRepository.updateExistingTask(taskToUpdate);

        recentTasksManager.push(taskId);
    }

    @Override
    public List<Task> getRecentTasks() {
        ArrayList<Task> recentTasks = new ArrayList<>();

        for (TaskId recentTaskId : recentTasksManager.getRecentTaskIds()) {
            recentTasks.add(taskRepository.getTaskByIdOrThrow(recentTaskId));
        }

        return Collections.unmodifiableList(recentTasks);
    }

    public List<Task> getRecentTasksWithStreamApi() {
        return recentTasksManager
                .getRecentTaskIds()
                .stream()
                .map(taskRepository::getTaskByIdOrThrow)
                .collect(Collectors.toUnmodifiableList());
    }
}
