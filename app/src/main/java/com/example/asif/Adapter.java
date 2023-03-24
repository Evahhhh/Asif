package com.example.asif;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asif.R;
import com.example.asif.Task;
import com.example.asif.UrlWebView;

import java.util.List;

public class Adapter extends BaseAdapter {
    private List<Task> listTask;
    private Context context; //état courant de l'application
    private LayoutInflater inflater;

    public Adapter(Context context, List<Task> listTask) {
        this.context = context;
        this.listTask = listTask;
        inflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return listTask.size();
    }

    public Object getItem(int position) {
        return listTask.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            //Initialisation de la vue
            view = (View) inflater.inflate(R.layout.activity_simple_task, parent, false);
        } else {
            view = (View) convertView;
        }

        TextView titleTask = (TextView) view.findViewById(R.id.taskTitleText);
        TextView durationTask = (TextView) view.findViewById(R.id.taskDurationValueText);
        TextView startDateTask = (TextView) view.findViewById(R.id.taskDateValueText);
        TextView descriptionTask = (TextView) view.findViewById(R.id.taskDescriptionText);
        TextView contextTask = (TextView) view.findViewById(R.id.taskContextText);
        TextView statusTask = (TextView) view.findViewById(R.id.taskStatusText);
        TextView urlTask = (TextView) view.findViewById(R.id.taskUrlValueText);
        Button buttonGo = (Button) view.findViewById(R.id.taskUrlButton);
        Button buttonEdit = (Button) view.findViewById(R.id.taskEditButton);
        Button buttonRemove = (Button) view.findViewById(R.id.taskRemoveButton);

        //modification des vues
        titleTask.setText(listTask.get(position).getTitle());
        durationTask.setText(listTask.get(position).getDuration().toString());
        startDateTask.setText(listTask.get(position).getStartDate().toString());
        descriptionTask.setText(listTask.get(position).getDescription());
        contextTask.setText(listTask.get(position).getContext());
        statusTask.setText(listTask.get(position).getStatus());
        urlTask.setText(listTask.get(position).getUrl());

        //gérer le bouton GO URL
        buttonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code à exécuter lorsque le bouton est cliqué


            }
        });

        //gérer le bouton modification
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code à exécuter lorsque le bouton est cliqué


            }
        });

        //gérer le bouton suppression
        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code à exécuter lorsque le bouton est cliqué


            }
        });

        //retourne vue créée
        return view;
    }

}