package com.example.smartbianco;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Aula extends AppCompatActivity {

    private EditText TxtalunoCadastro;
    private EditText TxtalunoApagar;

    private Button BtcCadastrar;
    private Button BtcApagar;
    private Button BtcExcluir;

    private ListView listaAlunos;

    private ArrayAdapter adapter;
    private static ArrayList<Alunos> exibeLista;

    private AlunosDB db;

    @SuppressLint("MissingInflatedId")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aula);

        db = new AlunosDB(this);

        TxtalunoCadastro = findViewById(R.id.TextCadastrarXML);
        TxtalunoApagar = findViewById(R.id.TextApagarXML);
        BtcCadastrar = findViewById(R.id.cadastrarXML);
        BtcApagar = findViewById(R.id.apagarXML);
        BtcExcluir = findViewById(R.id.excluirXML);
        listaAlunos = (ListView) findViewById(R.id.listViewXML);


        BtcCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TxtalunoCadastro.getText().length() == 0){
                    Toast.makeText(Aula.this,"Por favor insira um Nome!", Toast.LENGTH_SHORT).show();
                }else {
                    String name = TxtalunoCadastro.getText().toString();

                    Alunos cadastro = new Alunos(0, name);
                    long id = db.salvaAlunos(cadastro);
                    if (id != -1){
                        Toast.makeText(Aula.this, "Nome Inserido!", Toast.LENGTH_SHORT).show();

                        exibeLista = db.findAll();
                        adapter.clear();
                        adapter.addAll(exibeLista);
                        adapter.notifyDataSetChanged();

                    }

                    else
                        Toast.makeText(Aula.this, "Não foi possível inserir o nome", Toast.LENGTH_SHORT).show();

                    TxtalunoCadastro.setText("");
                }

                closeKeyboard();
            }
        });


        BtcApagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = db.apagaAlunos(TxtalunoApagar.getText().toString());
                if (count == 0 ){
                    Toast.makeText(Aula.this, "Aluno Inexistente", Toast.LENGTH_SHORT).show();
                    TxtalunoApagar.setText("");
                }else{
                    Toast.makeText(Aula.this, "Nome Excluído", Toast.LENGTH_SHORT).show();
                    TxtalunoApagar.setText("");

                    exibeLista = db.findAll();
                    adapter.clear();
                    adapter.addAll(exibeLista);
                    adapter.notifyDataSetChanged();
                }
                closeKeyboard();
            }
        });

        //inserir Btcexcluir
        BtcExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (exibeLista.isEmpty()){
                    Toast.makeText(Aula.this, "Não existem Alunos cadastrados", Toast.LENGTH_SHORT).show();
                }else {
                    db.excluirAlunos();

                    exibeLista = db.findAll();
                    adapter.clear();
                    adapter.addAll(exibeLista);
                    adapter.notifyDataSetChanged();

                    Toast.makeText(Aula.this, "Tabela de alunos Excluída", Toast.LENGTH_SHORT).show();
                }
            }
        });


        exibeLista = db.findAll();
        adapter = new ArrayAdapter<Alunos>(this, android.R.layout.simple_list_item_1,exibeLista);
        listaAlunos.setAdapter(adapter);


    }//Final onCreate

    private void closeKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }


}//Final
