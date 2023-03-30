package com.example.asif;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.asif.utils.TaskUtils;


public class UpdateTask extends Activity implements View.OnClickListener {

    Button buttonRemove;
    Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        buttonRemove = (Button) findViewById(R.id.buttonRemove);
        buttonRemove.setOnClickListener(this);

        // Extraire l'objet tâche de l'intent
        this.task = (Task) getIntent().getSerializableExtra("task");
    }

    @Override
    public void onClick(View view) {
        int idChoosenBtn = view.getId();
        System.out.println("HEYYYYYYY" + idChoosenBtn);
        //bouton suppression
        if (idChoosenBtn == R.id.buttonRemove) {
            System.out.println("IDDDDDDDDDDDDD" + task.getId());
            TaskUtils.removeTaskFromJsonFile(this, task.getId());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
