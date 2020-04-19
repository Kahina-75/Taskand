package com.example.taskand;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;


//test first commit
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
private ImageButton bt2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton bt1 = findViewById(R.id.ajouter);
        bt1.setOnClickListener(this);

        this.bt2=findViewById(R.id.liste);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent other= new Intent(getApplicationContext(),TaskListActivity.class);
                startActivity(other);

            }
        });


        ImageButton exit = findViewById(R.id.quitter);
        exit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View vexit) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ajouter){
            Toast toast = Toast.makeText(getApplicationContext(), "First button", Toast.LENGTH_SHORT);
            toast.show();
            startActivity(new Intent(getApplicationContext(), MakeTaskActivity.class));

        }

    }


        /*super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Task A = new Task("A");
        Task B = new Task("B");
        Task C = new Task("C");
        Task D = new Task("D");
        Task T = new Task("T");
        T.addChild(A);
        T.addChild(B);
        A.addChild(C);
        A.addChild(D);

        // afficher l'arbre
        // afficher T
        Boolean end = Boolean.FALSE;
        Task current = T;

        //afficher T
        // Test de l'arbre
        List<Task> soustaches_t = current.getChilds();
        int max = soustaches_t.size();
        //int i = 0;
        System.out.println("Sous taches de T");
        for (int i=0; i < max; i++){
            // afficher
            Task fils = soustaches_t.get(i);
            System.out.println(fils.getName());
        }
    */





}

