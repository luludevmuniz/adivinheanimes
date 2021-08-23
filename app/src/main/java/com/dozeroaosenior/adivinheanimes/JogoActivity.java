package com.dozeroaosenior.adivinheanimes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.squareup.picasso.Picasso;

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
    int numeroBotaoAleatorio;
    String tituloAnimeCerto;
    EstruturaAnime[] estruturaAnimes = new EstruturaAnime[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_jogo);
            capturaComponentesTela();
            if (getIntent().hasExtra("estruturaAnime4")) {
                configuraProximoDesafio();
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
            Picasso.get().load(estruturaAnimes[0].getImagem()).into(imagemAnime);
        } else if (botaoDois.getText().equals(tituloAnimeCerto)) {
            Picasso.get().load(estruturaAnimes[1].getImagem()).into(imagemAnime);
        } else if (botaoTres.getText().equals(tituloAnimeCerto)) {
            Picasso.get().load(estruturaAnimes[2].getImagem()).into(imagemAnime);
        } else if (botaoQuatro.getText().equals(tituloAnimeCerto)) {
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

    private void verificaResposta(AppCompatButton botao) {
        try {
            botao.setOnClickListener(v -> {
                if (botao.getText().equals(tituloAnimeCerto)) {
                     Intent intent = new Intent(JogoActivity.this, ConfiguracoesJogoActivity.class);
                        startActivity(intent);
                        finish();
                } else {
                    Toast.makeText(this, "Errou", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}