package com.example.smartbianco;

import android.annotation.SuppressLint;
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

public class Wod extends AppCompatActivity {

    private EditText wodInputTXT;
    private TextView wodCronoTXT;
    private Button BtcWodSet;
    private Button  BtcWodStartPause;
    private Button  BtcWodReset;

    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;

    private long    mIniciarTempoMillis;
    private long    mTimeLeftMillis;
    private long    mTempoFinal;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wod);

        wodInputTXT = findViewById(R.id.wodInputXML);
        wodCronoTXT = findViewById(R.id.wodCronoXML);

        BtcWodSet = findViewById(R.id.wodSetXML);
        BtcWodStartPause = findViewById(R.id.wodIniciarXML);
        BtcWodReset = findViewById(R.id.wodResetXML);

        BtcWodSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = wodInputTXT.getText().toString();
                if (input.length() == 0 ){
                    Toast.makeText(Wod.this,"Não pode estar Vazio", Toast.LENGTH_SHORT).show();
                    return;
                }

                long millisInput = Long.parseLong(input) * 60000;
                if (millisInput == 0){
                    Toast.makeText(Wod.this,"Entre com Número Positivo", Toast.LENGTH_SHORT).show();
                    return;
                }

                setTime(millisInput);
                wodInputTXT.setText("");
            }
        });


        BtcWodStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTimerRunning){
                    pauseTimer();
                }else {
                    startTime();
                }
            }
        });

        BtcWodReset.setOnClickListener(new View.OnClickListener() {
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

        wodCronoTXT.setText(timeLeftFormatted);
    }

    private void updateWatchInterface() {
        if (mTimerRunning){
            BtcWodStartPause.setText("Pausar");
        }else {
            BtcWodStartPause.setText("Iniciar");
        }
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
