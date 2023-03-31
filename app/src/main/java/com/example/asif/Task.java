package com.example.asif;

import android.widget.Toast;

import com.example.asif.utils.UUIDUtils;

import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

/**
 * The Task class represents a task with various properties such as title, description,
 * start and end dates, duration, status, context, and URL. Tasks can be created using the
 * constructor that accepts these parameters. Additionally, the class provides methods for
 * validating properties, calculating duration, and getting and setting properties.
 */
public class Task implements Serializable {
    private String id;
    private String title;
    private String description;
    private static String[] AllStatus = {"TODO", "DOING", "DONE"};
    private String status;
    private Date startDate;
    private Date endDate;
    private Duration duration;
    private static String[] AllContext = {"HOUSEHOLD", "WORK", "SCHOOL", "SPARETIME", "FAMILY", "FRIENDS", "OTHER"};
    private String context;
    private String url;

    /**
     * Constructs a new Task object with the given parameters.
     *
     * @param title The title of the task
     * @param description The description of the task
     * @param startDate The start date of the task
     * @param endDate The end date of the task
     * @param context The context of the task
     * @param status The status of the task
     * @param url The URL associated with the task
     */
    public Task(String title, String description, Date startDate, Date endDate, String context,String status, String url){
        if(isStartDateBeforeEndDate(startDate, endDate)){
            if(isInAllContext(context)){
                if(isInAllStatus(status)){
                    this.context = context;
                    this.status = status;
                    this.url = url;
                    this.title = title;
                    this.description = description;
                    this.startDate = startDate;
                    this.endDate = endDate;
                    this.duration = computeDuration(startDate, endDate);
                    //Give an unique id
                    this.id = UUIDUtils.getNewId().toString();
                }
                else System.err.println("Task : constructor : param status is not in AllStatus list");
            }
            else System.err.println("Task : constructor : param context is not in AllContext list");
        }
        else System.err.println("Task : constructor : param endDate is before startDate");
    }

    /**
     * Computes the duration of the task in days.
     *
     * @param startDate The start date of the task
     * @param endDate The end date of the task
     * @return The duration of the task in days
     */
    private Duration computeDuration(Date startDate, Date endDate){
        Instant instStart = startDate.toInstant();
        Instant instEnd = endDate.toInstant();

        return Duration.between(instStart, instEnd);
    }

    /**
     * Determines whether the given status is in the list of valid statuses.
     *
     * @param status The status to validate
     * @return true if the status is in the list of valid statuses, false otherwise
     */
    public static boolean isInAllStatus(String status) {
        for (String s : AllStatus) {
            if (s.equals(status)) {
                System.out.println(s);
                return true;
            }
        }
        return false;
    }


