package com.dozeroaosenior.adivinheanimes;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import static com.dozeroaosenior.adivinheanimes.MainActivity.vezesJogada;

public class FimDoJogoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fim_do_jogo);
        vezesJogada = 0;
    }
}