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
    private final HistoryManager historyManager;

    public InMemoryTaskManager(
            TaskIdGenerator taskIdGenerator,
            TaskRepository taskRepository,
            HistoryManager historyManager)
    {
        this.taskIdGenerator = taskIdGenerator;
        this.taskRepository = taskRepository;
        this.historyManager = historyManager;
    }

    @Override
    public EpicTask createNewEpicTask(String name, String description) {
        EpicTask epicTask = EpicTask.consNew(
                taskIdGenerator.getNextFreeTaskId(),
                name,
                description
        );

        taskRepository.saveNewTask(epicTask);

        historyManager.add(epicTask.getId());

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

        historyManager.add(singleTask.getId());

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

        historyManager.add(subTask.getId());

        return subTask;
    }

    @Override
    public void updateTaskName(TaskId taskId, String newName) {
        Task taskToUpdate = taskRepository.getTaskByIdOrThrow(taskId);
        taskToUpdate.setName(newName);
        taskRepository.updateExistingTask(taskToUpdate);

        historyManager.add(taskToUpdate.getId());
    }

    @Override
    public void updateTaskDescription(TaskId taskId, String newDescription) {
        Task taskToUpdate = taskRepository.getTaskByIdOrThrow(taskId);
        taskToUpdate.setName(newDescription);
        taskRepository.updateExistingTask(taskToUpdate);

        historyManager.add(taskId);
    }

    @Override
    public void updateTaskStatus(TaskId taskId, TaskStatus taskStatusToSet) {
        Task taskToUpdate = taskRepository.getTaskByIdOrThrow(taskId);
        taskToUpdate.setStatus(taskStatusToSet);
        taskRepository.updateExistingTask(taskToUpdate);

        historyManager.add(taskId);
    }

    @Override
    public List<Task> getRecentTasks() {
        return historyManager.getHistory();
    }
}
