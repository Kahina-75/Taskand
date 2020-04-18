package com.example.taskand;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ModiFieldActivity extends AppCompatActivity {
    Button addSon;
    String nomTask = "";
    String descripTask = "";
    EditText name,descrip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modi_field);
        Intent intent = getIntent();
        name = findViewById(R.id.nameEdit);
        descrip=findViewById(R.id.descripEdit);
        if (intent.hasExtra("description")){
            descripTask = intent.getStringExtra("description");}
        if (intent.hasExtra("nom")){
            nomTask = intent.getStringExtra("nom");}
        name.setText(nomTask);
        descrip.setText(descripTask);
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
    }
}
