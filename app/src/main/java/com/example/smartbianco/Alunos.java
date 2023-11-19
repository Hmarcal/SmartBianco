package com.example.smartbianco;

public class Alunos {

    private long _id;
    private String nome;


    public Alunos(long _id, String nome){
        this._id    = _id;
        this.nome   = nome;
    }

    public long get_id() {
        return _id;
    }
    public void set_id(long _id) {
        this._id = _id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString(){
        return "\n"+ "Nome : "+nome+"\n";
    }

}//Final
