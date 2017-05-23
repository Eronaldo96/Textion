package com.example.erona.textion;

import java.io.Serializable;

/**
 * Created by erona on 10/04/2017.
 */

public class Textos implements Serializable {

    private String titulo;
    private String texto;
    private int status;//0 = rascunho 1 = publicar

    public Textos(){

    }

    public String getTitulo() {return titulo;}

    public void setTitulo(String titulo) {this.titulo = titulo;}

    public Textos(String texto, String titulo){this.texto = texto;this.titulo = titulo;}

    public String getTexto(){
        return texto;
    }

    public void setTexto(String texto){
        this.texto = texto;
    }



    public int getStatus() {return status;}

    public void setStatus(int status) {this.status = status;}

    @Override
    public String toString() {
        return titulo;
    }


}