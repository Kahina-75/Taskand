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
