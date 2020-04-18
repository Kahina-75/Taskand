package com.example.taskand;

import java.util.Date;
import java.util.List;

public interface TaskDescriptor {

    String getName();
    String getDescription();
    //Date getFinalDate();
    String getStartingDate();
    int getPriority();
    boolean isCompleted();
    //boolean hasChilds();
    String getFatherName();

    TaskDescriptor name(String name);
    TaskDescriptor description(String description);
    TaskDescriptor FatherName(String fatherName);
    TaskDescriptor finalDate(Date finalDate);
    TaskDescriptor startingDate(String startingDate);
    TaskDescriptor priority(int priority);
    TaskDescriptor complete(boolean complete);
}
