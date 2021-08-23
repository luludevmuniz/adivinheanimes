package com.dozeroaosenior.adivinheanimes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import static com.dozeroaosenior.adivinheanimes.MainActivity.vezesJogada;

public class FimDoJogoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fim_do_jogo);
        vezesJogada = 0;
    }
}