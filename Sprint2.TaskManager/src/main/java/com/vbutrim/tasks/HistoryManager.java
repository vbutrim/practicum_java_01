package com.vbutrim.tasks;

import java.util.List;

/**
 * @author butrim
 */
public interface HistoryManager {

    void add(TaskId taskId);

    List<TaskId> getRecentTaskIds();

    List<Task> getHistory();
}
