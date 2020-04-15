package com.example.taskand;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
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
import java.util.ArrayList;
import java.util.Calendar;

public class MakeTaskActivity extends AppCompatActivity implements View.OnClickListener {

    private String curDate;
    private JSONObject jsonFile;
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
            addTask();
            //supprimer("TEst");
            //startActivity(new Intent(getApplicationContext(), MainActivity.class));
            startActivity(new Intent(getApplicationContext(), TaskListActivity.class));
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

    public  JSONObject readFromJsonFile(String fileName){
        ArrayList<String> result = new ArrayList<String>();
        JSONObject obj = null;
        try{
            File f = new File("/data/data/" + getPackageName() + "/" + fileName);
            if(!f.exists()){
                mCreateAndSaveFile("TasksFile.json","{'tasks':[]}");
            }
            FileInputStream is = new FileInputStream(f);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String mResponse = new String(buffer);
            obj = new JSONObject(mResponse);
        return  obj;
        }
        catch(Exception ex){
            System.out.println(ex.toString());
            return obj;
        }

    }

    public void addTask(){
        JSONObject obj = new JSONObject();
        JSONObject person = new JSONObject();
        JSONArray jarr = null;
        JSONObject file = null;
        String tmp =null;
        try{
            file = readFromJsonFile("TasksFile.json");
            //file = new JSONObject();
            jarr = file.getJSONArray("tasks");
            JSONObject obj1 = new JSONObject();

            EditText name = (EditText)findViewById(R.id.edtName);
            tmp = name.getText().toString();
            EditText desc = (EditText)findViewById(R.id.edtDescription);
            EditText edtPrio = (EditText)findViewById(R.id.edtPriority);
            DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker1);
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth() + 1;
            int year = datePicker.getYear();
            obj1.put("TaskName",name.getText().toString());
            obj1.put("TaskDesc",desc.getText().toString());
            obj1.put("TaskDate",day+"-" + month + "-"+year);

            JSONArray subTask = new JSONArray();
            obj1.put("subTask",subTask);
            jarr.put(obj1);

            file.put("tasks",jarr);

        }catch(Exception exception){}
        Toast toast = Toast.makeText(getApplicationContext(), "Succes add !", Toast.LENGTH_SHORT);
        toast.show();
        mCreateAndSaveFile("TasksFile.json",file.toString());
    }

    public void modifier(){

    }

    public void supprimer(String taskName){
        JSONObject obj = new JSONObject();
        JSONObject person = new JSONObject();
        JSONArray jarr = null;
        JSONObject file = null;
        try{
            file = readFromJsonFile("TasksFile.json");
            //file = new JSONObject();
            jarr = file.getJSONArray("tasks");
            ArrayList tmp = new ArrayList();
            int pos = 0;
            boolean find = false;
            for (int i = 0; i < jarr.length(); i++){
                if(!taskName.equals(jarr.getJSONObject(i).getString("TaskName"))){
                    tmp.add(jarr.getJSONObject(i));
                }
            }
            if(true){
                jarr = new JSONArray(tmp);
            }
            file.put("tasks",jarr);

        }catch(Exception exception){}

        mCreateAndSaveFile("TasksFile.json",file.toString());
    }


}
