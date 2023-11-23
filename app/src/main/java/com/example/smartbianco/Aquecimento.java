package com.example.smartbianco;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class Aquecimento extends AppCompatActivity {

    //Variáveis de elementos gráficos para interação
    private EditText aquecimentoInputTXT;
    private TextView aquecimentoCronoTXT;
    private Button  BtcAquecimentoSet;
    private Button  BtcAquecimentoStartPause;
    private Button  BtcAquecimentoReset;

    //Variáveis do cronômetro
    private CountDownTimer  mCountDownTimer;
    private boolean mTimerRunning;

    //Variáveis que controla o tempo
    private long    mIniciarTempoMillis;
    private long    mTimeLeftMillis;
    private long    mTempoFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aquecimento);

        //associação das variáveis com o XML
        aquecimentoInputTXT = findViewById(R.id.aquecimentoInputXML);
        aquecimentoCronoTXT = findViewById(R.id.aquecimentoCronoXML);

        BtcAquecimentoSet = findViewById(R.id.aquecimentoSetXML);
        BtcAquecimentoStartPause = findViewById(R.id.aquecimentoIniciarXML);
        BtcAquecimentoReset = findViewById(R.id.aquecimentoResetXML);

        BtcAquecimentoSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = aquecimentoInputTXT.getText().toString(); // Input de tempo do usuário
                if (input.length() == 0 ){
                    Toast.makeText(Aquecimento.this,"Não pode estar Vazio", Toast.LENGTH_SHORT).show();
                    return; //Condição não pode ser nula
                }

                long millisInput = Long.parseLong(input) * 60000; //Faz a constante de tempo para minutos do valor de entrada
                if (millisInput == 0){
                    Toast.makeText(Aquecimento.this,"Entre com Número Positivo", Toast.LENGTH_SHORT).show();
                    return; //Gera uma mensagem para entrar um valor 0
                }

                setTime(millisInput);
                aquecimentoInputTXT.setText(""); // Aparece o teclado para o  usuário digitar
            }
        });


        BtcAquecimentoStartPause.setOnClickListener(new View.OnClickListener() { // Instrui a operação do botão de iniciar ou pausar
            @Override
            public void onClick(View view) {
                if (mTimerRunning){ // Define um método para condição se o temporizador está funcionando
                    pauseTimer(); // Chama o método de pausa do timer
                }else {
                    startTime(); // Chama o método de início do timer
                }
            }
        });

        BtcAquecimentoReset.setOnClickListener(new View.OnClickListener() { // Instrui a operação do botão Reset
            @Override
            public void onClick(View view) {
                pauseTimer(); // Aciona o método de pausa
                resetTimer(); // Sequencialmente aciona o método reset
            }
        });
    } // Final onCreate


    private void setTime(long milliseconds) {  // Cria o método para set, definir o tempo
        mIniciarTempoMillis = milliseconds;  // Converte a variável para ms
        resetTimer(); // Chama o método resetimer para restaurar a variável e o timer
        closeKeyboard(); // Chama o método para fechar o teclado do usuário ao pressionar o botão set
    }


    private void startTime() {
        mTempoFinal = System.currentTimeMillis() + mTimeLeftMillis; //Inicia o temporizador

        mCountDownTimer = new CountDownTimer(mTimeLeftMillis,1000) { // Retorna a variável sob um intervalo decrescente de 1000 ms
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftMillis = millisUntilFinished; // Atribui  esse valor decrescente à variável
                updateCountDownText(); // Atualiza o relógio
            }

            @Override
            public void onFinish() { // Inicia o método onfinish que conclui o timer
                mTimerRunning = false; //Pausa o processo
                updateWatchInterface(); // Chama o méetodo de atualização dos botões Iniciar e Pausar
            }
        }.start();

        mTimerRunning = true;
        updateWatchInterface();
    }

    private void pauseTimer() { // método para pausar o timer
        mCountDownTimer.cancel(); // Anula o método pela classe .cancel()
        mTimerRunning = false; // Declara a variável da contagem de tempo com valor baixo
        updateWatchInterface(); // Chama o método que atualiza os botões
    }

    private void resetTimer() { // Método para resetar o timer
        mTimeLeftMillis = mIniciarTempoMillis; //Basicamente reatribui a variável à condição inicial dada
        updateCountDownText(); // Atualiza na tela pelo método

    }

    private void updateCountDownText() {
        int horas       = (int) (mTimeLeftMillis / 1000) / 3600; // A Variável de entrada é convertida para horas
        int minutos     = (int) ((mTimeLeftMillis / 1000) % 3600) / 60; // Calcula os minutos restantes, considerando o tempo que já foi usado para as horas
        int segundos    = (int) (mTimeLeftMillis / 1000) % 60; // Calcula os segundos restantes, considerando o tempo que já foi usado para as horas e minutos

        String timeLeftFormatted; // Formata esses valores para uma string
        if (horas > 0) { // Condicional para mostrar o tempo baseado no input.
            timeLeftFormatted = String.format(Locale.getDefault(), "%d:%02d:%02d",horas,minutos, segundos); // Se o tempo digitado é maior que uma hora
        } else{
            timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutos, segundos); // Caso contrário aparecerá o timer sem o string de horas
        }

        aquecimentoCronoTXT.setText(timeLeftFormatted); // Aparece na do cronômetro seu início.
    }

    private void updateWatchInterface() { // Condicional para verificar o estado do cronômetro
        if (mTimerRunning){
            BtcAquecimentoStartPause.setText("Pausar"); // Se estiver rodando, aparece o botão de pausar
        }else {
            BtcAquecimentoStartPause.setText("Iniciar"); // Ou então aparece o botão de iniciar
        }
    }

    private void closeKeyboard() { // Método usado apenas para fechar o teclado
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


}// FINAL
