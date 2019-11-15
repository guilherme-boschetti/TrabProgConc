package com.example.trabprogconc;

import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

public class RelogioThread extends Thread {

    private int hora, minuto, segundo;
    private TextView field;
    private String tempo;

    public RelogioThread(TextView tf) {

        field = tf;
        hora = 0;
        minuto = 0;
        segundo = 0;
    }

    public void run() {

        boolean notHaveException = true;
        while (notHaveException) {

            String strHora = StringUtils.leftPad(String.valueOf(hora), 2, "0");
            String strMinuto = StringUtils.leftPad(String.valueOf(minuto), 2, "0");
            String strSegundo = StringUtils.leftPad(String.valueOf(segundo), 2, "0");
            tempo = strHora + ":" + strMinuto + ":" + strSegundo;

            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                public void run() {
                    // UI code goes here
                    field.setText(tempo);
                }
            });

            try {

                sleep(1000);

            } catch (InterruptedException e) {
                notHaveException = false;
            }

            segundo++;

            if (segundo == 60) {
                minuto++;
                segundo = 0;
            }
            if (minuto == 60) {
                hora++;
                minuto = 0;
            }
            if (hora == 24) {
                hora = 0;
            }

        }

    }

}
