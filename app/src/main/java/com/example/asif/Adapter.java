package com.example.asif;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asif.utils.TaskUtils;

import java.text.SimpleDateFormat;
import java.util.List;

public class Adapter extends BaseAdapter {
    private List<Task> listTask;
    private Context context; //état courant de l'application
    private LayoutInflater inflater;

    TextView titleTask;
    TextView durationTask;
    TextView startDateTask;
    TextView descriptionTask;
    TextView contextTask;
    TextView statusTask;
    TextView urlTask;
    Button buttonGo;
    Button buttonEdit;


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

        titleTask = (TextView) view.findViewById(R.id.taskTitleText);
        durationTask = (TextView) view.findViewById(R.id.taskDurationValueText);
        startDateTask = (TextView) view.findViewById(R.id.taskDateValueText);
        descriptionTask = (TextView) view.findViewById(R.id.taskDescriptionText);
        contextTask = (TextView) view.findViewById(R.id.taskContextText);
        statusTask = (TextView) view.findViewById(R.id.taskStatusText);
        urlTask = (TextView) view.findViewById(R.id.taskUrlValueText);
        buttonGo = (Button) view.findViewById(R.id.taskUrlButton);
        buttonEdit = (Button) view.findViewById(R.id.taskEditButton);

        //modification des vues
        titleTask.setText(listTask.get(position).getTitle());
        durationTask.setText(listTask.get(position).getDurationInDays() + " jours");

        // Formater la date au format "dd/MM/yyyy"
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = dateFormat.format(listTask.get(position).getStartDate());
        startDateTask.setText(dateString);

        descriptionTask.setText(listTask.get(position).getDescription());
        contextTask.setText(listTask.get(position).getContext());
        statusTask.setText(listTask.get(position).getStatus());
        urlTask.setText(listTask.get(position).getUrl());

        //gérer le bouton GO URL
        buttonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UrlWebView.class);
                intent.putExtra("url", getUrlTask());
                context.startActivity(intent);
            }
        });

        //gérer le bouton modification
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code à exécuter lorsque le bouton est cliqué
                Intent intent = new Intent(context, UpdateTask.class);
                intent.putExtra("task", listTask.get(position));
                context.startActivity(intent);
            }
        });

        //retourne vue créée
        return view;
    }

    String getUrlTask (){
        return this.urlTask.getText().toString();
    }

}