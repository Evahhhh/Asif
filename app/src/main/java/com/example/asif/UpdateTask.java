package com.example.asif;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
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

/**
 * This activity allows the user to update an existing task.
 * The user can modify the title, description, status, context, start and end date,
 * as well as the URL of the task. The user can also delete the task.
 */
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

    /**
     * This method initializes the activity by setting up the layout and extracting the task object from the intent.
     * It also sets up the various UI elements, such as buttons, spinners, textviews, and edittexts, and populates them with
     * the appropriate data from the task object. Finally, it sets up event listeners for buttons and date pickers.
     * @param savedInstanceState the saved instance state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        // Extraire l'objet tâche de l'intent
        this.task = (Task) getIntent().getSerializableExtra("task");

        buttonRemove = (Button) findViewById(R.id.buttonRemove);

        if(taskIsDone() == false){
            buttonRemove.setEnabled(false);
        }else{
            buttonRemove.setEnabled(true);
        }

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

        // SET LES VALEURS PAR DEFAUT
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        this.title.setText(task.getTitle());
        this.description.setText(task.getDescription());
        this.spinnerStatus.setSelection(task.getStatusIndex());
        this.spinnerContext.setSelection(task.getContextIndex());
        this.startDateTextView.setText("Start date : " + dateFormat.format(task.getStartDate()));
        this.endDateTextView.setText("End date : " + dateFormat.format(task.getEndDate()));
        this.startDate = task.getStartDate();
        this.endDate = task.getEndDate();
        this.url.setText(task.getUrl());
    }

    /**
     * This method is called when a view has been clicked.
     */
    @Override
    public void onClick(View view) {
        int idChoosenBtn = view.getId();
        if (idChoosenBtn == R.id.buttonRemove) {
            //When remove button is clicked
            TaskUtils.removeTaskFromJsonFile(this, task.getId());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else if(idChoosenBtn == R.id.buttonUpdateTask){
            //When update button is clicked
            if(this.title.getText().length() != 0 && this.description.getText().length() != 0 && this.url.getText().length() != 0 && this.startDate != null && this.endDate != null) {
                // add the state
                String status = this.spinnerStatus.getSelectedItem().toString();
                if (!Task.isInAllStatus(status)) {
                    this.canUpdate = false;
                    Toast.makeText(this, "State isn't accepted", Toast.LENGTH_SHORT).show();
                }

                // add the context
                String context = this.spinnerContext.getSelectedItem().toString();
                if (!Task.isInAllContext(context)) {
                    this.canUpdate = false;
                    Toast.makeText(this, "Context isn't accepted", Toast.LENGTH_SHORT).show();
                } else if (this.endDate.compareTo(startDate) == -1 || this.endDate.compareTo(startDate) == 0) {
                    this.canUpdate = false;
                    Toast.makeText(this, "End date must be greater than start date", Toast.LENGTH_SHORT).show();
                }

                //Update the task if all is accepted
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
            //When start date button is clicked
            try {
                showDatePickerDialog(true);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }else if(idChoosenBtn == R.id.endDate_button){
            //When end date button is clicked
            try {
                showDatePickerDialog(false);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }else if(idChoosenBtn == R.id.buttonVoirUrl){
            //When url button is clicked
            Intent intent = new Intent(this, UrlWebView.class);
            intent.putExtra("url", this.url.getText().toString());
            startActivity(intent);
        }
    }

    /**
     * Shows a DatePickerDialog to allow the user to select a date.
     * @param startbtn a boolean representing whether the dialog is for selecting the start date (true) or end date (false).
     * @throws ParseException if there is an error parsing the selected date.
     */
    private void showDatePickerDialog(boolean startbtn) throws ParseException {
        // Retrieve current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a Calendar object with the selected date
        Calendar selectedDate = Calendar.getInstance();
        selectedDate.set(Calendar.YEAR, year);
        selectedDate.set(Calendar.MONTH, month);
        selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        // Convert calendar to Date object
        Date date = selectedDate.getTime();

        // Format date as "dd/MM/yyyy"
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = dateFormat.format(date);

        // Update the TextView with the formatted date
        if(startbtn) startDateTextView.setText(dateString);
        else endDateTextView.setText(dateString);

        // Display the DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    /**
                     * Callback method called when a date is selected in the DatePickerDialog.
                     * @param view The DatePicker view that was clicked
                     * @param year The year that was selected
                     * @param month The month that was selected, from 0 (January) to 11 (December)
                     * @param dayOfMonth The day of the month that was selected
                     * The method sets the selected date to the corresponding TextView and updates the underlying Date object.
                     * The date is formatted to the "dd/MM/yyyy" format before being displayed.
                     * If the start button was clicked to show the DatePickerDialog, the selected date is set as the start date;
                     * otherwise, it is set as the end date.
                     * If the formatting or parsing of the date fails, a RuntimeException is thrown.
                     */
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Format the chosen date in "dd/MM/yyyy" format
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

                        // Update the TextView with the chosen date formatted
                        if(startbtn) startDateTextView.setText(dateString);
                        else endDateTextView.setText(dateString);
                    }
                }, year, month, dayOfMonth);
        datePickerDialog.show();
    }

    /**
     * Sets the start date of the task to the specified date.
     * @param newDate the new start date for the task
     */
    void setStartDate (Date newDate){
        this.startDate = newDate;
    }

    /**
     * Sets the end date of the task to the specified date.
     * @param newDate the new end date for the task
     */
    void setEndDate (Date newDate){
        this.endDate = newDate;
    }

    /**
     * Checks if the task is marked as done and updates the background color of the "Remove" button accordingly.
     * If the task is done, sets the background color to red, otherwise sets it to gray.
     * @return true if the task is marked as done, false otherwise.
     */
    boolean taskIsDone(){
        if(task.getStatus().equals("Done")){
            int color = Color.parseColor("#9D0A00");
            ColorStateList newColors = ColorStateList.valueOf(color);
            buttonRemove.setBackgroundTintList(newColors);
            return true;
        }
        else{
            int color = Color.parseColor("#C4C4C4");
            ColorStateList newColors = ColorStateList.valueOf(color);
            buttonRemove.setBackgroundTintList(newColors);
            return false;
        }
    }
}
