package com.example.taskand;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public final static String TAG = "FATHERS";
    public TextView view;
    public ListView listOfTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<FatherTask> tasks = Make10RandomTask.make10RandomTask();
        List<String> labels =  new ArrayList<>();
        tasks.forEach(x -> labels.add(x.getName()));
        view = findViewById(R.id.textViewsMainActivity);
        listOfTask = findViewById(R.id.listFatherTask);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,labels);
        listOfTask.setAdapter(adapter);
        Log.d(TAG,"on creat executing");
        Log.d(TAG,tasks.toString());

        //debug purpose
        for (FatherTask task : tasks) {
            task.getTotalNumberOfChild();
            Log.d("TREES","------------------------");
        }

    }



}
