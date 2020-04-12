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
                person.put("TaskDate", day+"-" + month + "-"+year);

            }catch(Exception exception){}


            //mCreateAndSaveFile("TasksFile.json",person.toString());
            ArrayList<String> list = readFromJsonFile("TasksFile.json");
            /*Toast toast = Toast.makeText(getApplicationContext(), "number of : " + list.size() + "", Toast.LENGTH_SHORT);
            toast.show();*/
            //mReadJsonData("TasksFile.json");

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


    public  ArrayList<String> readFromJsonFile(String fileName){
        ArrayList<String> result = new ArrayList<String>();

        try{
            Toast toast1 = Toast.makeText(getApplicationContext(), "Test", Toast.LENGTH_SHORT);
            toast1.show();
            Thread.sleep(3000);
            File f = new File("/data/data/" + getPackageName() + "/" + fileName);
            FileInputStream is = new FileInputStream(f);
            int size = is.available();
            String tmp = "No Data";
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String mResponse = new String(buffer);
            JSONObject obj = new JSONObject(mResponse);
            JSONArray test = new JSONArray(mResponse);
            JSONObject t = test.getJSONObject(1);

            JSONArray arr = obj.getJSONArray("Tasks");
            Toast toast = Toast.makeText(getApplicationContext(), "number of : " + t.getString("TaskName"), Toast.LENGTH_SHORT);
            toast.show();
            Thread.sleep(5000);
            for(int i = 0; i < arr.length(); i++){
                String name = arr.getJSONObject(i).getString("TaskName");
                /*µshort salary = Short.parseShort(arr.getJSONObject(i).getString("salary"));
                String position = arr.getJSONObject(i).getString("position");
                byte years_in_company = Byte.parseByte(arr.getJSONObject(i).getString("years_in_company"));
                if (position.compareToIgnoreCase("manager") == 0){
                    result.add(new Manager(name, salary, position, years_in_company));
                }
                else{
                    result.add(new OrdinaryEmployee(name, salary, position, years_in_company));
                }*/
                result.add(name);

            }
            /*Toast toast = Toast.makeText(getApplicationContext(), "number of : " + result.size(), Toast.LENGTH_SHORT);
            toast.show();*/
        }
        catch(Exception ex){
            System.out.println(ex.toString());
        }
        return result;
    }


}
