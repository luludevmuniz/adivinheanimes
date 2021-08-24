package com.dozeroaosenior.adivinheanimes;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.BuscaInformacoesAnimeQuery;
import com.example.BuscaMaiorPopularidadeQuery;

import org.jetbrains.annotations.NotNull;

public class InformacoesJogo extends AppCompatActivity {
    String tituloAnimeRomaji;
    String imagemAnime;
    int popularidade;
    int idAnime;
    int numeroAnime = 0;

    public void pegaInformacoesAnime(int popularidade) {
        try {
            ApolloClient apolloClient = ApolloClient.builder()
                    .serverUrl("https://graphql.anilist.co")
                    .build();

            apolloClient.query(new BuscaInformacoesAnimeQuery(popularidade))
                    .enqueue(new ApolloCall.Callback<BuscaInformacoesAnimeQuery.Data>() {
                        @Override
                        public void onResponse(@NotNull Response<BuscaInformacoesAnimeQuery.Data> response) {
                            tituloAnimeRomaji = response.getData().Media().title().romaji();
                            idAnime = response.getData().Media().id();
                            imagemAnime = response.getData().Media().coverImage().extraLarge();
                            numeroAnime++;
                        }

                        @Override
                        public void onFailure(@NotNull ApolloException e) {
                        }
                    });
        } catch (Exception e)
        {
            Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void pegaPopularidadeMaxima() {
        try {
            ApolloClient apolloClient = ApolloClient.builder()
                    .serverUrl("https://graphql.anilist.co")
                    .build();

            apolloClient.query(new BuscaMaiorPopularidadeQuery(16498))
                    .enqueue(new ApolloCall.Callback<BuscaMaiorPopularidadeQuery.Data>() {
                        @Override
                        public void onResponse(@NotNull Response<BuscaMaiorPopularidadeQuery.Data> response) {
                            popularidade = response.getData().Media().popularity();
                        }

                        @Override
                        public void onFailure(@NotNull ApolloException e) {
                        }
                    });
        } catch (Exception e)
        {
            Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void clear(){
        tituloAnimeRomaji = null;
        imagemAnime = null;
        popularidade = 0;
        idAnime = 0;
    }
}

