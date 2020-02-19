package com.example.taskand;

import com.example.taskand.FatherTask;
import com.example.taskand.Task;
import com.example.taskand.TaskDescriptor;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Make10RandomTask {

    public static List<FatherTask> make10RandomTask(){
        List<FatherTask> tasks = new ArrayList<FatherTask>();

        for(int i=0;i<10;i++){
            FatherTask father = new FatherTask();
            makeTask(father, i);
            tasks.add(father);
        }
        return tasks;
    }

    private static void makeTask(TaskDescriptor current, int i){
        writeTask(current);
        Random random = new Random(i);
        int nChild = random.nextInt(2)+1;

        for(int j=0;j<nChild;j++)
            current.addChild(new Task());

        boolean canAddChild = (random.nextInt(100)>80)? true : false;

        if(canAddChild)
            current.getChilds().forEach(x->makeTask(x,i+1));
        else
            current.getChilds().forEach(x->writeTask(x));
    }

    private static void writeTask(TaskDescriptor current){
        Faker faker = new Faker();
        current.name(faker.buffy().episodes()).description(faker.book().title()).complete(false);
    }

}