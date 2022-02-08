package com.vbutrim.tasks;

import java.util.List;

/**
 * @author butrim
 */
public interface HistoryManager {

    void add(TaskId task);

    void remove(TaskId taskId);

    List<Task> getHistory();
}
