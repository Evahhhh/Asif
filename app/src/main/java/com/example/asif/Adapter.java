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

/**
 * Adapter class is used to display the list of tasks in a ListView.
 * It extends the BaseAdapter class.
 * It inflates the view of the activity_simple_task layout file for each item in the list.
 * It also manages the click events for the Go URL button and the Edit button.
 * The Adapter class has a constructor that accepts the context of the application and the list of tasks to display.
 * It also has several methods to get the count of items, to get an item by its position, and to get the ID of an item.
 * The getView method is used to create and populate the view for each item in the list.
 * The getUrlTask method returns the URL of a task.
 */
public class Adapter extends BaseAdapter {
    private List<Task> listTask;
    private Context context; //current state of the application
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

    /**
     * Constructs a new Adapter object.
     * @param context the current state of the application.
     * @param listTask the list of tasks to be displayed in the adapter.
     */
    public Adapter(Context context, List<Task> listTask) {
        this.context = context;
        this.listTask = listTask;
        inflater = LayoutInflater.from(context);
    }

    /**
     * Returns the number of items in the list of tasks.
     * @return the number of items in the list of tasks
     */
    public int getCount() {
        return listTask.size();
    }

    /**
     * Returns the task item at the specified position in the list.
     * @param position the position of the task item to return
     * @return the task item at the specified position in the list
     */
    public Object getItem(int position) {
        return listTask.get(position);
    }

    /**
     * Returns the row ID of the task item at the specified position in the list.
     * @param position the position of the task item in the list
     * @return the row ID of the task item at the specified position in the list
     */
    public long getItemId(int position) {
        return position;
    }

    /**
     * Returns a view that displays the data at the specified position in the list of tasks.
     * If a recycled view is not available, a new view is created.
     * @param position the position of the item within the adapter's data set of the item whose view we want
     * @param convertView the old view to reuse, if possible
     * @param parent the parent view that this view will eventually be attached to
     * @return a view corresponding to the data at the specified position
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            // Initialize the view
            view = (View) inflater.inflate(R.layout.activity_simple_task, parent, false);
        } else {
            view = (View) convertView;
        }

        // Retrieve views from the inflated layout
        titleTask = (TextView) view.findViewById(R.id.taskTitleText);
        durationTask = (TextView) view.findViewById(R.id.taskDurationValueText);
        startDateTask = (TextView) view.findViewById(R.id.taskDateValueText);
        descriptionTask = (TextView) view.findViewById(R.id.taskDescriptionText);
        contextTask = (TextView) view.findViewById(R.id.taskContextText);
        statusTask = (TextView) view.findViewById(R.id.taskStatusText);
        urlTask = (TextView) view.findViewById(R.id.taskUrlValueText);
        buttonGo = (Button) view.findViewById(R.id.taskUrlButton);
        buttonEdit = (Button) view.findViewById(R.id.taskEditButton);

        // Modify the views with the data at the specified position
        titleTask.setText(listTask.get(position).getTitle());
        durationTask.setText(listTask.get(position).getDurationInDays() + " days");

        // Format the date as "dd/MM/yyyy"
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = dateFormat.format(listTask.get(position).getStartDate());
        startDateTask.setText(dateString);

        descriptionTask.setText(listTask.get(position).getDescription());
        contextTask.setText(listTask.get(position).getContext());
        statusTask.setText(listTask.get(position).getStatus());
        urlTask.setText(listTask.get(position).getUrl());

        // Handle the "Go" button click to launch the URL in a web view
        buttonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UrlWebView.class);
                intent.putExtra("url", getUrlTask());
                context.startActivity(intent);
            }
        });

        // Handle the "Update" button click to launch the task update activity
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code à exécuter lorsque le bouton est cliqué
                Intent intent = new Intent(context, UpdateTask.class);
                intent.putExtra("task", listTask.get(position));
                context.startActivity(intent);
            }
        });

        // Return the created view
        return view;
    }

    /**
     * Returns the URL associated with the current task item
     * @return the URL associated with the current task item
     */
    String getUrlTask (){
        return this.urlTask.getText().toString();
    }

}