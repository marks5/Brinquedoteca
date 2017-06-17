package br.com.marks.brinquedoteca.a.model;

import org.parceler.Parcel;

/**
 * Created by Gabriel on 18/05/2017.
 */

@Parcel
public class Crianca {

    //Basic
    public String nome; //Nome Completo
    public Integer idade; //Numericos simples: 5/6/7/8 anos
    public String identidade;

    //IMGUR
    public String urlImagem;
    public String urlHash;

    public String nomeResponsavel;

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

    public String getIdentidade() {
        return identidade;
    }

    public void setIdentidade(String identidade) {
        this.identidade = identidade;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public String getUrlHash() {
        return urlHash;
    }

    public void setUrlHash(String urlHash) {
        this.urlHash = urlHash;
    }

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    @Override
    public String toString() {
        return "Crianca{" +
                "nome='" + nome + '\'' +
                ", idade=" + idade +
                ", identidade='" + identidade + '\'' +
                ", urlImagem='" + urlImagem + '\'' +
                ", urlHash='" + urlHash + '\'' +
                ", nomeResponsavel='" + nomeResponsavel + '\'' +
                '}';
    }
}
