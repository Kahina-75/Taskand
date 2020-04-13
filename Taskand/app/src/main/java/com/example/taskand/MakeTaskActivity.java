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
import java.util.ArrayList;
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
            JSONArray jarr = null;
            JSONObject file = null;
            String tmp =null;
            try{
                file = readFromJsonFile("TasksFile.json");
                EditText name = (EditText)findViewById(R.id.edtName);
                tmp = name.getText().toString();
                EditText desc = (EditText)findViewById(R.id.edtDescription);
                EditText edtPrio = (EditText)findViewById(R.id.edtPriority);
                DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker1);
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1;
                int year = datePicker.getYear();
                JSONObject o = new JSONObject();
                o.put(name.getText().toString(),name.getText().toString());
                jarr = file.getJSONArray("array");
                jarr.put(o);
                /*person.put("TaskName", name.getText());
                person.put("TaskDescritpion", desc.getText());
                person.put("TaskPriority", edtPrio.getText());
                person.put("TaskDate", day+"-" + month + "-"+year);
                person.put("array",jarr);*/
                file.put("TaskName" , name.getText().toString());
                file.put("array",jarr);
            }catch(Exception exception){}
            Toast toast = Toast.makeText(getApplicationContext(), tmp, Toast.LENGTH_SHORT);
            toast.show();
            mCreateAndSaveFile("TasksFile.json",file.toString());
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
            String tmp = "No Data";
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String mResponse = new String(buffer);
            try{
                JSONObject obj = new JSONObject(mResponse);
                tmp = obj.getString("TaskName");

            }catch (Exception e){}
            Toast toast = Toast.makeText(getApplicationContext(), tmp, Toast.LENGTH_SHORT);
            toast.show();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public  JSONObject readFromJsonFile(String fileName){
        ArrayList<String> result = new ArrayList<String>();
        JSONObject obj = null;
        try{
            File f = new File("/data/data/" + getPackageName() + "/" + fileName);
            FileInputStream is = new FileInputStream(f);
            int size = is.available();
            String tmp = "No Data";
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String mResponse = new String(buffer);
            obj = new JSONObject(mResponse);
            result.add(obj.getString("TaskName"));

            //EditText name = (EditText)findViewById(R.id.edtName);
            //name.setText(obj.getString("TaskName"));
            JSONArray arr = obj.getJSONArray("array");
            for(int i = 0; i < arr.length(); i++){
                result.add(arr.getJSONObject(i).getString("t1"));
            }
            //result.add(arr.getJSONObject(0).getString("t1"));
        return  obj;
        }
        catch(Exception ex){
            System.out.println(ex.toString());
            return obj;
        }

    }


}
