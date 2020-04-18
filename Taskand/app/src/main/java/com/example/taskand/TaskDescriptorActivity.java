package com.example.taskand;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class TaskDescriptorActivity extends AppCompatActivity {

    TextView name,descrip,date1;
    Button modif;
    String nom = "";
    String Description = "";
    String Date = "";
    //Partie ListSons
    ListView listView;
    List<Task> Tasks = new ArrayList<>();
    List<String> subject_list = new ArrayList<String>();
    ArrayAdapter<String> arrayadapter;
    JSONArray arr = null;
    JSONObject jsonObject = null;
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_descriptor);
        Intent intent = getIntent();
        modif = findViewById(R.id.modif);
        name = findViewById(R.id.taskName);
        descrip = findViewById(R.id.descrip);
        date1 = findViewById(R.id.taskDate);
        if (intent!=null){
            //Toast.makeText(TaskDescriptorActivity.this,"Data Inserted Successfully "+intent.getStringExtra("nom"),Toast.LENGTH_LONG).show();

            if (intent.hasExtra("nom")){ // vérifie qu'une valeur est associée à la clé “nom”
                nom = intent.getStringExtra("nom"); // on récupère la valeur associée à la clé
            }

            if (intent.hasExtra("description")){ // vérifie qu'une valeur est associée à la clé “Description”
                Description = intent.getStringExtra("description"); // on récupère la valeur associée à la clé
            }

            if (intent.hasExtra("date")){ // vérifie qu'une valeur est associée à la clé “date”
                Date= intent.getStringExtra("date"); // on récupère la valeur associée à la clé
            }
            name.setText(nom);
            descrip.setText(Description);
            date1.setText(Date);
        }
        modif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ModiFieldActivity.class);
                i.putExtra("nom", nom);
                i.putExtra("description", Description);
                startActivity(i);
            }
        });
        //Partie ListView

        try{
            jsonObject = readFromJsonFile("TasksFile.json");
            arr = jsonObject.getJSONArray("tasks");
            for (int i=0; i < arr.length(); i++ ){

                Task task = new Task();
                //subject_list.add(arr.getJSONObject(i).get("TaskName").toString());
                task.setName(arr.getJSONObject(i).get("TaskName").toString());
                task.setDescription(arr.getJSONObject(i).get("TaskDesc").toString());
                task.setStartingDate(arr.getJSONObject(i).get("TaskDate").toString());
                task.FatherName(arr.getJSONObject(i).get("TaskFatherName").toString());
                Tasks.add(task);
            }

        }catch (Exception e){}
        for(int i=0; i<Tasks.size();i++){
            if (Tasks.get(i).getFatherName().equals(nom)){
                subject_list.add(Tasks.get(i).getName());
            }
        }
            listView = (ListView) findViewById(R.id.listChildren);

        //subject_list = new ArrayList<String>(Arrays.asList(subjects));

        arrayadapter = new ArrayAdapter<String>(TaskDescriptorActivity.this, android.R.layout.simple_list_item_1, subject_list);

        listView.setAdapter(arrayadapter);
    }
    public JSONObject readFromJsonFile(String fileName){
        ArrayList<String> result = new ArrayList<String>();
        JSONObject obj = null;
        try{
            File f = new File("/data/data/" + getPackageName() + "/" + fileName);
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
