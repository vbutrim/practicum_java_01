package com.vbutrim.tasks.test;

import com.vbutrim.tasks.EpicTask;
import com.vbutrim.tasks.Managers;
import com.vbutrim.tasks.SubTask;
import com.vbutrim.tasks.Task;
import com.vbutrim.tasks.TaskManager;
import com.vbutrim.tasks.TaskRepository;
import com.vbutrim.tasks.TaskStatus;

/**
 * @author butrim
 */
public class TaskManagerTest {
    public static void main(String[] args) {
        TaskRepository taskRepository = Managers.getDefaultTaskRepository();
        TaskManager taskManager = Managers.getDefaultTaskManager();

        System.out.println("InMemoryTaskManager is initialized");
        System.out.println("Total tasks count: " + taskRepository.getTasksCount());

        System.out.println("\nCreating new epic task...");
        EpicTask createdEpicTask = taskManager.createNewEpicTask("Morning preparations", "Daily routine");
        System.out.println("Created epic task: " + createdEpicTask);

        System.out.println("\nCreating new sub task of epic task...");
        SubTask createdSubTask = taskManager.createNewSubTask(createdEpicTask.getId(), "Feed cat", "");
        System.out.println("Created sub task: " + createdSubTask);

        Task task = taskRepository.getTaskByIdOrThrow(createdEpicTask.getId());
        System.out.println("\nFinally epic task: " + task);

        System.out.println("\nTotal tasks count: " + taskRepository.getTasksCount());

        System.out.println("\nUpdating subTask status...");
        taskManager.updateTaskStatus(createdSubTask.getId(), TaskStatus.IN_PROGRESS);
        System.out.println("Finally subTask: " + taskRepository.getTaskByIdOrThrow(createdSubTask.getId()));

        System.out.println("\n\nRecent tasks:");
        for (Task recentTask : taskManager.getRecentTasks()) {
            System.out.println(recentTask);
        }
    }
}
