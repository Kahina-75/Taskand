package com.example.taskand;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ModiFieldActivity extends AppCompatActivity {
    Button addSon;
    Button editButton;
    String nomTask = "";
    String descripTask = "";
    EditText name,descrip,pre;
    int position = -1;
    int preValue = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modi_field);
        Intent intent = getIntent();
        name = findViewById(R.id.nameEdit);
        descrip=findViewById(R.id.descripEdit);
        pre=findViewById(R.id.PreEdit);
        if (intent.hasExtra("description")){
            descripTask = intent.getStringExtra("description");}

        if (intent.hasExtra("nom")){
            nomTask = intent.getStringExtra("nom");}

        if (intent.hasExtra("position")){
            position = intent.getIntExtra("position",-1);}

        if (intent.hasExtra("taskPre")){
            preValue = intent.getIntExtra("taskPre",-1);}

        name.setText(nomTask);
        descrip.setText(descripTask);
        pre.setText(preValue + "");
        addSon = findViewById(R.id.SonTask);
        addSon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MakeTaskActivity.class);
                //i.putExtra("taskType","SON");
                i.putExtra("nameFather", nomTask);
                startActivity(i);

            }
        });

        editButton = findViewById(R.id.modif);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifier("TasksFile.json");
            }
        });
    }

    public void modifier(String taskName){
        JSONObject obj = new JSONObject();
        JSONObject person = new JSONObject();
        JSONArray jarr = null;
        JSONObject file = null;
        try{
            file = readFromJsonFile("TasksFile.json");
            //file = new JSONObject();
            jarr = file.getJSONArray("tasks");

            jarr.getJSONObject(position).put("TaskName",name.getText());
            jarr.getJSONObject(position).put("TaskDesc",descrip.getText());
            jarr.getJSONObject(position).put("TaskPre",pre.getText());

            file.put("tasks",jarr);
        }catch(Exception exception){}

        mCreateAndSaveFile("TasksFile.json",file.toString());
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

}
