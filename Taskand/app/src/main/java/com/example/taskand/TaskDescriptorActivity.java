package com.example.taskand;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TaskDescriptorActivity extends AppCompatActivity {

    TextView name,descrip,date1,preText;
    Button modif,supp;
    String nom = "";
    String Description = "";
    String Date = "";
    int taskPre = -1;
    int posTask;
    //Partie ListSons
    ListView listView;
    List<Task> listChilren = new ArrayList<>();
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
        supp = findViewById(R.id.supp);
        modif = findViewById(R.id.modif);
        name = findViewById(R.id.taskName);
        descrip = findViewById(R.id.descrip);
        date1 = findViewById(R.id.taskDate);
        preText = findViewById(R.id.priority);
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
            if (intent.hasExtra("pre")){ // vérifie qu'une valeur est associée à la clé “date”
                taskPre= intent.getIntExtra("pre",-1); // on récupère la valeur associée à la clé
                //taskPre = 100;
            }
            name.setText(nom);
            descrip.setText(Description);
            date1.setText(Date);
            preText.setText(taskPre + "");
        }
        //BoutonModif
        modif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ModiFieldActivity.class);
                i.putExtra("nom", nom);
                i.putExtra("description", Description);
                startActivity(i);
            }
        });
      /*  if (intent.hasExtra("positionTask")){ // vérifie qu'une valeur est associée à la clé “date”
            posTask= Integer.parseInt(intent.getStringExtra("positionTask")); // on récupère la valeur associée à la clé
        }
        //BoutonSupp
        supp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //AlertDialog diaBox = AskOption();
                // diaBox.show();
                subject_list.remove(posTask);
                supprimer(posTask);
                arrayadapter.notifyDataSetChanged();
                Toast.makeText(TaskDescriptorActivity.this, "Item Deleted", Toast.LENGTH_LONG).show();

            }
        });*/
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
                listChilren.add(Tasks.get(i));
            }
        }
            listView = (ListView) findViewById(R.id.listChildren);

        //subject_list = new ArrayList<String>(Arrays.asList(subjects));

        arrayadapter = new ArrayAdapter<String>(TaskDescriptorActivity.this, android.R.layout.simple_list_item_1, subject_list);

        listView.setAdapter(arrayadapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(TaskDescriptorActivity.this,TaskDescriptorActivity.class);
                i.putExtra("nom",listChilren.get(position).getName());
                i.putExtra("description",listChilren.get(position).getDescription());
                i.putExtra( "date",listChilren.get(position).getStartingDate());
                startActivity(i);
            }
        });
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
    public void supprimer(int pos){
        try{
            ArrayList tmp = new ArrayList();
            boolean find = false;
            for (int i = 0; i < arr.length(); i++){
                if(i == pos){
                    tmp.add(arr.getJSONObject(i));
                    find = true;
                    break;
                }
            }
            if(true){
                arr = new JSONArray(tmp);
            }
            jsonObject.put("tasks",arr);

        }catch(Exception exception){}

        mCreateAndSaveFile("TasksFile.json",jsonObject.toString());
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
}
