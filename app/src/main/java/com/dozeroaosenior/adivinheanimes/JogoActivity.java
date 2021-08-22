package com.dozeroaosenior.adivinheanimes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class JogoActivity extends AppCompatActivity {
    AppCompatButton botaoUm;
    AppCompatButton botaoDois;
    AppCompatButton botaoTres;
    AppCompatButton botaoQuatro;
    AppCompatTextView descricaoAnime;
    ImageView imagemAnime;
    InformacoesJogo informacoesJogo = new InformacoesJogo();
    Random random = new Random();
    int posicaoListaIds = 0;
    int maxPopularidade = 0;
    int minPopularidade = 150000;
    int randomNum;
    int popularidadeAnimeUm;
    int popularidadeAnimeDois;
    int popularidadeAnimeTres;
    int popularidadeAnimeQuatro;
    List<Integer> numeroBotao = new LinkedList<>(Arrays.asList(0, 1, 2, 3));
    int tentativa;
    int numeroBotaoAleatorio;
    String tituloAnimeCerto;
    String imagemAnimeCerto;
    String tituloAnimeDois;
    String tituloAnimeTres;
    String tituloAnimeQuatro;
    EstruturaAnime[] estruturaAnimes = new EstruturaAnime[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_jogo);
            capturaComponentesTela();
            int tentativa = getIntent().getIntExtra("numeroTentativas", 0);
            if (getIntent().hasExtra("estruturaAnime4")) {
                configuraProximoDesafio();
            } else {
                configuraComponentesTela();
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private void configuraProximoDesafio() {
        recebeInformacoesNovoDesafio();
        configuraBotoesNovoDesafio();
        verificaRespostaBotoes();
    }

    private void configuraBotoesNovoDesafio() {
        configuraTituloCertoNovoDesafio();
        configuraTextoBotoesNovoDesafio();
        configuraImagemNovoDesafio();
    }

    private void configuraTituloCertoNovoDesafio() {
        numeroBotaoAleatorio = random.nextInt(4);
        tituloAnimeCerto = estruturaAnimes[numeroBotaoAleatorio].titulo;
    }

    private void configuraTextoBotoesNovoDesafio() {
        botaoUm.setText(estruturaAnimes[0].getTitulo());
        botaoDois.setText(estruturaAnimes[1].getTitulo());
        botaoTres.setText(estruturaAnimes[2].getTitulo());
        botaoQuatro.setText(estruturaAnimes[3].getTitulo());
    }

    private void configuraImagemNovoDesafio() {
        if (botaoUm.getText().equals(tituloAnimeCerto)) {
            if(estruturaAnimes[0].getImagem() == null)
            {
                informacoesJogo.pegaImagemAnime(estruturaAnimes[0].getTitulo());
                while(estruturaAnimes[0].getImagem() == null){}
                estruturaAnimes[0].imagem = informacoesJogo.imagemAnime;
            }
            Picasso.get().load(estruturaAnimes[0].getImagem()).into(imagemAnime);
        } else if (botaoDois.getText().equals(tituloAnimeCerto)) {
            if(estruturaAnimes[1].getImagem() == null)
            {
                informacoesJogo.pegaImagemAnime(estruturaAnimes[1].getTitulo());
                while(estruturaAnimes[0].getImagem() == null){}
                estruturaAnimes[1].imagem = informacoesJogo.imagemAnime;
            }
            Picasso.get().load(estruturaAnimes[1].getImagem()).into(imagemAnime);
        } else if (botaoTres.getText().equals(tituloAnimeCerto)) {
            if(estruturaAnimes[2].getImagem() == null)
            {
                informacoesJogo.pegaImagemAnime(estruturaAnimes[2].getTitulo());
                while(estruturaAnimes[0].getImagem() == null){}
                estruturaAnimes[2].imagem = informacoesJogo.imagemAnime;
            }
            Picasso.get().load(estruturaAnimes[2].getImagem()).into(imagemAnime);
        } else if (botaoQuatro.getText().equals(tituloAnimeCerto)) {
            if(estruturaAnimes[3].getImagem() == null)
            {
                informacoesJogo.pegaImagemAnime(estruturaAnimes[3].getTitulo());
                while(estruturaAnimes[0].getImagem() == null){}
                estruturaAnimes[3].imagem = informacoesJogo.imagemAnime;
            }
            Picasso.get().load(estruturaAnimes[3].getImagem()).into(imagemAnime);
        }
    }

    private void recebeInformacoesNovoDesafio() {
        estruturaAnimes[0] = getIntent().getParcelableExtra("estruturaAnime1");
        estruturaAnimes[1] = getIntent().getParcelableExtra("estruturaAnime2");
        estruturaAnimes[2] = getIntent().getParcelableExtra("estruturaAnime3");
        estruturaAnimes[3] = getIntent().getParcelableExtra("estruturaAnime4");
    }

    private void capturaComponentesTela() {
        botaoUm = findViewById(R.id.jogo_botao_resposta1);
        botaoDois = findViewById(R.id.jogo_botao_resposta2);
        botaoTres = findViewById(R.id.jogo_botao_resposta3);
        botaoQuatro = findViewById(R.id.jogo_botao_resposta4);
        descricaoAnime = findViewById(R.id.jogo_descricao_anime);
        imagemAnime = findViewById(R.id.jogo_imagem_anime);
    }

    private void configuraComponentesTela() {
        fazerRequisicao();
        verificaRespostaBotoes();
    }

    private void verificaRespostaBotoes() {
        try {
            verificaResposta(botaoUm);
            verificaResposta(botaoDois);
            verificaResposta(botaoTres);
            verificaResposta(botaoQuatro);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void fazerRequisicao() {
        try {
            new Thread(() -> {
                runOnUiThread(() -> {
                    geraPopularidadeMaxima();
                    geraIdsPopularidade();
                });

                runOnUiThread(() -> {
                    for (int numeroAnime = 1; numeroAnime < 5; numeroAnime++) {
                        informacoesJogo.pegaInformacoesAnime(geraIdsPopularidade());
                        while (informacoesJogo.tituloAnimeRomaji == null) {
                        }
//                        configuraIdAnime();
                        configuraBotaoAleatorio();
                    }
                });
            }).start();

        } catch (Exception e) {
            Log.println(Log.ERROR, "Erro", e.toString());
        }
    }

    private void fazerRequisicaoNovoDesafio() {
        try {
            new Thread(() -> {
                runOnUiThread(() -> {
                    geraPopularidadeMaxima();
                    geraIdsPopularidade();
                });

                runOnUiThread(() -> {
                    for (int index = 0; index < 4; index++) {
                        informacoesJogo.tituloAnimeRomaji = null;
                        informacoesJogo.imagemAnime = null;
                        informacoesJogo.pegaInformacoesAnime(geraIdsPopularidade());
                        while (informacoesJogo.tituloAnimeRomaji == null && informacoesJogo.imagemAnime == null) {
                        }
                        estruturaAnimes[index] = new EstruturaAnime(informacoesJogo.tituloAnimeRomaji, informacoesJogo.imagemAnime);
                        if (index == 3) {
                            while (estruturaAnimes[3] == null) {
                            }
                        }
                    }
                });

                runOnUiThread(this::entraNovoDesafio);
            }).start();

        } catch (Exception e) {
            Log.println(Log.ERROR, "Erro", e.toString());
        }
    }

    private void entraNovoDesafio() {
        try {
            Intent intent;
            intent = new Intent(JogoActivity.this, JogoActivity.class);
            intent.putExtra("estruturaAnime1", estruturaAnimes[0]);
            intent.putExtra("estruturaAnime2", estruturaAnimes[1]);
            intent.putExtra("estruturaAnime3", estruturaAnimes[2]);
            intent.putExtra("estruturaAnime4", estruturaAnimes[3]);
            startActivity(intent);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void configuraAnimeCerto() {
        if (tituloAnimeCerto == null) {
            Picasso.get().load(informacoesJogo.imagemAnime).into(imagemAnime);
            tituloAnimeCerto = informacoesJogo.tituloAnimeRomaji;
        }
    }

    private boolean configuraIdAnime() {
        try {
//                if (informacoesJogo.idsAnimes[posicaoListaIds] == 0) {
//                    if (informacoesJogo.idsAnimes[0] != 0)
//                    {
//                        for (int anime:informacoesJogo.idsAnimes){
//                            if(informacoesJogo.idAnime == anime){
//                                informacoesJogo.tituloAnime = null;
//                                pegaInformacaoAnime(geraIdsPopularidade());
//                                configuraIdAnime();
//                                posicaoListaIds++;
//                            }
//                            else{
//                                informacoesJogo.idsAnimes[posicaoListaIds] = informacoesJogo.idAnime;
//                                posicaoListaIds++;
//                            }
//                        }
//                    }
//                    else {
            informacoesJogo.idsAnimes[posicaoListaIds] = informacoesJogo.idAnime;
            posicaoListaIds++;
//                    }
//                }
        } catch (Exception e) {
            System.out.println(e);
        }
        return true;

    }

    private int geraIdsPopularidade() {
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

    private void verificaResposta(AppCompatButton botao) {
        try {
            botao.setOnClickListener(v -> {
                if (botao.getText().equals(tituloAnimeCerto)) {
                    tentativa++;
                    Intent intent;
                    if (tentativa < 6) {
                        fazerRequisicaoNovoDesafio();
                    } else {
                        intent = new Intent(JogoActivity.this, FimDoJogoActivity.class);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(this, "Errou", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void configuraBotaoAleatorio() {
        try {
            numeroBotaoAleatorio = random.nextInt(4);
            while (!numeroBotao.contains(numeroBotaoAleatorio)) {
                numeroBotaoAleatorio = random.nextInt(4);
            }
            configuraTextoBotao(numeroBotaoAleatorio);
            numeroBotao.remove((Integer) numeroBotaoAleatorio);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void configuraTextoBotao(int numeroBotao) {
        try {
            switch (numeroBotao) {
                case 0:
                    botaoUm.setText(informacoesJogo.tituloAnimeRomaji);
                    configuraAnimeCerto();
                    informacoesJogo.tituloAnimeRomaji = null;
                    break;
                case 1:
                    botaoDois.setText(informacoesJogo.tituloAnimeRomaji);
                    configuraAnimeCerto();
                    informacoesJogo.tituloAnimeRomaji = null;
                    break;
                case 2:
                    botaoTres.setText(informacoesJogo.tituloAnimeRomaji);
                    configuraAnimeCerto();
                    informacoesJogo.tituloAnimeRomaji = null;
                    break;
                case 3:
                    botaoQuatro.setText(informacoesJogo.tituloAnimeRomaji);
                    configuraAnimeCerto();
                    informacoesJogo.tituloAnimeRomaji = null;
                    break;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}