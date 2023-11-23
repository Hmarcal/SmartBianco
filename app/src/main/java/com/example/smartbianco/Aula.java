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

    //Variáveis de elementos gráficos para interação
    private EditText TxtalunoCadastro;
    private EditText TxtalunoApagar;

    private Button BtcCadastrar;
    private Button BtcApagar;
    private Button BtcExcluir;

    private ListView listaAlunos; //listview para exibir a lista de nomes

    private ArrayAdapter adapter; //adapter da listView
    private static ArrayList<Alunos> exibeLista; //array para a lista de contatos

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


        BtcCadastrar.setOnClickListener(new View.OnClickListener() {  //salva o nome digitado
            @Override
            public void onClick(View view) {
                if (TxtalunoCadastro.getText().length() == 0){ //verifica se há texto digitado
                    Toast.makeText(Aula.this,"Por favor insira um Nome!", Toast.LENGTH_SHORT).show();
                }else {
                    String name = TxtalunoCadastro.getText().toString();

                    Alunos cadastro = new Alunos(0, name); //insere o nome do aluno no banco de dados
                    long id = db.salvaAlunos(cadastro);
                    if (id != -1){
                        Toast.makeText(Aula.this, "Nome Inserido!", Toast.LENGTH_SHORT).show();

                        //atualiza a lista para visualização sempre que um nome for posto
                        exibeLista = db.findAll(); //chama o método do DB
                        adapter.clear(); //limpa o adpter que é um ArrayAdpter
                        adapter.addAll(exibeLista); //adiciona os elementos da lista no adpter
                        adapter.notifyDataSetChanged(); //faz a atualização da lista com novos dados

                    }

                    else
                        Toast.makeText(Aula.this, "Não foi possível inserir o nome", Toast.LENGTH_SHORT).show();

                    TxtalunoCadastro.setText("");
                }

                closeKeyboard(); // aciona o método para fechar teclado assim que clicar no botão
            }
        });


        BtcApagar.setOnClickListener(new View.OnClickListener() { //apaga um nome no banco de  dados
            @Override
            public void onClick(View view) {
                int count = db.apagaAlunos(TxtalunoApagar.getText().toString());
                if (count == 0 ){ //verifica se há nome escrito e/ou se corresponde com nome contido no banco de dados
                    Toast.makeText(Aula.this, "Aluno Inexistente", Toast.LENGTH_SHORT).show();
                    TxtalunoApagar.setText("");
                }else{
                    Toast.makeText(Aula.this, "Nome Excluído", Toast.LENGTH_SHORT).show();
                    TxtalunoApagar.setText("");

                    //atualiza a lista para visualização sempre que um nome for retirado
                    exibeLista = db.findAll(); //chama o método do DB
                    adapter.clear(); //limpa o adpter que é um ArrayAdpter
                    adapter.addAll(exibeLista); //adiciona os elementos da lista no adpter
                    adapter.notifyDataSetChanged(); //faz a atualização da lista com novos dados
                }
                closeKeyboard(); // aciona o método para fechar teclado assim que clicar no botão
            }
        });


        BtcExcluir.setOnClickListener(new View.OnClickListener() { //faz a exclusão da lista de nomes (registros/linha) no banco de dados
            @Override
            public void onClick(View view) {
                if (exibeLista.isEmpty()){ // verifica se a lista está vazia
                    Toast.makeText(Aula.this, "Não existem Alunos cadastrados", Toast.LENGTH_SHORT).show();
                }else {
                    db.excluirAlunos();

                    //atualiza a lista para visualização sempre que a lista for excluida
                    exibeLista = db.findAll(); //chama o método do DB
                    adapter.clear(); //limpa o adpter que é um ArrayAdpter
                    adapter.addAll(exibeLista); //adiciona os elementos da lista no adpter
                    adapter.notifyDataSetChanged(); //faz a atualização da lista com novos dados

                    Toast.makeText(Aula.this, "Tabela de alunos Excluída", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // permanece a lista visual
        exibeLista = db.findAll(); //chama o método findAll que devolve um array e guarda em exibeLista
        adapter = new ArrayAdapter<Alunos>(this, android.R.layout.simple_list_item_1,exibeLista);
        //criação de uma instância de um ListAdapter utilizando um layout nativo
        listaAlunos.setAdapter(adapter); //associação a ListView com o adapter


    }//Final onCreate

    private void closeKeyboard(){ //metodo para fechar o teclado nas finalizações de inserir nomes e apagar
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }


}//Final
