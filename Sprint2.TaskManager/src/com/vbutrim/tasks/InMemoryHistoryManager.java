package com.vbutrim.tasks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author butrim
 */
public class InMemoryHistoryManager implements HistoryManager {
    private static final int RECENT_TASKS_COUNT = 10;

    private final TaskRepository taskRepository;
    private final LinkedUniqTaskList historyList;

    public InMemoryHistoryManager(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        this.historyList = new LinkedUniqTaskList();
    }

    @Override
    public void add(TaskId taskId) {
        historyList.addFirst(taskId);
        if (historyList.size() > RECENT_TASKS_COUNT + 1) {
            historyList.removeLast();
        }
    }

    @Override
    public void remove(TaskId taskId) {
        historyList.remove(taskId);
    }

    @Override
    public List<Task> getHistory() {
        List<TaskId> recentlyTaskIds = historyList.asList();
        List<Task> recentTasks = new ArrayList<>(recentlyTaskIds.size());
        for (TaskId taskId : recentlyTaskIds) {
            recentTasks.add(taskRepository.getTaskByIdOrThrow(taskId));
        }
        return Collections.unmodifiableList(recentTasks);
    }

    /**
     * uniqueness --> unique --> uniq
     */
    private static class LinkedUniqTaskList {
        private final LinkedList<TaskId> list = new LinkedList<>();
        private final Map<TaskId, Node<TaskId>> map = new HashMap<>();

        private LinkedUniqTaskList() {
        }

        /**
         * O(1)
         */
        private void addFirst(TaskId taskId) {
            remove(taskId);
            map.put(taskId, list.addFirst(taskId));
        }

        private void removeLast() {
            map.remove(list.removeLast());
        }

        /**
         * O(1)
         */
        private void remove(TaskId taskId) {
            if (map.containsKey(taskId)) {
                list.remove(map.remove(taskId));
            }
        }

        private List<TaskId> asList() {
            return list.asListWithWhile();
        }

        private int size() {
            return list.size();
        }
    }

    private static class LinkedList<E> implements Iterable<E> {
        private Node<E> head = null;
        private Node<E> tail = null;
        private int size = 0;

        private LinkedList() {
        }

        private Node<E> addFirst(E value) {
            Node<E> node = new Node<>(value);
            node.setNext(head);
            if (head != null) {
                head.setPrev(node);
            }
            head = node;

            if (tail == null) {
                tail = head;
            }

            ++size;
            return node;
        }

        /**
         * O(1)
         */
        private E remove(Node<E> node) {
            if (node == head) {
                if (node != tail) {
                    head.getNext().setPrev(null);
                } else {
                    tail = null;
                }
                head = node.getNext();
                node.setNext(null);
            } else if (node == tail) {
                // tail.prev can't be null here, as it should be hand = tail situation, that was handled previously
                tail.getPrev().setNext(null);
                node.setPrev(null);
                tail = tail.getPrev();
            } else {
                node.getPrev().setNext(node.getNext());
                node.getNext().setPrev(node.getPrev());
                node.setPrev(null);
                node.setNext(null);
            }
            --size;
            return node.getValue();
        }

        private E removeLast() {
            return remove(tail);
        }

        private List<E> asListWithWhile() {
            List<E> list = new ArrayList<>(size());
            Node<E> current = head;
            while (current != null) {
                list.add(current.getValue());
                current = current.getNext();
            }
            return Collections.unmodifiableList(list);
        }

        private List<E> asListWithIterator() {
            List<E> list = new ArrayList<>(size());
            Iterator<E> iterator = iterator();
            while (iterator.hasNext()) {
                list.add(iterator.next());
            }
            return Collections.unmodifiableList(list);
        }

        private List<E> asListIteratorWithFunctionalProgramming() {
            List<E> list = new ArrayList<>(size());
            iterator().forEachRemaining(list::add);
            return Collections.unmodifiableList(list);
        }

        private List<E> asListWithIterable() {
            List<E> list = new ArrayList<>(size());
            for (E e : this) {
                list.add(e);
            }
            return Collections.unmodifiableList(list);
        }

        @Override
        public Iterator<E> iterator() {
            return new Iterator<>() {
                private Node<E> current = head;

                @Override
                public boolean hasNext() {
                    return current != null;
                }

                @Override
                public E next() {
                    E value = current.getValue();
                    current = current.getNext();
                    return value;
                }
            };
        }

        private int size() {
            return size;
        }
    }

    private static class Node<E> {
        private final E value;
        private Node<E> prev;
        private Node<E> next;

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

        private void setNext(Node<E> next) {
            this.next = next;
        }

        private void setPrev(Node<E> prev) {
            this.prev = prev;
        }
    }
}
