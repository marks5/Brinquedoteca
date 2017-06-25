package br.com.marks.brinquedoteca.a.model;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Gabriel on 18/05/2017.
 */
public class Crianca implements Parcelable {

    public String nome;
    public Integer idade;
    public String identidade;
    public String urlImagem;
    public String urlHash;
    public String nomeResponsavel;

    public Crianca() {
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

    protected Crianca(Parcel in) {
        nome = in.readString();
        idade = in.readByte() == 0x00 ? null : in.readInt();
        identidade = in.readString();
        urlImagem = in.readString();
        urlHash = in.readString();
        nomeResponsavel = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nome);
        if (idade == null) {
            parcel.writeByte((byte) (0x00));
        } else {
            parcel.writeByte((byte) (0x01));
            parcel.writeInt(idade);
        }
        parcel.writeString(identidade);
        parcel.writeString(urlImagem);
        parcel.writeString(urlHash);
        parcel.writeString(nomeResponsavel);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Crianca> CREATOR = new Parcelable.Creator<Crianca>() {
        @Override
        public Crianca createFromParcel(Parcel in) {
            return new Crianca(in);
        }

        @Override
        public Crianca[] newArray(int size) {
            return new Crianca[size];
        }
    };
}
