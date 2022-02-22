package com.vbutrim.tasks;

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
