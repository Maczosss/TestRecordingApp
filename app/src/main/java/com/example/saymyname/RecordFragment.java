package com.example.saymyname;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class RecordFragment extends Fragment implements View.OnClickListener {

    private NavController navController;

    private ImageButton listButton;
    private ImageButton recordButton;

    private boolean isRecording = false;

    public  RecordFragment(){}

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

        listButton.setOnClickListener(this);
        recordButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.record_list_button:
                navController.navigate(R.id.action_recordFragment_to_audioListFragment);
                break;
            case R.id.record_button:
                if(isRecording){
                    //Stop recording
                    recordButton.setImageDrawable(getResources().getDrawable(R.drawable.microphone_off, null));
                    isRecording = false;
                }
                else {
                    //Start Recording
                    recordButton.setImageDrawable(getResources().getDrawable(R.drawable.microphone_on, null));
                    isRecording = true;
                }
        }
    }
}