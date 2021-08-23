package com.dozeroaosenior.adivinheanimes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    public static int vezesJogada;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void iniciarJogo(View view) {
        try {
            Intent irParaJogoActivity = new Intent(this, ConfiguracoesJogoActivity.class);
            startActivity(irParaJogoActivity);
        } catch(Exception e){
            System.out.println(e);
        }
    }
}