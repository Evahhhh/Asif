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
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asif.utils.TaskUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NewTask extends Activity implements View.OnClickListener{
    boolean canAdd = true;
    Button cancelButton;
    Button addButton;
    EditText title;
    EditText description;
    Spinner spinnerStatus;
    Spinner spinnerContext;

    //TODO AAAAAAAAAAAAAAAAA
    TextView startDateTextView;
    Button startDateButton;
    TextView endDateTextView;
    Button endDateButton;

    Date startDate = null;
    Date endDate = null;
    //TODO AAAAAAAAAAAAAAAAA

    EditText url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        this.title = findViewById(R.id.editTextTitre);
        this.description = findViewById(R.id.editTextDescription);
        this.spinnerStatus = findViewById(R.id.spinner_status);
        this.spinnerContext = findViewById(R.id.spinner_context);
        this.url = findViewById(R.id.editTextUrl);

        ScrollView scrollView = findViewById(R.id.scrollView);
        scrollView.setAccessibilityPaneTitle(getString(R.string.scrollview_name));



        //TODO AAAAAAAAAAAAAAAAA
        startDateTextView = findViewById(R.id.startDate_textview);
        startDateButton = findViewById(R.id.startDate_button);
        endDateTextView = findViewById(R.id.endDate_textview);
        endDateButton = findViewById(R.id.endDate_button);

        startDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(true);
            }
        });

        endDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(false);
            }
        });
        //TODO AAAAAAAAAAAAAAAAA


        this.addButton = findViewById(R.id.buttonAddTask);
        this.cancelButton = findViewById(R.id.buttonCancel);
        this.addButton.setOnClickListener(this);
        this.cancelButton.setOnClickListener(this);

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

    }


    //TODO AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
    private void showDatePickerDialog(boolean startbtn) {
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

        if(startbtn) this.startDate = date;
        else this.endDate = date;

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

                        // Mettre à jour le TextView avec la date choisie formatée
                        if(startbtn) startDateTextView.setText(dateString);
                        else endDateTextView.setText(dateString);
                    }
                }, year, month, dayOfMonth);
        datePickerDialog.show();
    }
    //TODO AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA

    @Override
    public void onClick(View v) {

        int idChoosenBtn = v.getId();
        if (idChoosenBtn == R.id.buttonAddTask) {
            if(this.title.getText().length() != 0 && this.description.getText().length() != 0 && this.url.getText().length() != 0){

                // ajout du status
                String status = this.spinnerStatus.getSelectedItem().toString();
                if(!Task.isInAllStatus(status)){
                    this.canAdd = false;
                    Toast.makeText(this,"Le statut n'est pas accepté", Toast.LENGTH_SHORT).show();
                }

                // ajout du contexte
                String context = this.spinnerContext.getSelectedItem().toString();
                if(!Task.isInAllContext(context)){
                    this.canAdd = false;
                    Toast.makeText(this,"Le contexte n'est pas accepté", Toast.LENGTH_SHORT).show();
                }

                // ajout de la date de début
                if(this.startDate == null){
                    this.canAdd = false;
                    Toast.makeText(this,"Veuillez entrer une date de début", Toast.LENGTH_SHORT).show();
                }

                // ajout de la date de fin
                if(this.endDate == null){
                    this.canAdd = false;
                    Toast.makeText(this,"Veuillez entrer une date de fin", Toast.LENGTH_SHORT).show();
                }


                //Créer la tâche et la stocker si tous les éléments sont bons
                if(canAdd) {
                    //Ajouter la tâche
                    Task newTask = new Task(this.title.getText().toString(), this.description.getText().toString(), startDate, endDate, context, status, this.url.getText().toString());
                    TaskUtils.writeTaskToJsonFile(this, newTask);
                    Toast.makeText(this, "Tâche ajoutée", Toast.LENGTH_SHORT).show();
                }


            } else {
                Toast.makeText(NewTask.this, "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show();
            }

        } else if (idChoosenBtn == R.id.buttonCancel) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

    }



}
