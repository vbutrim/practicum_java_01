package com.vbutrim.tasks.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import com.vbutrim.tasks.TaskId;
import com.vbutrim.tasks.managers.TaskManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class TasksServer {
    public static final int PORT = 8888;
    private final TaskManager manager;
    private final HttpServer server;

    private final Gson gson = new GsonBuilder().create();

    public TasksServer(TaskManager manager) throws Exception {
        this.manager = manager;
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        server.createContext("/ping", exchange -> sendSuccess(exchange, true));
        server.createContext("/tasks", this::serveTasks);
    }

    public void start() {
        System.out.println("Running server on port " + PORT);
        server.start();
    }

    protected void serveTasks(HttpExchange h) throws IOException {
        try {
            String method = h.getRequestMethod();
            String path = h.getRequestURI().getPath();
            if (path.equals("/tasks")) {
                switch (method) {
                    case "GET" -> {
                        System.out.println("Get all task");
                        sendObj(h, new TasksResponseDto(manager.getAllTasks()));
                    }
                    case "POST" -> {
                        System.out.println("Creating new task");
                        String taskJson = readText(h);
                    }
                    default -> h.sendResponseHeaders(403, 0);
                }
            } else {
                TaskId id = TaskId.from(Integer.parseInt(path.substring("/tasks/".length())));
                switch (method) {
                    case "GET":
                        System.out.println("Get task #" + id);
                        sendObj(h, manager.getTaskO(id));
                        break;
                    case "PUT":
                        /*System.out.println("Update task #" + id);
                        String taskJson = readText(h);
                        sendSuccess(h, manager.updateTask(taskJson));*/
                        break;
                    case "DELETE":
                        /*System.out.println("Delete task #" + id);
                        sendSuccess(h, manager.delete(id));*/
                        break;
                    default:
                        h.sendResponseHeaders(403, 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e);
        } finally {
            h.close();
        }
    }

    protected String readText(HttpExchange h) throws IOException {
        return new String(h.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
    }

    protected void sendObj(HttpExchange h, Object obj) throws IOException {
        if (obj != null) {
            String resp = gson.toJson(obj);
            h.getResponseHeaders().add("Content-Type", "application/json");
            h.sendResponseHeaders(200, resp.length());
            h.getResponseBody().write(resp.getBytes(StandardCharsets.UTF_8));
        } else {
            h.sendResponseHeaders(404, 0);
        }
    }

    protected void sendSuccess(HttpExchange h, boolean success) throws IOException {
        h.sendResponseHeaders(success ? 200 : 405, 0);
    }
}