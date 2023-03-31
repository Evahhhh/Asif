package com.example.asif;

import android.app.Activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.asif.utils.TaskUtils;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener{

    Button newTaskBtn;
    Button statusBtn;
    Button mostUrgentBtn;
    Button contextBtn;
    ArrayList<Task> taskListArray;
    Adapter adapter;

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //gérer le bouton new task
        this.newTaskBtn = (Button)findViewById(R.id.newTaskBtn);
        this.newTaskBtn.setOnClickListener(this);

        //gérer le bouton "Plus Récent"
        this.statusBtn = (Button)findViewById(R.id.statusBtn);
        this.statusBtn.setOnClickListener(this);

        //gérer le bouton "Plus Récent"
        this.mostUrgentBtn = (Button)findViewById(R.id.mostUrgentBtn);
        this.mostUrgentBtn.setOnClickListener(this);

        //gérer le bouton "Plus Récent"
        this.contextBtn = (Button)findViewById(R.id.contextBtn);
        this.contextBtn.setOnClickListener(this);

        //création d'une arraylist de tasks en fonctions des tâches entrées
        this.taskListArray = TaskUtils.readTasksFromJsonFile(this);

        //initalisation de l'adapter pour task
        this.adapter = new Adapter(this, taskListArray);

        //récupération listView
        ListView list = (ListView)findViewById(R.id.listView);

        //passage des données à la listView
        list.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        int idChosenBtn = v.getId();
        if (idChosenBtn == R.id.newTaskBtn) {
            Intent intent = new Intent(this, NewTask.class);
            startActivity(intent);
        } else if (idChosenBtn == R.id.statusBtn) {
            this.taskListArray = (ArrayList<Task>) TaskSorter.sortTasks(this.taskListArray, TaskSorter.SortType.STATUS);
            this.adapter.notifyDataSetChanged();
        } else if (idChosenBtn == R.id.mostUrgentBtn) {
            this.taskListArray = (ArrayList<Task>) TaskSorter.sortTasks(this.taskListArray, TaskSorter.SortType.END_DATE);
            this.adapter.notifyDataSetChanged();
        } else if (idChosenBtn == R.id.contextBtn) {
            this.taskListArray = (ArrayList<Task>) TaskSorter.sortTasks(this.taskListArray, TaskSorter.SortType.CONTEXT);
            this.adapter.notifyDataSetChanged();
        }
    }

}