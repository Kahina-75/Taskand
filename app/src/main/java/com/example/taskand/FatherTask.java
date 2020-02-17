package com.example.taskand;

import java.util.List;

public class FatherTask extends Task implements TaskDescriptor {

    int completationPercent;

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