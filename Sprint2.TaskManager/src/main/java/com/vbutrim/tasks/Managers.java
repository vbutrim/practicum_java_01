package com.vbutrim.tasks;

import com.vbutrim.tasks.managers.*;
import com.vbutrim.tasks.managers.filebacked.FileBackedHistoryManager;
import com.vbutrim.tasks.managers.filebacked.FileBackedTaskManager;
import com.vbutrim.tasks.managers.filebacked.FileBackedTaskRepository;
import com.vbutrim.tasks.http.TasksServer;
import com.vbutrim.tasks.managers.inmemory.InMemoryHistoryManager;
import com.vbutrim.tasks.managers.inmemory.InMemoryTaskManager;
import com.vbutrim.tasks.managers.inmemory.TaskRepository;

import java.nio.file.Path;

/**
 * @author butrim
 */
public abstract class Managers {
    private static TaskIdGenerator taskIdGenerator;
    private static TaskRepository taskRepository;
    private static InMemoryHistoryManager inMemoryHistoryManager;
    private static InMemoryTaskManager inMemoryTaskManager;

    private static final Path existingTasksDb = Path.of("target/classes/existing_tasks_db");
    private static final Path newTasksDb = Path.of("target/classes/new_tasks_db");
    private static FileBackedTaskRepository fileBackedTaskRepository;
    private static FileBackedHistoryManager fileBackedHistoryManager;
    private static FileBackedTaskManager fileBackedTaskManager;

    private static TasksServer tasksServer;

    private static TaskIdGenerator getTaskIdGenerator() {
        if (taskIdGenerator == null) {
            taskIdGenerator = new TaskIdGenerator();
        }
        return taskIdGenerator;
    }

    private static TaskRepository getInMemoryTaskRepository() {
        if (taskRepository == null) {
            taskRepository = new TaskRepository();
        }
        return taskRepository;
    }

    private static HistoryManager getInMemoryHistoryManager() {
        if (inMemoryHistoryManager == null) {
            inMemoryHistoryManager = new InMemoryHistoryManager(getInMemoryTaskRepository());
        }
        return inMemoryHistoryManager;
    }

    private static InMemoryTaskManager getInMemoryTaskManager() {
        if (inMemoryTaskManager == null) {
            return inMemoryTaskManager = new InMemoryTaskManager(
                    getTaskIdGenerator(),
                    getInMemoryTaskRepository(),
                    getInMemoryHistoryManager()
            );
        }
        return inMemoryTaskManager;
    }

    private static FileBackedTaskRepository getFileBackedTaskRepository() {
        if (fileBackedTaskRepository == null) {
            return fileBackedTaskRepository = new FileBackedTaskRepository(newTasksDb);
        }
        return fileBackedTaskRepository;
    }

    private static FileBackedHistoryManager getFileBackedHistoryManager() {
        if (fileBackedHistoryManager == null) {
            return fileBackedHistoryManager = new FileBackedHistoryManager(
                    fileBackedTaskRepository
            );
        }

        return fileBackedHistoryManager;
    }

    private static FileBackedTaskManager getFileBackedTaskManager() {
        if (fileBackedTaskManager == null) {
            return fileBackedTaskManager = new FileBackedTaskManager(
                    getTaskIdGenerator(),
                    getFileBackedTaskRepository(),
                    getFileBackedHistoryManager()
            );
        }

        return fileBackedTaskManager;
    }

    public static TaskRepository getDefaultTaskRepository() {
        return getInMemoryTaskRepository();
    }

    public static HistoryManager getDefaultHistoryManager() {
        return getInMemoryHistoryManager();
    }

    public static TaskManager getDefaultTaskManager() {
        return getInMemoryTaskManager();
    }

    public static TasksServer getTasksServer() {
        if (tasksServer == null) {
            try {
                return tasksServer = new TasksServer(getDefaultTaskManager());
            } catch(Exception e) {
                throw new RuntimeException(e);
            }
        }
        return tasksServer;
    }
}
