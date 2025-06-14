package com.example.agendapessoal.model;

import java.util.Date;

public class Compromisso {
    private long id;
    private Date data;
    private String hora;
    private String descricao;

    // Construtor vazio
    public Compromisso() {
    }

    // Construtor com parâmetros
    public Compromisso(Date data, String hora, String descricao) {
        this.data = data;
        this.hora = hora;
        this.descricao = descricao;
    }

    // Construtor completo com ID
    public Compromisso(long id, Date data, String hora, String descricao) {
        this.id = id;
        this.data = data;
        this.hora = hora;
        this.descricao = descricao;
    }

    // Getters e Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return hora + ": " + descricao;
    }
}