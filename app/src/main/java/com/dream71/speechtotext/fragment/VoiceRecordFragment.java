package com.dream71.speechtotext.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dream71.speechtotext.MainActivity;
import com.dream71.speechtotext.R;


public class VoiceRecordFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_voice_record, container, false);
        //MainActivity.titleBar.setText("Dashboard");

        return view;
    }



    @Override
    public void onResume() {
        super.onResume();
        MainActivity.titleBar.setText("Voice Record");
        MainActivity.shouldShowBackButton(false);
       // GetDataFromOffline();
    }
    }
