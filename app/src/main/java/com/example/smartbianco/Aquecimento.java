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


    private EditText aquecimentoInputTXT;
    private TextView aquecimentoCronoTXT;
    private Button  BtcAquecimentoSet;
    private Button  BtcAquecimentoStartPause;
    private Button  BtcAquecimentoReset;

    private CountDownTimer  mCountDownTimer;
    private boolean mTimerRunning;

    private long    mIniciarTempoMillis;
    private long    mTimeLeftMillis;
    private long    mTempoFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aquecimento);

        aquecimentoInputTXT = findViewById(R.id.aquecimentoInputXML);
        aquecimentoCronoTXT = findViewById(R.id.aquecimentoCronoXML);

        BtcAquecimentoSet = findViewById(R.id.aquecimentoSetXML);
        BtcAquecimentoStartPause = findViewById(R.id.aquecimentoIniciarXML);
        BtcAquecimentoReset = findViewById(R.id.aquecimentoResetXML);

        BtcAquecimentoSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = aquecimentoInputTXT.getText().toString();
                if (input.length() == 0 ){
                    Toast.makeText(Aquecimento.this,"Não pode estar Vazio", Toast.LENGTH_SHORT).show();
                    return;
                }

                long millisInput = Long.parseLong(input) * 60000;
                if (millisInput == 0){
                    Toast.makeText(Aquecimento.this,"Entre com Número Positivo", Toast.LENGTH_SHORT).show();
                    return;
                }

                setTime(millisInput);
                aquecimentoInputTXT.setText("");
            }
        });


        BtcAquecimentoStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTimerRunning){
                    pauseTimer();
                }else {
                    startTime();
                }
            }
        });

        BtcAquecimentoReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseTimer();
                resetTimer();
            }
        });
    } // Final onCreate


    private void setTime(long milliseconds) {
        mIniciarTempoMillis = milliseconds;
        resetTimer();
        closeKeyboard();
    }


    private void startTime() {
        mTempoFinal = System.currentTimeMillis() + mTimeLeftMillis;

        mCountDownTimer = new CountDownTimer(mTimeLeftMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                updateWatchInterface();
            }
        }.start();

        mTimerRunning = true;
        updateWatchInterface();
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        updateWatchInterface();
    }

    private void resetTimer() {
        mTimeLeftMillis = mIniciarTempoMillis;
        updateCountDownText();

    }

    private void updateCountDownText() {
        int horas       = (int) (mTimeLeftMillis / 1000) / 3600;
        int minutos     = (int) ((mTimeLeftMillis / 1000) % 3600) / 60;
        int segundos    = (int) (mTimeLeftMillis / 1000) % 60;

        String timeLeftFormatted;
        if (horas > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(), "%d:%02d:%02d",horas,minutos, segundos);
        } else{
            timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutos, segundos);
        }

        aquecimentoCronoTXT.setText(timeLeftFormatted);
    }

    private void updateWatchInterface() {
        if (mTimerRunning){
            BtcAquecimentoStartPause.setText("Pausar");
        }else {
            BtcAquecimentoStartPause.setText("Iniciar");
        }
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


}// FINAL
