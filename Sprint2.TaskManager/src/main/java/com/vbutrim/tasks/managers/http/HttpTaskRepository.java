package com.vbutrim.tasks.managers.http;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.vbutrim.tasks.Task;
import com.vbutrim.tasks.TaskId;
import com.vbutrim.tasks.TaskNotFoundException;
import com.vbutrim.tasks.TaskWithIdAlreadyExistsException;
import com.vbutrim.tasks.managers.TaskRepositoryDto;
import com.vbutrim.tasks.managers.inmemory.TaskRepository;

import java.io.*;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author butrim
 */
public class HttpTaskRepository extends TaskRepository {
    public HttpTaskRepository() {
    }

    @Override
    public void saveNewTask(Task task) throws TaskWithIdAlreadyExistsException {
        super.saveNewTask(task);
        saveTasksToKVStorage();
    }

    @Override
    public void updateExistingTask(Task task) throws TaskNotFoundException {
        super.updateExistingTask(task);
        saveTasksToKVStorage();
    }

    @Override
    public void removeExistingTask(TaskId taskId) throws TaskNotFoundException {
        super.removeExistingTask(taskId);
        saveTasksToKVStorage();
    }

    private void saveTasksToKVStorage() {
        // todo:
    }

    private static class IORuntimeException extends RuntimeException {
        private final IOException original;

        private IORuntimeException(IOException original) {
            this.original = original;
        }

        private IOException getOriginal() {
            return original;
        }
    }
}
