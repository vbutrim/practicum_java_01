package com.vbutrim.tasks.managers.filebacked;

import com.vbutrim.tasks.managers.HistoryManager;
import com.vbutrim.tasks.managers.inmemory.InMemoryTaskManager;
import com.vbutrim.tasks.TaskIdGenerator;

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
