package com.example.smartbianco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    private Button aquecimentoBtc;
    private Button skillBtc;
    private Button wodBtc;
    private Button alunosBtc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aquecimentoBtc = findViewById(R.id.aquecimentoXML);
        skillBtc = findViewById(R.id.skillXML);
        wodBtc = findViewById(R.id.wodXML);
        alunosBtc = findViewById(R.id.alunosXML);

        aquecimentoBtc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent aquecimentoIntent = new Intent(MainActivity.this, Aquecimento.class);
                startActivity(aquecimentoIntent);
            }
        });

        skillBtc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  skillIntent = new Intent(MainActivity.this, Skill.class);
                startActivity(skillIntent);
            }
        });

        wodBtc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent wodIntent = new Intent(MainActivity.this, Wod.class);
                startActivity(wodIntent);
            }
        });

        alunosBtc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent alunosIntent = new Intent(MainActivity.this, Aula.class);
                startActivity(alunosIntent);
            }
        });


    }
}