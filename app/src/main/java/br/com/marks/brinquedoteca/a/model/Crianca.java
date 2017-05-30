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
}
