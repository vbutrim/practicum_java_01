package com.vbutrim.tasks;

/**
 * @author butrim
 */
public class FileBackedTaskManager extends InMemoryTaskManager {
    public FileBackedTaskManager(
            TaskIdGenerator taskIdGenerator,
            FileBackedTaskRepository taskRepository,
            HistoryManager historyManager)
    {
        super(taskIdGenerator, taskRepository, historyManager);
    }
}
