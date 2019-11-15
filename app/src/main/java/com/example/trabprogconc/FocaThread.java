package com.example.trabprogconc;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class FocaThread extends Thread {

    private int id;
    private int calorias;
    private int decCal;
    private int decTempo;
    private int incCal;
    private int LINHAS;
    private int COLUNAS;
    private Drawable[][] matriz;
    private int lin;
    private int col;
    private int linOld;
    private int colOld;
    private GridLayout gridLayout;
    private Drawable shark;
    private Drawable seal;
    private Drawable fish;
    private Drawable seaweed;
    private Drawable emptySquare;
    private TextView txtFocas;
    private TextView txtPeixes;

    private Semaphore semaphore;

    private boolean vivo;

    public FocaThread(int id,
                      int calorias, int decCal, int decTempo, int incCal,
                      int LINHAS, int COLUNAS, Drawable[][] matriz, GridLayout gridLayout,
                      Drawable shark, Drawable seal, Drawable fish, Drawable seaweed, Drawable emptySquare,
                      Semaphore semaphore,
                      TextView txtFocas, TextView txtPeixes) {

        this.id = id;
        this.calorias = calorias;
        this.decCal = decCal;
        this.decTempo = decTempo;
        this.incCal = incCal;
        this.LINHAS = LINHAS;
        this.COLUNAS = COLUNAS;
        this.matriz = matriz;
        this.gridLayout = gridLayout;
        this.shark = shark;
        this.seal = seal;
        this.fish = fish;
        this.seaweed = seaweed;
        this.emptySquare = emptySquare;
        this.txtFocas = txtFocas;
        this.txtPeixes = txtPeixes;

        this.semaphore = semaphore;
    }

    public void run() {

        System.out.println("Sou a thread FocaThread: " + id);
        Random randomGenerator = new Random();
        lin = randomGenerator.nextInt(LINHAS);
        col = randomGenerator.nextInt(COLUNAS);

        vivo = true;
        long segundos = 0;

        while (vivo) {

            try {

                sleep(1000); // movimenta a cada 1 segundo

                semaphore.acquire();
                System.out.println("2222222222 entrou");

                // Se neste ponto for um tubarão é porque a foca foi comida
                if (segundos > 0 && matriz[lin][col] == shark) {
                    vivo = false;
                } else {

                    final ImageView oldImageView = (ImageView) gridLayout.getChildAt(COLUNAS * lin + col);

                    linOld = lin;
                    colOld = col;

                    // == movimentação ==

                    // 0 -> cima; 1 -> baixo; 2 -> esquerda; 3 -> direita;
                    int direction = randomGenerator.nextInt(4);
                    switch (direction) {
                        case 0:
                            if (lin > 0) {
                                lin--;
                            } else {
                                lin++;
                            }
                            break;
                        case 1:
                            if (lin < LINHAS - 1) {
                                lin++;
                            } else {
                                lin--;
                            }
                            break;
                        case 2:
                            if (col > 0) {
                                col--;
                            } else {
                                col++;
                            }
                            break;
                        case 3:
                            if (col < COLUNAS - 1) {
                                col++;
                            } else {
                                col--;
                            }
                            break;
                    }

                    // == ==

                    // incremento de calorias por comida
                    if (matriz[lin][col] == fish) {
                        System.out.println("Comeu um peixe");
                        calorias += incCal;
                        SharedVariablesUtil.qtPeixes--;
                    }

                    // == ==

                    boolean movimentou = false;
                    if (matriz[lin][col] != shark && matriz[lin][col] != seal && matriz[lin][col] != seaweed) {
                        matriz[lin][col] = seal;
                        if (matriz[linOld][colOld] != seaweed && matriz[linOld][colOld] != shark) {
                            matriz[linOld][colOld] = emptySquare;
                        }
                        movimentou = true;
                    } else if (matriz[lin][col] == shark) {
                        calorias = 0; // morre
                    }

                    if (!movimentou && matriz[linOld][colOld] != seaweed) {
                        matriz[linOld][colOld] = emptySquare;
                    }

                    segundos++;

                    if (decTempo > 0 && segundos % decTempo == 0) { // decremento de calorias por tempo
                        calorias -= decCal;
                    }
                    final ImageView newImageView = (ImageView) gridLayout.getChildAt(COLUNAS * lin + col);
                    if (calorias <= 0) {
                        vivo = false;
                        SharedVariablesUtil.qtFocas--;
                        if (movimentou && matriz[lin][col] == seal) {
                            matriz[lin][col] = emptySquare;
                        }
                        System.out.println("FocaThread: " + id + " morreu");
                    }

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        public void run() {
                            // UI code goes here
                            oldImageView.setImageDrawable(matriz[linOld][colOld]);
                            newImageView.setImageDrawable(matriz[lin][col]);
                            txtPeixes.setText(String.valueOf(SharedVariablesUtil.qtPeixes));
                            if (!vivo) {
                                txtFocas.setText(String.valueOf(SharedVariablesUtil.qtFocas));
                            }
                        }
                    });

                }

            } catch (Exception e) {
                System.out.println("falha na thread foca " + id);
            } finally {
                System.out.println("2222222222 saiu");
                semaphore.release();
            }
        }
    }
}