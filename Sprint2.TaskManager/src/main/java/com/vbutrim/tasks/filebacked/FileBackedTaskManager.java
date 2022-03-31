package com.vbutrim.tasks.filebacked;

import com.vbutrim.tasks.HistoryManager;
import com.vbutrim.tasks.InMemoryTaskManager;
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
