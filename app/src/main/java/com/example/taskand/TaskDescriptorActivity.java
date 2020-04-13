package com.example.taskand;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class TaskDescriptorActivity extends AppCompatActivity {

    TextView name,descrip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_descriptor);
        Intent intent = getIntent();
        name = findViewById(R.id.taskName);
        descrip = findViewById(R.id.descrip);
        if (intent!=null){
            Toast.makeText(TaskDescriptorActivity.this,"Data Inserted Successfully"+intent.getStringExtra("nom"),Toast.LENGTH_LONG).show();
            String nom = "";
            if (intent.hasExtra("nom")){ // vérifie qu'une valeur est associée à la clé “nom”
                nom = intent.getStringExtra("nom"); // on récupère la valeur associée à la clé
            }
            String Description = "";
            if (intent.hasExtra("description")){ // vérifie qu'une valeur est associée à la clé “nom”
                Description = intent.getStringExtra("description"); // on récupère la valeur associée à la clé
            }
            name.setText(nom);
            descrip.setText(Description);
        }
    }
}