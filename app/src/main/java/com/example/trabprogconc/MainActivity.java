package com.example.trabprogconc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // https://www.youtube.com/watch?v=47IwP-FmKNQ
    // https://stackoverflow.com/questions/12458383/java-random-numbers-using-a-seed
    // https://codippa.com/how-to-generate-a-random-number-with-in-a-range-in-java/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Pega as views da tela
        final EditText edtQtTubaroes = findViewById(R.id.edtQtTubaroes);
        final EditText edtQtFocas = findViewById(R.id.edtQtFocas);
        final EditText edtQtPeixes = findViewById(R.id.edtQtPeixes);
        final EditText edtQtAlgas = findViewById(R.id.edtQtAlgas);
        final EditText edtInicialCal = findViewById(R.id.edtInicialCal);
        final EditText edtDecCal = findViewById(R.id.edtDecCal);
        final EditText edtDecTempo = findViewById(R.id.edtDecTempo);
        final EditText edtIncCal = findViewById(R.id.edtIncCal);
        final EditText edtLinhas = findViewById(R.id.edtLinhas);
        final EditText edtColunas = findViewById(R.id.edtColunas);
        final Button btnConcluir = findViewById(R.id.btnConcluir);

        // Listener do botão
        btnConcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Faz validação dos campos
                if (edtQtTubaroes.getText().toString().isEmpty() ||
                        edtQtFocas.getText().toString().isEmpty() ||
                        edtQtPeixes.getText().toString().isEmpty() ||
                        edtQtAlgas.getText().toString().isEmpty() ||
                        edtInicialCal.getText().toString().isEmpty() ||
                        edtDecCal.getText().toString().isEmpty() ||
                        edtDecTempo.getText().toString().isEmpty() ||
                        edtIncCal.getText().toString().isEmpty() ||
                        edtLinhas.getText().toString().isEmpty() ||
                        edtColunas.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Necessário preencher todos os campos", Toast.LENGTH_SHORT).show();
                    return;
                }
                // abre a nova tela passando os parâmetros
                Intent intent = new Intent(MainActivity.this, AmbienteActivity.class);
                intent.putExtra("qtTubaroes", Integer.parseInt(edtQtTubaroes.getText().toString()));
                intent.putExtra("qtFocas", Integer.parseInt(edtQtFocas.getText().toString()));
                intent.putExtra("qtPeixes", Integer.parseInt(edtQtPeixes.getText().toString()));
                intent.putExtra("qtAlgas", Integer.parseInt(edtQtAlgas.getText().toString()));
                intent.putExtra("inicialCal", Integer.parseInt(edtInicialCal.getText().toString()));
                intent.putExtra("decCal", Integer.parseInt(edtDecCal.getText().toString()));
                intent.putExtra("decTempo", Integer.parseInt(edtDecTempo.getText().toString()));
                intent.putExtra("incCal", Integer.parseInt(edtIncCal.getText().toString()));
                intent.putExtra("linhas", Integer.parseInt(edtLinhas.getText().toString()));
                intent.putExtra("colunas", Integer.parseInt(edtColunas.getText().toString()));
                startActivity(intent);
            }
        });
    }
}
