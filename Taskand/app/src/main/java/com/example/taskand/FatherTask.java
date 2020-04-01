package com.example.taskand;

import java.util.Date;
import java.util.List;

public class FatherTask extends Task implements TaskDescriptor {

    int completationPercent;

    public FatherTask(String name, String description, Date startingDate, Date finalDate, int priority, boolean isCompleted, int completationPercent) {
        super(name, description, startingDate, finalDate, priority, isCompleted);
        this.completationPercent = 0;
    }

    public FatherTask() {
        // super();
        this.completationPercent = 0;
    }

    public int getNumberOfChild() {
        return getChilds().size();
    }

    public int getTotalNumberOfChild() {
        return countChilds(this);
    }

    private int countChilds(TaskDescriptor current) {
        int sum = 0;
        if (getChilds().size() > 0) {
            for (int i = 0; i < getChilds().size(); i++)
                sum += countChilds(getChilds().get(i));
        }
        return sum + getChilds().size();
    }
}