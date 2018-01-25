package com.dream71.speechtotext.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.dream71.speechtotext.MainActivity;
import com.dream71.speechtotext.R;


import java.util.ArrayList;
import java.util.Locale;


public class TestVoiceToTextFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    String LOG_TAG="TestVoiceToText";
     EditText editText;
     Button start;
    Button stop;
    ImageButton button;
     SpeechRecognizer mSpeechRecognizer;
     Intent mSpeechRecognizerIntent;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view=inflater.inflate(R.layout.fragment_test, container, false);

        MainActivity.titleBar.setText("Voice to Text Converter");

        MainActivity.shouldShowBackButton(false);
        checkStoragePermission();
        init();

        return view;
    }

    private void init()
    {
        editText = (EditText)view.findViewById(R.id.editText1);
        button = (ImageButton) view.findViewById(R.id.button);
        start = (Button) view.findViewById(R.id.start);
        stop = (Button) view.findViewById(R.id.stop);

        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(getActivity());

//
//        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
//        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,"en");
//        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getActivity().getPackageName());
       // mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
      //  mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);
       // mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
        stop.setEnabled(false);
  mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                "bn");

        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {
                Log.i(LOG_TAG, "onReadyForSpeech");
            }

            @Override
            public void onBeginningOfSpeech() {
                Log.i(LOG_TAG, "onBeginningOfSpeech");
            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {
                Log.i(LOG_TAG, "onBufferReceived: " + bytes);
            }

            @Override
            public void onEndOfSpeech() {
                Log.i(LOG_TAG, "onEndOfSpeech");
            }

            @Override
            public void onError(int i) {
                Log.d(LOG_TAG, "FAILED " + i);
            }

            @Override
            public void onResults(Bundle bundle) {
                //getting all the matches
                ArrayList<String> matches = bundle
                        .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                //displaying the first match
                if (matches != null) {
                    editText.setText(matches.get(0));
Log.e("text",""+matches.get(0));
                }
            }

            @Override
            public void onPartialResults(Bundle bundle) {
                Log.i(LOG_TAG, "onPartialResults");
            }

            @Override
            public void onEvent(int i, Bundle bundle) {
                Log.i(LOG_TAG, "onEvent");
            }
        });
        stop.setTextColor(Color.parseColor("#D4D4D4"));
start.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        stop.setEnabled(true);
        start.setEnabled(false);
        stop.setTextColor(Color.WHITE);
        start.setTextColor(Color.parseColor("#D4D4D4"));
        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
        editText.setText("");
        editText.setHint("শুনছি...");

    }
});
stop.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       // if(mSpeechRecognizer.)
        start.setEnabled(true);
        stop.setEnabled(false);

        start.setTextColor(Color.WHITE);
        stop.setTextColor(Color.parseColor("#D4D4D4"));
        mSpeechRecognizer.stopListening();
        editText.setHint("আপনি এখানে  টেক্সট  আকারে ফলাফল দেখতে পাবেন");
    }
});
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        mSpeechRecognizer.stopListening();
                        editText.setHint("আপনি এখানে  টেক্সট  আকারে ফলাফল দেখতে পাবেন");
                        break;

                    case MotionEvent.ACTION_DOWN:
                        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                        editText.setText("");
                        editText.setHint("শুনছি...");
                        break;
                }
                return false;
            }
        });
    }

    void checkStoragePermission(){
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {Manifest.permission.RECORD_AUDIO};
        if (!hasPermissions(getActivity(), PERMISSIONS)) {
            ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, PERMISSION_ALL);
        }
        try{
            init();
            // Toast.makeText(LoginActivity.this, "We got the permission.Thanks", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
            //  Toast.makeText(LoginActivity.this, "Sorry some portion of the application may not work", Toast.LENGTH_SHORT).show();
        }
    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

}
