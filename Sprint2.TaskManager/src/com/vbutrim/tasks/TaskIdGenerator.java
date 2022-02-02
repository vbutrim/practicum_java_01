package com.vbutrim.tasks;

/**
 * @author butrim
 */
public class TaskIdGenerator {
    private int generatedIdsCount;

    public TaskIdGenerator() {
        this.generatedIdsCount = 0;
    }

    public TaskId getNextFreeTaskId() {
        return new TaskId(generatedIdsCount++);
    }
}
