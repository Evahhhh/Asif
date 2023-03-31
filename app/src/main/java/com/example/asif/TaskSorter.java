package com.example.asif;

import java.util.Comparator;
import java.util.List;

/**
 * A class that sorts a list of tasks based on a specified sort type.
 */
public class TaskSorter {

    /**
     * An enum representing the different sort types available.
     */
    public enum SortType {
        CONTEXT,
        STATUS,
        END_DATE
    }

    /**
     * Sorts the given list of tasks based on the specified sort type.
     * @param tasks The list of tasks to sort.
     * @param sortType The sort type to use.
     * @return The sorted list of tasks.
     * @throws IllegalArgumentException If an invalid sort type is provided.
     */
    public static List<Task> sortTasks(List<Task> tasks, SortType sortType) {
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

        return tasks;

    }
}
