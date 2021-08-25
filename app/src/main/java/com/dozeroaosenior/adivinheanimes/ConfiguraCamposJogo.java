package com.dozeroaosenior.adivinheanimes;

import android.content.Context;
import android.util.Log;

import com.squareup.picasso.Picasso;

import java.util.Random;

public class ConfiguraCamposJogo {
    InformacoesJogo informacoesJogo = new InformacoesJogo();
    Random random = new Random();
    int maxPopularidade = 0;
    int minPopularidade = 100;
    int randomNum;
    EstruturaAnime[] estruturaAnimes = new EstruturaAnime[4];
    int[] idsAnimes = new int[4];

    public EstruturaAnime[] fazRequisicaoNovoDesafio() {
        try {
                    geraPopularidadeMaxima();
                    while (informacoesJogo.popularidade == 0) {
                    }
                    geraPopularidadeAleatoria();

                    preencheEstruturaAnimes();
                    while (estruturaAnimes[3].imagem == null) {
                    }

        } catch (Exception e) {
            Log.println(Log.ERROR, "Erro", e.toString());
        }
        return estruturaAnimes;
    }

    private void preencheEstruturaAnimes() {
        try {
            for (int index = 0; index < 4; index++) {
                if (validaIdAnime(informacoesJogo.idAnime)) {
                    index = 0;
                }
                informacoesJogo.pegaInformacoesAnime(geraPopularidadeAleatoria());
                while (informacoesJogo.imagemAnime == null) {
                }
                estruturaAnimes[index] = new EstruturaAnime(informacoesJogo.tituloAnimeRomaji, informacoesJogo.imagemAnime);
                Picasso.get().load(estruturaAnimes[index].getImagem()).fetch();
                informacoesJogo.imagemAnime = null;
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private boolean validaIdAnime(int idAnime) {
        boolean isIdDuplicado = false;
        try {
            if (idsAnimes[0] != 0) {
                for (int index = 0; index < 4; index++) {
                    if (idAnime == idsAnimes[index]) {
                        isIdDuplicado = true;
                    }
                }
            } else {
                idsAnimes[0] = idAnime;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return isIdDuplicado;

    }

    private int geraPopularidadeAleatoria() {
        try {
//            maxPopularidade = informacoesJogo.popularidade;
//            if (randomNum == 0) {
//                randomNum = random.nextInt(maxPopularidade - minPopularidade) + minPopularidade;
//            } else if ((randomNum + minPopularidade) < maxPopularidade) {
//                randomNum = random.nextInt((randomNum + minPopularidade) - randomNum) + minPopularidade;
//            } else {
//                randomNum = random.nextInt(randomNum - minPopularidade) + minPopularidade;
//            }
            maxPopularidade = 80000;
            randomNum = random.nextInt(maxPopularidade - minPopularidade) + minPopularidade;

        } catch (Exception e) {
            System.out.println(e);
        }
        return randomNum;
    }

    private void geraPopularidadeMaxima() {
        try {
            informacoesJogo.pegaPopularidadeMaxima();
            while (informacoesJogo.popularidade == 0) {
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
