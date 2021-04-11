package com.example.saymyname;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RecordFragment extends Fragment implements View.OnClickListener {

    private int PERMISSION_CODE = 21;
    private NavController navController;

    private ImageButton listButton;
    private ImageButton recordButton;
    private String recordPermission = Manifest.permission.RECORD_AUDIO;
    private MediaRecorder mediaRecorder;
    private TextView fileNameText;

    //storing files
    String recordFile;

    //Record timer
    private Chronometer timer;

    private boolean isRecording = false;

    public RecordFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_record, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        listButton = view.findViewById(R.id.record_list_button);
        recordButton = view.findViewById(R.id.record_button);
        timer = view.findViewById(R.id.record_timer);
        fileNameText = view.findViewById(R.id.record_filename);

        listButton.setOnClickListener(this);
        recordButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.record_list_button:
                if (isRecording) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                    alertDialog.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            navController.navigate(R.id.action_recordFragment_to_audioListFragment);
                            isRecording = false;
                        }
                    });
                    alertDialog.setNegativeButton("Cancel", null);
                    alertDialog.setTitle("Audio still recording");
                    alertDialog.setMessage("Are You sure You want to stop recording?");
                    alertDialog.create().show();
                } else {
                    navController.navigate(R.id.action_recordFragment_to_audioListFragment);
                }
                break;
            case R.id.record_button:
                if (isRecording) {
                    //Stop recording
                    stopRecording();
                    recordButton.setImageDrawable(getResources().getDrawable(R.drawable.microphone_on, null));
                    isRecording = false;
                } else {
                    //Start Recording
                    if (checkPermissions()) {
                        startRecording();
                        recordButton.setImageDrawable(getResources().getDrawable(R.drawable.microphone_off, null));
                        isRecording = true;
                    }
                }
                break;
        }
    }

    private void stopRecording() {
        timer.stop();
        timer.setBase(SystemClock.elapsedRealtime());
        fileNameText.setText("Recording stopped, File saved: " + recordFile);

        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
    }

    private void startRecording() {
        timer.setBase(SystemClock.elapsedRealtime());
        timer.start();
        String recordPath = getActivity().getExternalFilesDir("/").getAbsolutePath();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss", Locale.ENGLISH);
        Date now = new Date();
        //name of File
        recordFile = "Recording_" + formatter.format(now) + ".3gp";

        fileNameText.setText("Recording, File name: " + recordFile);

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(recordPath + "/" + recordFile);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaRecorder.start();
    }

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(getContext(), recordPermission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{recordPermission}, PERMISSION_CODE);
        }
        return false;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (isRecording) {
            stopRecording();
        }
    }
}