package com.vbutrim.tasks.managers.filebacked;

import com.vbutrim.tasks.*;
import com.vbutrim.tasks.managers.TaskRepositoryDto;
import com.vbutrim.tasks.managers.inmemory.TaskRepository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

/**
 * @author butrim
 */
public class FileBackedTaskRepository extends TaskRepository {
    private final Path dbPath;

    public FileBackedTaskRepository(Path dbPath) {
        this.dbPath = dbPath;
    }

    public static FileBackedTaskRepository loadFromFile(Path existingDbPath, Path newDbPath) {
        if (existingDbPath.getFileName().equals(newDbPath.getFileName())) {
            throw new IllegalStateException();
        }

        FileBackedTaskRepository repository = new FileBackedTaskRepository(newDbPath);

        try (BufferedReader reader = new BufferedReader(new FileReader(existingDbPath.toFile()))) {
            while (reader.ready()) {
                repository.saveNewTask(TaskRepositoryDto.from(reader.readLine()).asTask());
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IORuntimeException(e);
        }

        return repository;
    }

    public static FileBackedTaskRepository loadFromFileF(Path existingDbPath, Path newDbPath) throws IOException {
        if (existingDbPath.getFileName().equals(newDbPath.getFileName())) {
            throw new IllegalStateException();
        }

        FileBackedTaskRepository repository = new FileBackedTaskRepository(newDbPath);

        BufferedReader reader = new BufferedReader(new FileReader(existingDbPath.toFile()));
        String line;
        while ((line = reader.readLine()) != null) {
            repository.saveNewTask(TaskRepositoryDto.from(line).asTask());
        }
        reader.close();

        return repository;
    }

    public static void main(String[] args) throws IOException {
        loadFromFileF(
                Path.of("target/classes/existing_tasks_db"),
                Path.of("target/classes/from_existing_tasks_db_f")
        );
    }

    @Override
    public void saveNewTask(Task task) throws TaskWithIdAlreadyExistsException {
        super.saveNewTask(task);
        saveTasksToLocalStorage();
    }

    @Override
    public void updateExistingTask(Task task) throws TaskNotFoundException {
        super.updateExistingTask(task);
        saveTasksToLocalStorage();
    }

    @Override
    public void removeExistingTask(TaskId taskId) throws TaskNotFoundException {
        super.removeExistingTask(taskId);
        saveTasksToLocalStorage();
    }

    private void saveTasksToLocalStorage() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dbPath.toFile()))) {
            for (TaskRepositoryDto task : tasks.values()) {
                writer.write(task.asString());
                writer.write(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
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
