package com.example.taskand;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

public class TaskListActivity extends AppCompatActivity {

    ListView listview;
    List<Task> Tasks = new ArrayList<>();
    String[] subjects = new String[]{
            "Android",
            "PHP",
            "Blogger",
            "WordPress",
            "SEO"
    };
    List<String> subject_list = new ArrayList<String>();
    ArrayAdapter<String> arrayadapter;
    JSONArray arr = null;
    JSONObject jsonObject = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MakeTaskActivity.class));

            }
        });

        try{
            jsonObject = readFromJsonFile("TasksFile.json");
            arr = jsonObject.getJSONArray("tasks");
        for (int i=0; i < arr.length(); i++ ){
            Task task = new Task();
            subject_list.add(arr.getJSONObject(i).get("TaskName").toString());
            task.setName(arr.getJSONObject(i).get("TaskName").toString());
            task.setDescription(arr.getJSONObject(i).get("TaskDesc").toString());
            task.setStartingDate(arr.getJSONObject(i).get("TaskDate").toString());
            Tasks.add(task);
        }

        }catch (Exception e){}
        listview = (ListView) findViewById(R.id.listView1);

        //subject_list = new ArrayList<String>(Arrays.asList(subjects));

        arrayadapter = new ArrayAdapter<String>(TaskListActivity.this, android.R.layout.simple_list_item_1, subject_list);

        listview.setAdapter(arrayadapter);

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                // TODO Auto-generated method stub

                AlertDialog diaBox = AskOption();
                diaBox.show();
                subject_list.remove(position);
                supprimer(position);
                arrayadapter.notifyDataSetChanged();
                Toast.makeText(TaskListActivity.this, "Item Deleted", Toast.LENGTH_LONG).show();
                return true;
            }

        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(TaskListActivity.this,TaskDescriptorActivity.class);
                i.putExtra("nom",Tasks.get(position).getName());
                i.putExtra("description",Tasks.get(position).getDescription());
                i.putExtra( "date",Tasks.get(position).getStartingDate() );
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

    private void confirmDialogDemo() {
        boolean delete = false;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm dialog demo !");
        builder.setMessage("You are about to delete all records of database. Do you really want to proceed ?");
        builder.setCancelable(false);

        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Done !", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "You've changed your mind to delete all records", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }


    private AlertDialog AskOption()
    {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to Delete")
                .setIcon(R.drawable.ic_launcher_foreground)

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
                        dialog.dismiss();
                    }

                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();

        return myQuittingDialogBox;
    }

    //Pour faire la recherche dans la liste
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        MenuItem searchItem= menu.findItem(R.id.search_item);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList <String> TaskName = new ArrayList<>(0);
                for(String name : subject_list)
                { if (name.toLowerCase().contains(newText.toLowerCase())){

                            TaskName.add(name);
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(TaskListActivity.this,
                        android.R.layout.simple_list_item_1,TaskName);
                listview.setAdapter(adapter);
                return true;

            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}

