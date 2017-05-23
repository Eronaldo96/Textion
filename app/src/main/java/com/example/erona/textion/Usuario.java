package com.example.erona.textion;

import android.media.Image;

/**
 * Created by erona on 10/04/2017.
 */

public class Usuario
{
    private String login;
    private String nome;
    private String data;
    private String sexo;
    private String idUser;
    private Image foto;

    public Usuario()
    {

    }

    public Usuario(String login, String nome, String data, String sexo, String idUser) {
        this.login = login;
        this.nome = nome;
        this.data = data;
        this.sexo = sexo;
        this.idUser = idUser;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
