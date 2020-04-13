package com.example.taskand;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListTask extends AppCompatActivity {
    ArrayList<String> listItem;
    ArrayList<Task> listTask;
    ArrayAdapter adapter;
    SQLiteDataBase db;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_task);
        listView = findViewById(R.id.list);
        db = new SQLiteDataBase(ListTask.this);
        listItem = new ArrayList<>();
        listTask = new ArrayList<>();
        getAll();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ListTask.this,TaskDescriptorActivity.class);
                i.putExtra("nom",listTask.get(position).getName());
                i.putExtra("description",listTask.get(position).getDescription());
                i.putExtra( "date",listTask.get(position).getStartingDate() );
                startActivity(i);
            }
        });
    }

    private void getAll() {
        Cursor cursor = db.getAllTask();
        if(cursor.getCount()==0)
        {
            Toast.makeText(ListTask.this,"No data to show", Toast.LENGTH_SHORT);
        } else {
            while (cursor.moveToNext()){
                Task task = new Task();
                task.name(cursor.getString(1)); task.description(cursor.getString(2));task.startingDate(cursor.getString(3));
                listTask.add(task);
                listItem.add(cursor.getString(1)+"   "+ cursor.getString(3));
            }
            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItem);
            listView.setAdapter(adapter);
        }

    }
}
