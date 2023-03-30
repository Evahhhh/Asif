package com.example.asif;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asif.utils.TaskUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class UpdateTask extends Activity implements View.OnClickListener {

    boolean canUpdate = true;
    Button buttonRemove;
    Button buttonUpdateTask;
    Button urlButton;
    Task task;

    EditText title;
    EditText description;
    Spinner spinnerStatus;
    Spinner spinnerContext;

    TextView startDateTextView;
    Button startDateButton;
    TextView endDateTextView;
    Button endDateButton;

    Date startDate = null;
    Date endDate = null;

    EditText url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        // Extraire l'objet tâche de l'intent
        this.task = (Task) getIntent().getSerializableExtra("task");

        buttonRemove = (Button) findViewById(R.id.buttonRemove);
        buttonRemove.setOnClickListener(this);

        buttonUpdateTask = (Button) findViewById(R.id.buttonUpdateTask);
        buttonUpdateTask.setOnClickListener(this);

        this.title = findViewById(R.id.editTextTitre);
        this.description = findViewById(R.id.editTextDescription);
        this.spinnerStatus = findViewById(R.id.spinner_status);
        this.spinnerContext = findViewById(R.id.spinner_context);
        this.url = findViewById(R.id.editTextUrl);
        startDateTextView = findViewById(R.id.startDate_textview);
        startDateButton = findViewById(R.id.startDate_button);
        startDateButton.setOnClickListener(this);
        endDateTextView = findViewById(R.id.endDate_textview);
        endDateButton = findViewById(R.id.endDate_button);
        endDateButton.setOnClickListener(this);
        this.urlButton = findViewById(R.id.buttonVoirUrl);
        this.urlButton.setOnClickListener(this);

        //spinnerContext
        this.spinnerContext = findViewById(R.id.spinner_context);
        ArrayAdapter<String> adapterContext = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Task.getAllContext());
        adapterContext.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerContext.setAdapter(adapterContext);

        //spinnerStatus
        this.spinnerStatus = findViewById(R.id.spinner_status);
        ArrayAdapter<String> adapterStatus = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Task.getAllStatus());
        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapterStatus);

        // SET LES TEXTES
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        this.title.setText(task.getTitle());
        this.description.setText(task.getDescription());
        this.spinnerStatus.setSelection(task.getStatusIndex());
        this.spinnerContext.setSelection(task.getContextIndex());
        this.startDateTextView.setText("Date de début : " + dateFormat.format(task.getStartDate()));
        this.endDateTextView.setText("Date de fin : " + dateFormat.format(task.getEndDate()));
        this.url.setText(task.getUrl());
    }

    @Override
    public void onClick(View view) {
        int idChoosenBtn = view.getId();
        //bouton suppression
        if (idChoosenBtn == R.id.buttonRemove) {
            TaskUtils.removeTaskFromJsonFile(this, task.getId());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else if(idChoosenBtn == R.id.buttonUpdateTask){
            //BOUTON MODIFIER
            if(this.title.getText().length() != 0 && this.description.getText().length() != 0 && this.url.getText().length() != 0 && this.startDate != null && this.endDate != null) {

                // ajout du status
                String status = this.spinnerStatus.getSelectedItem().toString();
                if (!Task.isInAllStatus(status)) {
                    this.canUpdate = false;
                    Toast.makeText(this, "Le statut n'est pas accepté", Toast.LENGTH_SHORT).show();
                }

                // ajout du contexte
                String context = this.spinnerContext.getSelectedItem().toString();
                if (!Task.isInAllContext(context)) {
                    this.canUpdate = false;
                    Toast.makeText(this, "Le contexte n'est pas accepté", Toast.LENGTH_SHORT).show();
                } else if (this.endDate.compareTo(startDate) == -1 || this.endDate.compareTo(startDate) == 0) {
                    System.out.println("COMPARTE / " + this.endDate.compareTo(startDate));
                    this.canUpdate = false;
                    Toast.makeText(this, "La date de fin doit être supérieure à la date de début", Toast.LENGTH_SHORT).show();
                }

                //Modifier la tâche et la stocker si tous les éléments sont bons
                if(canUpdate) {
                    this.task.setTitle(this.title.getText().toString());
                    this.task.setDescription(this.description.getText().toString());
                    this.task.setStartDate(this.startDate);
                    this.task.setEndDate(this.endDate);
                    this.task.setStatus(status);
                    this.task.setContext(context);
                    this.task.setUrl(this.url.getText().toString());
                    this.task.setDuration(this.startDate, this.endDate);

                    TaskUtils.modifyTaskInJsonFile(this,task);
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
            }
        }else if(idChoosenBtn == R.id.startDate_button){
            try {
                showDatePickerDialog(true);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }else if(idChoosenBtn == R.id.endDate_button){
            try {
                showDatePickerDialog(false);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }else if(idChoosenBtn == R.id.buttonVoirUrl){
            Intent intent = new Intent(this, UrlWebView.class);
            intent.putExtra("url", this.url.getText().toString());
            startActivity(intent);
        }
    }

    private void showDatePickerDialog(boolean startbtn) throws ParseException {
        // Récupère la date actuelle
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Créer un objet Calendar avec la date sélectionnée
        Calendar selectedDate = Calendar.getInstance();
        selectedDate.set(Calendar.YEAR, year);
        selectedDate.set(Calendar.MONTH, month);
        selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        // Convertir le calendrier en objet Date
        Date date = selectedDate.getTime();

        // Formater la date au format "dd/MM/yyyy"
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = dateFormat.format(date);

        // Mettre à jour le TextView avec la date formatée
        if(startbtn) startDateTextView.setText(dateString);
        else endDateTextView.setText(dateString);

        // Afficher le DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Formater la date choisie au format "dd/MM/yyyy"
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(Calendar.YEAR, year);
                        selectedDate.set(Calendar.MONTH, month);
                        selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        Date date = selectedDate.getTime();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        String dateString = dateFormat.format(date);

                        try {
                            date = dateFormat.parse(dateString);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }

                        if(startbtn) setStartDate(date);
                        else setEndDate(date);

                        // Mettre à jour le TextView avec la date choisie formatée
                        if(startbtn) startDateTextView.setText(dateString);
                        else endDateTextView.setText(dateString);
                    }
                }, year, month, dayOfMonth);
        datePickerDialog.show();
    }

    void setStartDate (Date newDate){
        this.startDate = newDate;
    }

    void setEndDate (Date newDate){
        this.endDate = newDate;
    }
}
