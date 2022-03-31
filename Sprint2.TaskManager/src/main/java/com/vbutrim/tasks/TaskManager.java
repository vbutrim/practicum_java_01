package com.vbutrim.tasks;

import java.util.List;

/**
 * @author butrim
 */
public interface TaskManager {
    EpicTask createNewEpicTask(String name, String description);

    SingleTask createNewSingleTask(String name, String description);

    SubTask createNewSubTask(TaskId epicTaskId, String name, String description);

    void updateTaskName(TaskId taskId, String newName);

    void updateTaskDescription(TaskId taskId, String newDescription);

    void updateTaskStatus(TaskId taskId, TaskStatus taskStatusToSet);

    List<Task> getRecentTasks();
}