    /**
     * Checks if the start date is before the end date
     * @param startDate the start date of a task
     * @param endDate the end date of a task
     * @return true if the start date is before the end date, false otherwise
     */
    public static boolean isStartDateBeforeEndDate(Date startDate, Date endDate) {
        if (startDate.before(endDate)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if a given context is present in the list of all contexts
     * @param context the context to be checked
     * @return true if the context is present in the list of all contexts, false otherwise
     */
    public static boolean isInAllContext(String context) {
        for (String c : AllContext) {
            if (c.equals(context)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns an array of all possible task statuses
     * @return an array of all possible task statuses
     */
    public static String[] getAllStatus() {
        return AllStatus;
    }

    /**
     * Returns an array of all possible task contexts
     * @return an array of all possible task contexts
     */
    public static String[] getAllContext() {
        return AllContext;
    }

    /**
     * Returns the ID of a task
     * @return the ID of a task
     */
    public String getId(){ return this.id; }

    /**
     * Returns the title of a task
     * @return the title of a task
     */
    public String getTitle(){
        return this.title;
    }

    /**
     * Returns the description of a task
     * @return the description of a task
     */
    public String getDescription(){
        return this.description;
    }

    /**
     * Calculates and returns the duration of a task in days
     * @return the duration of a task in days
     */
    public String getDurationInDays() {
        if(this.duration == null){
            System.out.println("duration null");
            return null;
        }
        else{
            duration = computeDuration(this.startDate, this.endDate);
            long hours = duration.toHours(); // conversion en heures
            Long days = hours / 24; // conversion en jours
            return days.toString();
        }
    }

    /**
     * Returns the status of a task in human-readable form
     * @return the status of a task in human-readable form
     */
    public String getStatus(){
        switch(this.status){
            case "TODO":
                return "To do";
            case "DOING":
                return "Doing";
            case "DONE":
                return "Done";
            default:
                throw new IllegalArgumentException("Unknown activity type: " + this);
        }
    }

    /**
     * Returns the index of the status of a task
     * @return the index of the status of a task
     */
    public int getStatusIndex(){
        switch(this.status){
            case "TODO":
                return 0;
            case "DOING":
                return 1;
            case "DONE":
                return 2;
            default:
                throw new IllegalArgumentException("Unknown activity type: " + this);
        }
    }

    /**
     * Returns the start date of a task
     * @return the start date of a task
     */
    public Date getStartDate(){
        return this.startDate;
    }

    /**
     * Returns the end date of a task
     * @return the end date of a task
     */
    public Date getEndDate(){
        return this.endDate;
    }

    /**
     * Returns a string representation of the context of this task.
     * @return A string representation of the context of this task.
     * @throws IllegalArgumentException if the context of the task is not one of the pre-defined options.
     */
    public String getContext() {
        switch(this.context){
            case "HOUSEHOLD":
                return "Household";
            case "WORK":
                return "Work";
            case "SCHOOL":
                return "School";
            case "SPARETIME":
                return "Spare time";
            case "FAMILY":
                return "Family";
            case "FRIENDS":
                return "Friends";
            case "OTHER":
                return "Other";
            default:
                throw new IllegalArgumentException("Unknown activity type: " + this);
        }
    }

    /**
     * Returns the index of the context of this task.
     * @return The index of the context of this task.
     * @throws IllegalArgumentException if the context of the task is not one of the pre-defined options.
     */
    public int getContextIndex() {
        switch(this.context){
            case "HOUSEHOLD":
                return 0;
            case "WORK":
                return 1;
            case "SCHOOL":
                return 2;
            case "SPARETIME":
                return 3;
            case "FAMILY":
                return 4;
            case "FRIENDS":
                return 5;
            case "OTHER":
                return 6;
            default:
                throw new IllegalArgumentException("Unknown activity type: " + this);
        }
    }

    /**
     * Returns the URL associated with this task.
     * @return The URL associated with this task.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the title of this task.
     * @param newTitle The new title of this task.
     */
    public void setTitle(String newTitle){
        if(newTitle != null) this.title = newTitle;
        else System.err.println("Task : setTitle : newTitle is null");
    }

    /**
     * Sets the description of this task.
     * @param newDescription The new description of this task.
     */
    public void setDescription(String newDescription){
        if(newDescription != null) this.description = newDescription;
        else System.err.println("Task : setDescription : newDescription is null");
    }

    /**
     * Sets the duration of this task based on its start and end dates.
     * @param startDate The start date of this task.
     * @param endDate The end date of this task.
     */
    public void setDuration(Date startDate, Date endDate){
        if(startDate != null && endDate != null){
            Duration newDuration = computeDuration(startDate, endDate);
            this.duration = newDuration;
        }
        else System.err.println("Task : setDuration : startDate or endDate is null");
    }

    /**
     * Sets the start date of this task.
     * @param newStartDate The new start date of this task.
     */
    public void setStartDate(Date newStartDate){
        if(newStartDate != null) this.startDate = newStartDate;
        else System.err.println("Task : setStartDate : newStartDate is null");
    }

    /**
     * Sets the end date of this task.
     * @param newEndDate The new end date of this task.
     */
    public void setEndDate(Date newEndDate){
        if(newEndDate != null) this.endDate = newEndDate;
        else System.err.println("Task : setEndDate : newEndDate is null");
    }

    /**
     * Sets the context of this task.
     * @param newContext The new context of this task.
     */
    public void setContext(String newContext){
        if(newContext != null && isInAllContext(newContext)) {
            this.context = newContext;
        } else System.err.println("Task : setContext : newContext is null or not in the list");
    }

    /**
     * Sets the status of this task.
     * @param newStatus The new status of this task.
     */
    public void setStatus(String newStatus){
        if(newStatus != null && isInAllStatus(newStatus)) {
            this.status = newStatus;
        } else System.err.println("Task : setStatus : newStatus is null  or not in the list");
    }

    /**
     * Sets the URL associated with this task.
     * @param newUrl The new URL associated with this task.
     */
    public void setUrl(String newUrl){
        if(newUrl != null) this.url = newUrl;
        else System.err.println("Task : setUrl : newUrl is null");
    }

}
