package com.dozeroaosenior.adivinheanimes;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.squareup.picasso.Picasso;

import java.util.Random;

import static com.dozeroaosenior.adivinheanimes.MainActivity.vezesJogada;

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
    EstruturaAnime[] proximaEstruturaAnimes = new EstruturaAnime[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_jogo);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        capturaComponentesTela();
    }

    @Override
    protected void onResume() {
        super.onResume();
        configuraDesafio();
        new Thread(() -> {
            runOnUiThread(this::chamaConfiguracoesNovoJogo);
        }).start();
    }

    private void chamaConfiguracoesNovoJogo() {
        ConfiguraCamposJogo configuraCamposJogo = new ConfiguraCamposJogo();
        proximaEstruturaAnimes = configuraCamposJogo.fazRequisicaoNovoDesafio();
    }

    private void capturaComponentesTela() {
        botaoUm = findViewById(R.id.jogo_botao_resposta1);
        botaoDois = findViewById(R.id.jogo_botao_resposta2);
        botaoTres = findViewById(R.id.jogo_botao_resposta3);
        botaoQuatro = findViewById(R.id.jogo_botao_resposta4);
        descricaoAnime = findViewById(R.id.jogo_descricao_anime);
        imagemAnime = findViewById(R.id.jogo_imagem_anime);
    }

    private void configuraDesafio() {
        recebeInformacoesDesafio();
        configuraBotoesDesafio();
        verificaRespostaBotoes();
    }

    private void configuraBotoesDesafio() {
        configuraAnimeCertoDesafio();
        configuraTextoBotoesDesafio();
    }

    private void configuraAnimeCertoDesafio() {
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(this);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();
        numeroBotaoAleatorio = random.nextInt(4);
        tituloAnimeCerto = estruturaAnimes[numeroBotaoAleatorio].titulo;
        Picasso.get().load(estruturaAnimes[numeroBotaoAleatorio].getImagem()).placeholder(circularProgressDrawable).into(imagemAnime);
    }

    private void configuraTextoBotoesDesafio() {
        botaoUm.setText(estruturaAnimes[0].getTitulo());
        botaoDois.setText(estruturaAnimes[1].getTitulo());
        botaoTres.setText(estruturaAnimes[2].getTitulo());
        botaoQuatro.setText(estruturaAnimes[3].getTitulo());
    }

    private void recebeInformacoesDesafio() {
        Parcelable[] estruturaAnime = getIntent().getParcelableArrayExtra("estruturaAnime");
        for (int indice = 0; indice < 4; indice++) {
            estruturaAnimes[indice] = (EstruturaAnime) estruturaAnime[indice];
        }
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
                    botao.setBackgroundColor(Color.parseColor("#388e3c"));
                    if (vezesJogada < 5) {
                        while (proximaEstruturaAnimes == null) {
                        }
                        vezesJogada++;
                        Intent intent;
                        intent = new Intent(this, JogoActivity.class);
                        intent.putExtra("estruturaAnime", proximaEstruturaAnimes);
                        startActivity(intent);
                    } else {
                        Intent intent;
                        intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                    }
                    finish();
                } else {
                    botao.setBackgroundColor(Color.RED);
                    Toast.makeText(this, "Errou", Toast.LENGTH_SHORT).show();
                    Intent intent;
                    intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}