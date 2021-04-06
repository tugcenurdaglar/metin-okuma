package com.tugcenurdaglar.sesli;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;

public class AndroidTextToSpeechActivity extends AppCompatActivity  implements
        TextToSpeech.OnInitListener  {

    private TextToSpeech tts;
    private Button btnSpeak;
    private EditText txtText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_text_to_speech);

        tts = new TextToSpeech(this, this);

        btnSpeak = findViewById(R.id.btnSpeak);

        txtText = findViewById(R.id.txtText);

        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakOut();
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","Dil desteklenmemektedir");
            }

            else {
                btnSpeak.setEnabled(true);
                speakOut();
            }

        } else {
            Log.e("TTS","Yüklenemedi");
        }

    }

    private void speakOut() {

        String text = txtText.getText().toString();//edittextten girilen metni aldık

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);//speak fonksiyonuna girdi olarak verdik.
    }
}