package org.model;

public class Contato {
    private int id;

    private String nome;

    private String numero;

    public Contato() {
        this.id = 0;
        this.nome = "";
        this.numero = "";
    }

    public Contato(int id, String nome, String numero) {
        this.id = id;
        this.nome = nome;
        this.numero = numero;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "Contato [id = " + id + ", nome = " + nome + ", numero = " + numero + "]";
    }
    
}