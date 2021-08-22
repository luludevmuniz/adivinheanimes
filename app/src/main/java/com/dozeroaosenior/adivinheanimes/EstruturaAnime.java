package com.dozeroaosenior.adivinheanimes;

import android.os.Parcel;
import android.os.Parcelable;

public class EstruturaAnime implements Parcelable {
    String titulo;
    String imagem;

    public EstruturaAnime(String titulo, String imagem) {
        this.titulo = titulo;
        this.imagem = imagem;
    }

    public EstruturaAnime(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.titulo);
        dest.writeString(this.imagem);
    }

    public void readFromParcel(Parcel source) {
        this.titulo = source.readString();
        this.imagem = source.readString();
    }

    protected EstruturaAnime(Parcel in) {
        this.titulo = in.readString();
        this.imagem = in.readString();
    }

    public static final Parcelable.Creator<EstruturaAnime> CREATOR = new Parcelable.Creator<EstruturaAnime>() {
        @Override
        public EstruturaAnime createFromParcel(Parcel source) {
            return new EstruturaAnime(source);
        }

        @Override
        public EstruturaAnime[] newArray(int size) {
            return new EstruturaAnime[size];
        }
    };
}
