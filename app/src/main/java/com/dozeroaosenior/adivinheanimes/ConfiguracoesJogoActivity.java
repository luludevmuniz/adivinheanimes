package com.dozeroaosenior.adivinheanimes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

import static com.dozeroaosenior.adivinheanimes.MainActivity.vezesJogada;

public class ConfiguracoesJogoActivity extends AppCompatActivity {
    InformacoesJogo informacoesJogo = new InformacoesJogo();
    Random random = new Random();
    int maxPopularidade = 0;
    int minPopularidade = 150000;
    int randomNum;
    EstruturaAnime[] estruturaAnimes = new EstruturaAnime[4];
    int[] idsAnimes = new int[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes_jogo);
        try {
            fazRequisicaoNovoDesafio();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private void fazRequisicaoNovoDesafio() {
        try {
            new Thread(() -> {
                runOnUiThread(() -> {
                    geraPopularidadeMaxima();
                    geraPopularidadeAleatoria();
                });
                runOnUiThread(this::preencheEstruturaAnimes);
                runOnUiThread(this::entraNovoDesafio);
            }).start();

        } catch (Exception e) {
            Log.println(Log.ERROR, "Erro", e.toString());
        }
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
                informacoesJogo.imagemAnime = null;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void entraNovoDesafio() {
        try {
            if (vezesJogada > 3){
                Intent intent = new Intent(ConfiguracoesJogoActivity.this, FimDoJogoActivity.class);
                startActivity(intent);
                finish();
            }
            else {
                vezesJogada++;
                Intent intent;
                intent = new Intent(ConfiguracoesJogoActivity.this, JogoActivity.class);
                intent.putExtra("estruturaAnime1", estruturaAnimes[0]);
                intent.putExtra("estruturaAnime2", estruturaAnimes[1]);
                intent.putExtra("estruturaAnime3", estruturaAnimes[2]);
                intent.putExtra("estruturaAnime4", estruturaAnimes[3]);
                startActivity(intent);
                finish();
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
                        System.out.println("ID DUPLICADO?" + isIdDuplicado);
                    }
                }
            } else {
                idsAnimes[0] = idAnime;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("ID DUPLICADO?" + isIdDuplicado);
        return isIdDuplicado;

    }

    private int geraPopularidadeAleatoria() {
        try {
            maxPopularidade = informacoesJogo.popularidade;
            if (randomNum == 0) {
                randomNum = random.nextInt(maxPopularidade - minPopularidade) + minPopularidade;
            } else if ((randomNum + minPopularidade) < maxPopularidade) {
                randomNum = random.nextInt((randomNum + minPopularidade) - randomNum) + minPopularidade;
            } else {
                randomNum = random.nextInt(randomNum - minPopularidade) + minPopularidade;
            }
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
