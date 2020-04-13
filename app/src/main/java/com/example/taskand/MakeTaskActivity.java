package com.example.taskand;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class MakeTaskActivity extends AppCompatActivity {

    SQLiteDataBase db;
    Button btn;
    EditText nom;
    EditText description, priorite;
    Intent intent;
    DatePicker datePicker;
    String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_task);
        db = new SQLiteDataBase(this);
        btn = findViewById(R.id.submitMakeTask);

        nom = findViewById(R.id.edtName);
        description = findViewById(R.id.edtDescription);
        priorite = findViewById(R.id.edtPriority);


        Add();

    }
    public void Add(){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker = (DatePicker) findViewById(R.id.datePicker1);
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1;
                int year = datePicker.getYear();
                date = day+"/"+month+"/"+year;
                boolean isInserted=db.insertData(nom.getText().toString(),description.getText().toString(),date);//Integer.parseInt(priorite.getText().toString()));
                if (isInserted ==true)
                {//Toast.makeText(MakeTaskActivity.this,"Data Inserted Successfully",Toast.LENGTH_LONG).show();
                    intent = new Intent(MakeTaskActivity.this,TaskDescriptorActivity.class);
                    intent.putExtra("nom",nom.getText().toString());
                    intent.putExtra("description",description.getText().toString());
                    intent.putExtra ("date",date);
                    startActivity(intent);

                }
                else
                    Toast.makeText(MakeTaskActivity.this,"Data Not Inserted",Toast.LENGTH_LONG).show();
            }
        });
    }
}
