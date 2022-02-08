package com.vbutrim.tasks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author butrim
 */
public class InMemoryHistoryManager implements HistoryManager {
    private static final int RECENT_TASKS_COUNT = 10;

    private final LinkedUniqList<TaskId, TaskId> taskIds;
    private final TaskRepository taskRepository;

    public InMemoryHistoryManager(TaskRepository taskRepository) {
        this.taskIds = new LinkedUniqList<>(new Function<TaskId, TaskId>() {
            @Override
            public TaskId apply(TaskId taskId) {
                return taskId;
            }
        });

        this.taskRepository = taskRepository;
    }

    public List<TaskId> getRecentTaskIds() {
        return taskIds.asUnmodifiableList();
    }

    @Override
    public void add(TaskId taskId) {
        taskIds.addFirst(taskId);
        if (taskIds.size() == RECENT_TASKS_COUNT + 1) {
            taskIds.removeLast();
        }
    }

    @Override
    public void remove(TaskId taskId) {
        taskIds.remove(taskId);
    }

    @Override
    public List<Task> getHistory() {
        return getRecentTaskIds()
                .stream()
                .map(taskRepository::getTaskByIdOrThrow)
                .toList();
    }

    private static final class LinkedUniqList<E, TUniqKey> {
        private final LinkedList<E> list = new LinkedList<>();
        private final Map<TUniqKey, Node<E>> map = new HashMap<>();
        private final Function<E, TUniqKey> uniqKeyFunc;

        private LinkedUniqList(Function<E, TUniqKey> uniqKeyFunc) {
            this.uniqKeyFunc = uniqKeyFunc;
        }

        private int size() {
            return list.size();
        }

        private boolean contains(E value) {
            return map.containsKey(uniqKeyFunc.apply(value));
        }

        private void remove(E value) {
            if (!contains(value)) {
                return;
            }

            TUniqKey key = uniqKeyFunc.apply(value);
            list.remove(map.get(key));
            map.remove(key);
        }

        private void removeLast() {
            E removedElement = list.removeLast();
            if (removedElement != null) {
                map.remove(uniqKeyFunc.apply(removedElement));
            }
        }

        private void addFirst(E value) {
            TUniqKey key = uniqKeyFunc.apply(value);
            if (map.containsKey(key)) {
                list.remove(map.get(key));
            }
            map.put(key, list.addFirst(value));
        }

        private List<E> asUnmodifiableList() {
            List<E> list = new ArrayList<>(this.list.size());
            this.list.iterator().forEachRemaining(list::add);
            return Collections.unmodifiableList(list);
        }
    }

    private static final class LinkedList<E> implements Iterable<E> {
        private Node<E> head = null;
        private Node<E> tail = null;
        private int size = 0;

        private LinkedList() {
        }

        private Node<E> addFirst(E value) {
            Node<E> node = new Node<>(value);
            if (head == null) {
                head = node;
                tail = node;
            } else {
                node.setNext(head);
                head.setPrev(node);
                head = node;
            }
            ++size;
            return node;
        }

        private int size() {
            return size;
        }

        private E removeLast() {
            E value = tail == null ? null : tail.getValue();
            remove(tail);
            return value;
        }

        private void remove(Node<E> node) {
            if (node == head) {
                head = head.getNext();
                head.setPrev(null);
                node.setNext(null); // not to keep link on new head at all
            } else if (node == tail) {
                tail = tail.getPrev();
                tail.setNext(null);
                node.setPrev(null);
            } else {
                node.getPrev()
                        .setNext(node.getNext());
                node.getNext()
                        .setPrev(node.getPrev());
            }
            --size;
        }

        @Override
        public Iterator<E> iterator() {
            return new Iterator<>() {
                private Node<E> node = head;

                @Override
                public boolean hasNext() {
                    return node != null;
                }

                @Override
                public E next() {
                    E value = node.getValue();
                    node = node.getNext();
                    return value;
                }
            };
        }
    }

    private static final class Node<E> {
        private final E value;
        private Node<E> prev = null;
        private Node<E> next = null;

        private Node(E value) {
            this.value = value;
        }

        private E getValue() {
            return value;
        }

        private Node<E> getPrev() {
            return prev;
        }

        private Node<E> getNext() {
            return next;
        }

        private void setPrev(Node<E> prev) {
            this.prev = prev;
        }

        private void setNext(Node<E> next) {
            this.next = next;
        }
    }
}
