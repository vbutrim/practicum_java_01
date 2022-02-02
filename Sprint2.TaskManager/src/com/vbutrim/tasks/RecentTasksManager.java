package com.vbutrim.tasks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author butrim
 */
public class RecentTasksManager {
    private static final int RECENT_TASKS_COUNT = 10;

    private final ArrayList<TaskId> taskIds;

    public RecentTasksManager() {
        this.taskIds = new ArrayList<>();
    }

    public void push(TaskId taskId) {
        if (taskIds.contains(taskId)) {
            taskIds.remove(taskId);
        }
        if (taskIds.size() == RECENT_TASKS_COUNT) {
            taskIds.remove(RECENT_TASKS_COUNT - 1);
        }
        taskIds.add(0, taskId);
    }

    public List<TaskId> getRecentTaskIds() {
        return Collections.unmodifiableList(taskIds);
    }
}
