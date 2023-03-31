package com.example.asif.utils;

import android.content.Context;
import android.util.Log;

import com.example.asif.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * This class contains utility methods for working with tasks.
 */
public class TaskUtils {
    private static final String TAG = "TaskUtils";
    private static final String FILE_NAME = "tasks.json";

    /**
     * Writes a single task to a JSON file. Returns true if successful, false otherwise.
     *
     * @param context The application context.
     * @param task The task to be written to the JSON file.
     * @return true if the task was successfully written to the JSON file, false otherwise.
     */
    public static boolean writeTaskToJsonFile(Context context, Task task) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            File file = new File(context.getFilesDir(), FILE_NAME);

            // Create the file if it doesn't exist
            if (!file.exists()) {
                file.createNewFile();
            }

            // Read the existing tasks from the file
            ArrayList<Task> tasks = readTasksFromJsonFile(context);

            // Add the new task to the list of tasks
            tasks.add(task);

            // Write the updated task list to the file
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            gson.toJson(tasks, writer);
            writer.close();

            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error writing task to JSON file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Removes a task from the JSON file. Returns true if successful, false otherwise.
     *
     * @param context The application context.
     * @param idTask The unique ID of the task to be removed.
     * @return true if the task was successfully removed from the JSON file, false otherwise.
     */
    public static boolean removeTaskFromJsonFile(Context context, String idTask) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            File file = new File(context.getFilesDir(), FILE_NAME);

            // Create the file if it doesn't exist
            if (!file.exists()) {
                Log.e(TAG,"JSON File doesn't exist");
                return false;
            }else{
                // Read the existing tasks from the file
                ArrayList<Task> tasks = readTasksFromJsonFile(context);

                // Remove the task from the list of tasks
                for(Task task : tasks) {
                    if(task.getId().equals(idTask)){
                        tasks.remove(task);
                    }
                }

                // Write the updated task list to the file
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                gson.toJson(tasks, writer);
                writer.close();

                return true;
            }
        } catch (Exception e) {
            Log.e(TAG, "Error removing task from JSON file: " + e.getMessage());
            return false;
        }
    }


    /**
     * Reads tasks from a JSON file and returns them as an ArrayList. Returns an empty ArrayList if the file does not exist.
     *
     * @param context The application context.
     * @return an ArrayList of tasks read from the JSON file.
     */
    public static ArrayList<Task> readTasksFromJsonFile(Context context) {
        try {
            Gson gson = new Gson();
            File file = new File(context.getFilesDir(), FILE_NAME);

            // Create the file if it doesn't exist
            if (!file.exists()) {
                file.createNewFile();
            }

            // Read the tasks from the file
            BufferedReader reader = new BufferedReader(new FileReader(file));
            Type taskListType = new TypeToken<ArrayList<Task>>(){}.getType();
            ArrayList<Task> tasks = gson.fromJson(reader, taskListType);
            reader.close();

            if (tasks == null) {
                tasks = new ArrayList<>();
            }

            return tasks;
        } catch (Exception e) {
            Log.e(TAG, "Error reading tasks from JSON file: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Removes all tasks from the JSON file. Returns true if successful, false otherwise.
     * @param context The application context.
     * @return true if all tasks have been removed successfully, false otherwise.
     */
    public static boolean removeAllTaskFromJsonFile(Context context) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            File file = new File(context.getFilesDir(), FILE_NAME);

            // Create the file if it doesn't exist
            if (!file.exists()) {
                Log.e(TAG,"JSON File doesn't exist");
                return false;
            }else{
                // Read the existing tasks from the file
                ArrayList<Task> tasks = new ArrayList<>();

                // Write the updated task list to the file
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                gson.toJson(tasks, writer);
                writer.close();

                return true;
            }
        } catch (Exception e) {
            Log.e(TAG, "Error removing task from JSON file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Modifies an existing task in the JSON file using its unique ID.
     * @param context The application context.
     * @param modifiedTask The modified task to write to the JSON file.
     * @return true if the task has been modified successfully, false otherwise.
     */
    public static boolean modifyTaskInJsonFile(Context context, Task modifiedTask) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            File file = new File(context.getFilesDir(), FILE_NAME);

            // Create the file if it doesn't exist
            if (!file.exists()) {
                file.createNewFile();
            }

            // Read the existing tasks from the file
            ArrayList<Task> tasks = readTasksFromJsonFile(context);

            // Find the task to modify in the list of tasks using its unique ID
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                if (task.getId().equals(modifiedTask.getId())) {
                    tasks.set(i, modifiedTask);
                    break;
                }
            }

            // Write the updated task list to the file
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            gson.toJson(tasks, writer);
            writer.close();

            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error modifying task in JSON file: " + e.getMessage());
            return false;
        }
    }
}
