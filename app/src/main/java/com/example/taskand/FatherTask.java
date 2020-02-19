package com.example.taskand;

import android.util.Log;

import java.util.List;

public class FatherTask extends Task implements TaskDescriptor {

    int completationPercent;
    private final static String TAG = "TREES";

    public int getNumberOfChild() {
        return getChilds().size();
    }

    public int getTotalNumberOfChild() {
        return countChilds(this);
    }

    private int countChilds(TaskDescriptor current) {
        Log.d(TAG,current.getName());
        int sum = 0;
        if (current.getChilds().size() > 0) {
            for (int i = 0; i < current.getChilds().size(); i++)
                sum += countChilds(current.getChilds().get(i));
        }
        return sum + getChilds().size();
    }
}