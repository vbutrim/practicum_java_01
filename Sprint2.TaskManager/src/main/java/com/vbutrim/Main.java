package com.vbutrim;

import com.vbutrim.tasks.Managers;
import com.vbutrim.tasks.test.TaskManagerTest;

public class Main {
    public static void main(String[] args) {
        Managers.getTasksServer()
                .start();

        TaskManagerTest.main(args);
    }
}
