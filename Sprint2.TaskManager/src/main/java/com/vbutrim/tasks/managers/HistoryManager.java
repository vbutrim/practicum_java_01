package com.vbutrim.tasks.managers;

import com.vbutrim.tasks.Task;
import com.vbutrim.tasks.TaskId;

import java.util.List;

/**
 * @author butrim
 */
public interface HistoryManager {

    void add(TaskId taskId);

    List<TaskId> getRecentTaskIds();

    List<Task> getHistory();
}
