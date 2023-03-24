package com.example.asif;

import android.widget.Toast;

import com.example.asif.utils.UUIDUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class Task {
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

    public Task(String title, String description, Date startDate, Date endDate, String context,String status, String url){
        //VERIF DATES ORDER
        if(isStartDateBeforeEndDate(startDate, endDate)){
            //VERIF CONTEXT
            if(isInAllContext(context)){
                //VERIF STATUS
                if(isInAllStatus(status)){
                    this.context = context;
                    this.status = status;
                    this.url = url;
                    this.title = title;
                    this.description = description;
                    this.startDate = startDate;
                    this.endDate = endDate;
                    System.out.println("start date striiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiing" + this.startDate);
                    System.out.println("end date striiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiing" + this.endDate);
                    this.duration = computeDuration(startDate, endDate);
                    //assigner l'id
                    this.id = UUIDUtils.getNewId().toString();

                    System.out.println("duraaaaaaaaaaaation striiiiiiiing" + getDurationInDays());
                }
                else System.err.println("Task : constructor : param status is not in AllStatus list");
            }
            else System.err.println("Task : constructor : param context is not in AllContext list");
        }
        else System.err.println("Task : constructor : param endDate is before startDate");
    }

    private Duration computeDuration(Date startDate, Date endDate){
        Instant instStart = startDate.toInstant();
        Instant instEnd = endDate.toInstant();

        return Duration.between(instStart, instEnd);
    }

    public static boolean isInAllStatus(String status) {
        for (String s : AllStatus) {
            if (s.equals(status)) {
                System.out.println(s);
                return true;
            }
        }
        return false;
    }


    public static boolean isStartDateBeforeEndDate(Date startDate, Date endDate) {
        if (startDate.before(endDate)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isInAllContext(String context) {
        for (String c : AllContext) {
            if (c.equals(context)) {
                return true;
            }
        }
        return false;
    }

    public static String[] getAllStatus() {
        return AllStatus;
    }

    public static String[] getAllContext() {
        return AllContext;
    }

    public String getId(){ return this.id; }

    public String getTitle(){
        return this.title;
    }

    public String getDescription(){
        return this.description;
    }

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

    public String getStatus(){
        switch(this.status){
            case "TODO":
                return "A faire";
            case "DOING":
                return "En cours";
            case "DONE":
                return "Terminé";
            default:
                throw new IllegalArgumentException("Unknown activity type: " + this);
        }
    }

    public Duration getDuration(){
        return this.duration;
    }

    public Date getStartDate(){
        return this.startDate;
    }

    public String getContext() {
        switch(this.context){
            case "HOUSEHOLD":
                return "Ménage";
            case "WORK":
                return "Travail";
            case "SCHOOL":
                return "École";
            case "SPARETIME":
                return "Temps libre";
            case "FAMILY":
                return "Famille";
            case "FRIENDS":
                return "Amis";
            case "OTHER":
                return "Autre";
            default:
                throw new IllegalArgumentException("Unknown activity type: " + this);
        }
    }

    public String getUrl() {
        return url;
    }



    public void setTitle(String newTitle){
        if(newTitle != null) this.title = newTitle;
        else System.err.println("Task : setTitle : newTitle is null");
    }

    public void setDescription(String newDescription){
        if(newDescription != null) this.description = newDescription;
        else System.err.println("Task : setDescription : newDescription is null");
    }

    public void setDuration(Duration newDuration){
        if(newDuration != null) this.duration = newDuration;
        else System.err.println("Task : setDuration : newDuration is null");
    }

    public void setStartDate(Date newStartDate){
        if(newStartDate != null) this.startDate = newStartDate;
        else System.err.println("Task : setStartDate : newStartDate is null");
    }

    public void setContext(String newContext){
        if(newContext != null && isInAllContext(newContext)) {
            this.context = newContext;
        } else System.err.println("Task : setContext : newContext is null or not in the list");
    }

    public void setStatus(String newStatus){
        if(newStatus != null && isInAllStatus(newStatus)) {
            this.status = newStatus;
        } else System.err.println("Task : setStatus : newStatus is null  or not in the list");
    }

    public void setUrl(String newUrl){
        if(newUrl != null) this.url = newUrl;
        else System.err.println("Task : setUrl : newUrl is null");
    }

}
