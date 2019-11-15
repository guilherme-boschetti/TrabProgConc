package com.example.trabprogconc;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class AmbienteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambiente);

        // pega os parâmetros
        Intent intent = getIntent();
        int qtTubaroes = intent.getIntExtra("qtTubaroes", 0);
        int qtFocas = intent.getIntExtra("qtFocas", 0);
        int qtPeixes = intent.getIntExtra("qtPeixes", 0);
        int qtAlgas = intent.getIntExtra("qtAlgas", 0);
        int inicialCal = intent.getIntExtra("inicialCal", 0);
        int decCal = intent.getIntExtra("decCal", 0);
        int decTempo = intent.getIntExtra("decTempo", 0);
        int incCal = intent.getIntExtra("incCal", 0);
        int LINHAS = intent.getIntExtra("linhas", 0);
        int COLUNAS = intent.getIntExtra("colunas", 0);

        // pega as views da tela
        TextView txtTempo = findViewById(R.id.txtTempo);
        TextView txtTubaroes = findViewById(R.id.txtTubaroes);
        TextView txtFocas = findViewById(R.id.txtFocas);
        TextView txtPeixes = findViewById(R.id.txtPeixes);
        TextView txtAlgas = findViewById(R.id.txtAlgas);
        GridLayout gridLayout = findViewById(R.id.gridLayout);

        // declara a matriz
        Drawable[][] matriz = new Drawable[LINHAS][COLUNAS];

        // == ==

        // Carrega as imagens dos recursos
        Drawable shark = getResources().getDrawable(R.drawable.ic_shark);
        Drawable seal = getResources().getDrawable(R.drawable.ic_seal);
        Drawable fish = getResources().getDrawable(R.drawable.ic_fish);
        Drawable seaweed = getResources().getDrawable(R.drawable.ic_seaweed);
        Drawable emptySquare = getResources().getDrawable(R.drawable.ic_empty_square);

        // == ==

        // Inicializa a matriz
        for (int i = 0; i < LINHAS; i++) {
            for (int j = 0; j < COLUNAS; j++) {
                matriz[i][j] = emptySquare;
            }
        }

        // == ==

        // Inicia o relógio
        RelogioThread relogio = new RelogioThread(txtTempo);
        relogio.start();

        // == ==

        // monta o layout do ambiente
        for (int i = 0; i < LINHAS; i++) {
            for (int j = 0; j < COLUNAS; j++) {

                GridLayout.Spec linha = GridLayout.spec(i);
                GridLayout.Spec coluna = GridLayout.spec(j);
                GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(linha, coluna);

                ImageView imageView = new ImageView(this);
                imageView.setImageDrawable(matriz[i][j]);
                String tag = "" + i + "" + j;
                imageView.setTag(tag);

                gridLayout.addView(imageView, layoutParams);
            }
        }

        // == ==

        // Inicializa os marcadores
        txtTubaroes.setText(String.valueOf(qtTubaroes));
        txtFocas.setText(String.valueOf(qtFocas));
        txtPeixes.setText(String.valueOf(qtPeixes));
        txtAlgas.setText(String.valueOf(qtAlgas));

        SharedVariablesUtil.qtTubaroes = qtTubaroes;
        SharedVariablesUtil.qtFocas = qtFocas;
        SharedVariablesUtil.qtPeixes = qtPeixes;
        SharedVariablesUtil.qtAlgas = qtAlgas;

        // == Instancia e inicia as threads ==

        // https://www.mkyong.com/java/java-thread-mutex-and-semaphore-example/
        Semaphore semaphore = new Semaphore(1);

        // Tubarões
        TubaraoThread[] t = new TubaraoThread[qtTubaroes];
        for (int i = 0; i < qtTubaroes; i++) {
            t[i] = new TubaraoThread(i,
                    inicialCal, decCal, decTempo, incCal,
                    LINHAS, COLUNAS, matriz, gridLayout,
                    shark, seal, fish, seaweed, emptySquare,
                    semaphore,
                    txtTubaroes, txtFocas, txtPeixes);
            t[i].start();
        }

        // Focas
        FocaThread[] f = new FocaThread[qtFocas];
        for (int i = 0; i < qtFocas; i++) {
            f[i] = new FocaThread(i,
                    inicialCal, decCal, decTempo, incCal,
                    LINHAS, COLUNAS, matriz, gridLayout,
                    shark, seal, fish, seaweed, emptySquare,
                    semaphore,
                    txtFocas, txtPeixes);
            f[i].start();
        }

        // Peixes
        PeixeThread[] p = new PeixeThread[qtPeixes];
        for (int i = 0; i < qtPeixes; i++) {
            p[i] = new PeixeThread(i,
                    inicialCal, decCal, decTempo, incCal,
                    LINHAS, COLUNAS, matriz, gridLayout,
                    shark, seal, fish, seaweed, emptySquare,
                    semaphore,
                    txtPeixes, txtAlgas);
            p[i].start();
        }

        // == ==

        // Algas
        Random randomGenerator = new Random();
        for (int i = 0; i < qtAlgas; i++) {
            int lin = randomGenerator.nextInt(LINHAS);
            int col = randomGenerator.nextInt(COLUNAS);
            matriz[lin][col] = seaweed;
            ImageView imageView = (ImageView) gridLayout.getChildAt(COLUNAS * lin + col);
            imageView.setImageDrawable(seaweed);
        }

        // == == -- -- -- -- -- do not discomment this -- -- -- -- -- -- -- == ==

        /*try {
            relogio.join();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        /*for(int i=0;i<qtTubaroes;i++) {
            try {
                t[i].join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for(int i=0;i<qtFocas;i++) {
            try {
                f[i].join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for(int i=0;i<qtPeixes;i++) {
            try {
                p[i].join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/

        // == ==
    }

}
