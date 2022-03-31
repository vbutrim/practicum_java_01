package com.vbutrim.tasks.managers.filebacked;

import com.vbutrim.tasks.managers.inmemory.InMemoryHistoryManager;
import com.vbutrim.tasks.TaskId;
import com.vbutrim.tasks.managers.inmemory.TaskRepository;

/**
 * @author butrim
 */
public class FileBackedHistoryManager extends InMemoryHistoryManager {
    public FileBackedHistoryManager(TaskRepository taskRepository) {
        super(taskRepository);
    }

    @Override
    public void add(TaskId taskId) {
        super.add(taskId);
        // todo: save result into file
    }
}
