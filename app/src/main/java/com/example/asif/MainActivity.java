package com.example.asif;

import android.app.Activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.asif.utils.TaskUtils;

import java.util.ArrayList;

/**
 * The main activity of the task management application. Displays a list of tasks and provides
 * buttons to sort the tasks by different criteria or add a new task.
 */
public class MainActivity extends Activity implements View.OnClickListener{

    Button newTaskBtn;
    Button statusBtn;
    Button mostUrgentBtn;
    Button contextBtn;
    ArrayList<Task> taskListArray;
    Adapter adapter;

    /**
     * Called when the activity is first created. Sets up the UI elements, reads tasks from the JSON
     * file, and initializes the adapter for the task list.
     *
     * @param savedInstanceState The saved instance state of the activity.
     */
    @Override
    protected void onCreate(android.os.Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the UI elements
        this.newTaskBtn = (Button)findViewById(R.id.newTaskBtn);
        this.newTaskBtn.setOnClickListener(this);
        this.statusBtn = (Button)findViewById(R.id.statusBtn);
        this.statusBtn.setOnClickListener(this);
        this.mostUrgentBtn = (Button)findViewById(R.id.mostUrgentBtn);
        this.mostUrgentBtn.setOnClickListener(this);
        this.contextBtn = (Button)findViewById(R.id.contextBtn);
        this.contextBtn.setOnClickListener(this);

        // Read tasks from the JSON file
        this.taskListArray = TaskUtils.readTasksFromJsonFile(this);

        // Initialize the adapter for the task list
        this.adapter = new Adapter(this, taskListArray);

        // Get the ListView and set the adapter
        ListView list = (ListView)findViewById(R.id.listView);
        list.setAdapter(adapter);
    }

    /**
     * Called when one of the buttons is clicked. Sorts the task list by the selected criteria or
     * starts the activity for creating a new task.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int idChosenBtn = v.getId();
        if (idChosenBtn == R.id.newTaskBtn) {
            // When new task button is clicked
            Intent intent = new Intent(this, NewTask.class);
            startActivity(intent);
        } else if (idChosenBtn == R.id.statusBtn) {
            // When status button is clicked
            this.taskListArray = (ArrayList<Task>) TaskSorter.sortTasks(this.taskListArray, TaskSorter.SortType.STATUS);
            this.adapter.notifyDataSetChanged();
        } else if (idChosenBtn == R.id.mostUrgentBtn) {
            // When more urgent button is clicked
            this.taskListArray = (ArrayList<Task>) TaskSorter.sortTasks(this.taskListArray, TaskSorter.SortType.END_DATE);
            this.adapter.notifyDataSetChanged();
        } else if (idChosenBtn == R.id.contextBtn) {
            // When context button is clicked
            this.taskListArray = (ArrayList<Task>) TaskSorter.sortTasks(this.taskListArray, TaskSorter.SortType.CONTEXT);
            this.adapter.notifyDataSetChanged();
        }
    }

}