package com.example.asif;

import android.app.Activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.asif.utils.TaskUtils;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener{

    Button newTaskBtn;

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //gérer le bouton new task
        this.newTaskBtn = (Button)findViewById(R.id.newTaskBtn);
        this.newTaskBtn.setOnClickListener(this);

        //création d'une arraylist de tasks en fonctions des tâches entrées
        ArrayList<Task> taskListArray = TaskUtils.readTasksFromJsonFile(this);

        //initalisation de l'adapter pour task
        Adapter adapter = new Adapter(this, taskListArray);

        //récupération listView
        ListView list = (ListView)findViewById(R.id.listView);

        //passage des données à la listView
        list.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        int idChoosenBtn = v.getId();
        if (idChoosenBtn == R.id.newTaskBtn) {
            Intent intent = new Intent(this, NewTask.class);
            startActivity(intent);
        }
    }
}