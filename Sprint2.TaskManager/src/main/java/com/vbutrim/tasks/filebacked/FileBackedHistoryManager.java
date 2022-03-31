package com.vbutrim.tasks.filebacked;

import com.vbutrim.tasks.InMemoryHistoryManager;
import com.vbutrim.tasks.TaskId;
import com.vbutrim.tasks.TaskRepository;

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
