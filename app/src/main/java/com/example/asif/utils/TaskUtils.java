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

public class TaskUtils {
    private static final String TAG = "TaskUtils";
    private static final String FILE_NAME = "tasks.json";

    /**
     * Writes a single task to a JSON file. Returns true if successful, false otherwise.
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
     * Reads tasks from a JSON file and returns them as an ArrayList. Returns an empty ArrayList if the file does not exist.
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
}
