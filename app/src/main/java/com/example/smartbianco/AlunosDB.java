package com.example.smartbianco;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AlunosDB extends SQLiteOpenHelper {

    public static final String TAG  =   "sql";
    public static final String NOME_BANCO   =   "MeuBancodeDados.db";
    public static final int VERSAO_BANCO    =   1;
    public static final String TABLE_NAME   =   "alunos";
    public static final String COLUNA0  =   "_id";
    public static final String COLUNA1  =   "nome";

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " +TABLE_NAME + " ("
                    + COLUNA0 +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUNA1 +" TEXT )";

    public AlunosDB(@Nullable Context context ) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_TABLE);

        Log.d(TAG, "Tabela"+TABLE_NAME+" criada com sucesso");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public long salvaAlunos(Alunos alunos){ //cria conexão com o DB e insere o nome digitado na classe aula
        long id = alunos.get_id();

        SQLiteDatabase db = getWritableDatabase();
        try{

            ContentValues valores = new ContentValues();

            valores.put(COLUNA1,alunos.getNome());
            if (id !=0){
                int count = db.update(TABLE_NAME, valores, "_id =?", new String[]{String.valueOf(id)});
                return count;
            }
            else {
                id = db.insert(TABLE_NAME, null, valores);
                return id;
            }

        }finally {
            db.close();
        }
    }


    public int apagaAlunos(String nomeAlunos) { //cria conexão com o DB e apaga o nome digitado na classe aula
        SQLiteDatabase db = getWritableDatabase();
        try {
            int count = db.delete(TABLE_NAME,"nome=?", new String[]{nomeAlunos});
            Log.i(TAG,"deletou registro =>" + count);
            return count;
        }finally {
            db.close();
        }
    }


    public void excluirAlunos(){ //cria conexão com o DB e apaga toda a linha "alunos" na classe aula

        SQLiteDatabase db = getWritableDatabase();
        try{
            db.delete(TABLE_NAME,null,null);

        }finally {
            db.close();
        }
    }

    public ArrayList<Alunos> findAll(){

        SQLiteDatabase db = getWritableDatabase();

        ArrayList<Alunos> lista = new ArrayList<>();
        try {
            Cursor c =db.query(TABLE_NAME, null,null, null,null,null,null);
            if (c.moveToFirst()){
                do{
                    @SuppressLint("Range") long id = c.getLong(c.getColumnIndex("_id"));
                    @SuppressLint("Range") String nome = c.getString(c.getColumnIndex("nome"));

                    Alunos currentContact = new Alunos(id, nome);
                    lista.add(currentContact);
                }while (c.moveToNext());
            }
            return lista;
        }finally {
            db.close();
        }
    }


}//Final
