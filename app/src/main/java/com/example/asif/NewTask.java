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

/**
 * An activity that allows the user to create a new task. The user can enter the title,
 * description, context, status, URL, start date, and end date of the task. Once the user
 * fills out all the required fields, they can click on the "Add" button to create the task.
 */
public class NewTask extends Activity implements View.OnClickListener{
    // Instance variables
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

    /**
     * Initializes the activity when it is first created. This method sets up the
     * UI components and event listeners for the activity.
     *
     * @param savedInstanceState the saved instance state
     */
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

        // Set up the context spinner
        this.spinnerContext = findViewById(R.id.spinner_context);
        ArrayAdapter<String> adapterContext = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Task.getAllContext());
        adapterContext.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerContext.setAdapter(adapterContext);

        // Set up the status spinner
        this.spinnerStatus = findViewById(R.id.spinner_status);
        ArrayAdapter<String> adapterStatus = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Task.getAllStatus());
        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapterStatus);

    }

    /**
     * Shows a date picker dialog when the user clicks on the start date or end date button.
     *
     * @param startbtn true if the start date button was clicked, false if the end date button was clicked
     * @throws ParseException if the date cannot be parsed
     */
    private void showDatePickerDialog(boolean startbtn) throws ParseException {
        // Get the current date
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
     * This method is called when a view has been clicked.
     */
    @Override
    public void onClick(View v) {

        int idChoosenBtn = v.getId();
        if (idChoosenBtn == R.id.buttonAddTask) {
            //When add button is clicked
            if(this.title.getText().length() != 0 && this.description.getText().length() != 0 && this.url.getText().length() != 0 && this.startDate != null && this.endDate != null){

                // Add the status
                String status = this.spinnerStatus.getSelectedItem().toString();
                if(!Task.isInAllStatus(status)){
                    this.canAdd = false;
                    Toast.makeText(this,"State isn't accepted", Toast.LENGTH_SHORT).show();
                }

                // Add the context
                String context = this.spinnerContext.getSelectedItem().toString();
                if(!Task.isInAllContext(context)){
                    this.canAdd = false;
                    Toast.makeText(this,"Context isn't accepted", Toast.LENGTH_SHORT).show();
                }else if(this.endDate.compareTo(startDate) == -1 || this.endDate.compareTo(startDate) == 0){
                    this.canAdd = false;
                    Toast.makeText(this,"End date must be greater than start date", Toast.LENGTH_SHORT).show();
                }


                //Create the task and store it if all elements are good
                if(canAdd) {
                    // Add the task
                    Task newTask = new Task(this.title.getText().toString(), this.description.getText().toString(), startDate, endDate, context, status, this.url.getText().toString());
                    TaskUtils.writeTaskToJsonFile(this, newTask);
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
            } else {
                Toast.makeText(NewTask.this, "Please complete all fields", Toast.LENGTH_LONG).show();
            }

        } else if (idChoosenBtn == R.id.buttonCancel) {
            //When cancel button is clicked
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (idChoosenBtn == R.id.buttonVoirUrl){
            //When url button is clicked
            Intent intent = new Intent(this, UrlWebView.class);
            intent.putExtra("url", this.url.getText().toString());
            startActivity(intent);
        }

    }

    /**
     * Sets the start date of the event to the specified date.
     * @param newDate the new start date for the event
     */
    void setStartDate (Date newDate){
        this.startDate = newDate;
    }

    /**
     * Sets the end date of the event to the specified date.
     * @param newDate the new end date for the event
     */
    void setEndDate (Date newDate){
        this.endDate = newDate;
    }
}
