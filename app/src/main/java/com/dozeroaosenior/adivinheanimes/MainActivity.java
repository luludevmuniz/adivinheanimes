package com.dozeroaosenior.adivinheanimes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    EstruturaAnime[] estruturaAnimes = new EstruturaAnime[4];
    public static int vezesJogada;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConfiguraCamposJogo configuraCamposJogo = new ConfiguraCamposJogo();
        estruturaAnimes = configuraCamposJogo.fazRequisicaoNovoDesafio();
    }

    @Override
    protected void onStart() {
        super.onStart();
        vezesJogada = 0;
    }

    public void iniciarJogo(View view) {
        try {
                vezesJogada++;
                Intent intent;
                intent = new Intent(this, JogoActivity.class);
                intent.putExtra("estruturaAnime", estruturaAnimes);
                startActivity(intent);
        } catch(Exception e){
            System.out.println(e);
        }
    }
}