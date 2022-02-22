package com.vbutrim.cmd;

import java.nio.file.Files;
import java.util.Scanner;

import com.vbutrim.tasks.Task;
import com.vbutrim.tasks.Managers;
import com.vbutrim.tasks.TaskId;

/**
 * @author butrim
 */
public class CommandLineManager {
    private final Scanner scanner = new Scanner(System.in);

    public CommandLineManager() {
    }

    public void handleCmdAndProcessCommands() {
        while (true) {
            // todo: print menu
            System.out.println("Введите команду");
            int cmd = scanner.nextInt();
            if (cmd == 1) {
                    Task task = Managers
                            .getDefaultTaskRepository()
                            .getTaskByIdOrThrow(TaskId.from(4));
                    System.out.println(task);
            }
        }
    }
}
