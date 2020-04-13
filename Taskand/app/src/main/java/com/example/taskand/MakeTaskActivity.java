package com.example.taskand;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

public class MakeTaskActivity extends AppCompatActivity implements View.OnClickListener {

    private String curDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_task);
        Button bt1 = findViewById(R.id.bt1);
        bt1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt1){
            JSONObject obj = new JSONObject();
            JSONObject person = new JSONObject();
            try{
                EditText name = (EditText)findViewById(R.id.edtName);
                EditText desc = (EditText)findViewById(R.id.edtDescription);
                EditText edtPrio = (EditText)findViewById(R.id.edtPriority);
                DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker1);
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1;
                int year = datePicker.getYear();

                person.put("TaskName", name.getText());
                person.put("TaskDescritpion", desc.getText());
                person.put("TaskPriority", edtPrio.getText());
                person.put("TaskDate", day+"-"+month+"-"+year);


            }catch(Exception exception){}


            mCreateAndSaveFile("TasksFile.json",person.toString());
            mReadJsonData("TasksFile.json");
            objectToJson();
        }
    }

    public void mCreateAndSaveFile(String params, String mJsonResponse) {
        try {
            FileWriter file = new FileWriter("/data/data/" + getApplicationContext().getPackageName() + "/" + params);
            file.write(mJsonResponse);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mReadJsonData(String params) {
        try {
            File f = new File("/data/data/" + getPackageName() + "/" + params);
            FileInputStream is = new FileInputStream(f);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String mResponse = new String(buffer);
            Toast toast = Toast.makeText(getApplicationContext(), mResponse, Toast.LENGTH_SHORT);
            toast.show();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void objectToJson() {
        try{
            JSONObject jo = new JSONObject();
            jo.put("firstName", "John");
            jo.put("lastName", "Smith");
            jo.put("age", 25);

            PrintWriter pw = new PrintWriter("/data/data/" + getApplicationContext().getPackageName() + "/fileJ.json");
            pw.write(jo.toString());
        }catch (Exception e){}

    }


}