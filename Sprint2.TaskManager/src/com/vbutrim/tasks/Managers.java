package com.vbutrim.tasks;

/**
 * @author butrim
 */
public abstract class Managers {
    private static TaskIdGenerator taskIdGenerator;
    private static TaskRepository taskRepository;
    private static RecentTasksManager recentTasksManager;
    private static InMemoryTaskManager inMemoryTaskManager;

    public static TaskIdGenerator getTaskIdGenerator() {
        if (taskIdGenerator == null) {
            taskIdGenerator = new TaskIdGenerator();
        }
        return taskIdGenerator;
    }

    public static TaskRepository getTaskRepository() {
        if (taskRepository == null) {
            taskRepository = new TaskRepository();
        }
        return taskRepository;
    }

    public static RecentTasksManager getRecentTasksManager() {
        if (recentTasksManager == null) {
            recentTasksManager = new RecentTasksManager();
        }
        return recentTasksManager;
    }

    public static TaskManager getDefaultTaskManager() {
        if (inMemoryTaskManager == null) {
            return inMemoryTaskManager = new InMemoryTaskManager(
                    getTaskIdGenerator(),
                    getTaskRepository(),
                    getRecentTasksManager()
            );
        }
        return inMemoryTaskManager;
    }
}
