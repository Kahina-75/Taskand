package com.example.taskand;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Task implements TaskDescriptor{

    private final List<Task> childs = new ArrayList<>();
    private String name, description;
    private Date startingDate, finalDate;
    private int priority;
    private boolean isCompleted;
    private Task father;
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public Date getFinalDate() {
        return this.finalDate;
    }

    @Override
    public Date getStartingDate() {
        return this.startingDate;
    }

    @Override
    public int getPriority() {
        return this.priority;
    }

    @Override
    public List<Task> getChilds() {
        return this.childs;
    }

    @Override
    public boolean isCompleted() {
        return this.isCompleted;
    }

    @Override
    public void addChild(Task task) {
        childs.add(task);
        task.father = this;// pr recuperer l'adresse de la tache mêre a partir de la sous tache
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
    }

    public void setPriority(int priority) {
        this.priority = priority;
        this.father.UpdateChildsOrder();
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public Task(String name, String description, Date startingDate, Date finalDate, int priority, boolean isCompleted) {
        this.name = name;
        this.description = description;
        this.startingDate = startingDate;
        this.finalDate = finalDate;
        this.priority = priority;
        this.isCompleted = isCompleted;
    }

    public Task(String name) {
        this.name = name;
    }

    public Task() {
        this.name = "unkown";
    }


    public void  UpdateChildsOrder() {

        int taille = childs.size();
        Task tmp = new Task("Default");
        for (int i = 0; i < taille; i++) {
            for (int j = 1; j < (taille - i); j++) {
                if (childs.get(j - 1).getPriority() > childs.get(j).getPriority()) {
                    //echanges des elements
                    tmp = childs.get(j - 1);
                    childs.add(j-1,childs.get(j));
                    childs.add(j,tmp);
                }

            }
        }
    }


            @Override
    public boolean hasChilds() {
        return childs.size() > 0;
    }

    @Override
    public TaskDescriptor name(String name) {
        this.name = name;
        return this;
    }

    @Override
    public TaskDescriptor description(String description) {
        this.description = description;
        return this;
    }

    @Override
    public TaskDescriptor finalDate(Date finalDate) {
        this.finalDate = finalDate;
        return this;
    }

    @Override
    public TaskDescriptor startingDate() {
        startingDate = new Date();
        return this;
    }

    @Override
    public TaskDescriptor priority(int priority) {
        this.priority = priority;
        return this;
    }

    @Override
    public TaskDescriptor complete(boolean complete) {
        this.isCompleted = complete;
        return this;
    }
}
