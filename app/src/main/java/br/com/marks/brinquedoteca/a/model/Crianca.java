package br.com.marks.brinquedoteca.a.model;

/**
 * Created by Gabriel on 18/05/2017.
 */

public class Crianca {
    private String nome;
    private Integer idade;

    public Crianca(String nome, Integer idade) {
        this.nome = nome;
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }
}
