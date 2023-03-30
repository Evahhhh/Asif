package com.example.asif;

import java.util.Comparator;
import java.util.List;

public class TaskSorter {

    public enum SortType {
        CONTEXT,
        STATUS,
        END_DATE
    }

    public static void sortTasks(List<Task> tasks, SortType sortType) {
        Comparator<Task> comparator = null;
        switch (sortType) {
            case CONTEXT:
                comparator = Comparator.comparing(Task::getContext);
                break;
            case STATUS:
                comparator = Comparator.comparing(Task::getStatus, Comparator.reverseOrder());
                break;
            case END_DATE:
                comparator = Comparator.comparing(Task::getEndDate);
                break;
            default:
                throw new IllegalArgumentException("Invalid sort type: " + sortType);
        }
        tasks.sort(comparator);
    }
}
