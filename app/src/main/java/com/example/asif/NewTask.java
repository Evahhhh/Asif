package com.example.asif;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
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


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewTask extends Activity implements View.OnClickListener{
    boolean canAdd = true;
    Button cancelButton;
    Button addButton;
    Button urlButton;
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
        setContentView(R.layout.activity_new_task);

        this.title = findViewById(R.id.editTextTitre);
        this.description = findViewById(R.id.editTextDescription);
        this.spinnerStatus = findViewById(R.id.spinner_status);
        this.spinnerContext = findViewById(R.id.spinner_context);
        this.url = findViewById(R.id.editTextUrl);

        ScrollView scrollView = findViewById(R.id.scrollView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            scrollView.setAccessibilityPaneTitle(getString(R.string.scrollview_name));
        }


        startDateTextView = findViewById(R.id.startDate_textview);
        startDateButton = findViewById(R.id.startDate_button);
        endDateTextView = findViewById(R.id.endDate_textview);
        endDateButton = findViewById(R.id.endDate_button);

        startDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    showDatePickerDialog(true);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        endDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    showDatePickerDialog(false);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        this.addButton = findViewById(R.id.buttonAddTask);
        this.cancelButton = findViewById(R.id.buttonCancel);
        this.urlButton = findViewById(R.id.buttonVoirUrl);
        this.addButton.setOnClickListener(this);
        this.cancelButton.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {

        int idChoosenBtn = v.getId();
        if (idChoosenBtn == R.id.buttonAddTask) {
            if(this.title.getText().length() != 0 && this.description.getText().length() != 0 && this.url.getText().length() != 0 && this.startDate != null && this.endDate != null){

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
                }else if(this.endDate.compareTo(startDate) == -1 || this.endDate.compareTo(startDate) == 0){
                    System.out.println("COMPARTE / " + this.endDate.compareTo(startDate));
                    this.canAdd = false;
                    Toast.makeText(this,"La date de fin doit être supérieure à la date de début", Toast.LENGTH_SHORT).show();
                }




                //Créer la tâche et la stocker si tous les éléments sont bons
                if(canAdd) {
                    //Ajouter la tâche
                    Task newTask = new Task(this.title.getText().toString(), this.description.getText().toString(), startDate, endDate, context, status, this.url.getText().toString());
                    TaskUtils.writeTaskToJsonFile(this, newTask);
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }



            } else {
                Toast.makeText(NewTask.this, "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show();
            }

        } else if (idChoosenBtn == R.id.buttonCancel) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (idChoosenBtn == R.id.buttonVoirUrl){
            Intent intent = new Intent(this, UrlWebView.class);
            intent.putExtra("url", this.url.getText().toString());
            startActivity(intent);
        }

    }

    void setStartDate (Date newDate){
        this.startDate = newDate;
    }

    void setEndDate (Date newDate){
        this.endDate = newDate;
    }

}
