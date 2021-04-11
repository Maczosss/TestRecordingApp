package com.example.saymyname;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.io.File;
import java.io.IOException;


public class AudioListFragment extends Fragment implements AudioListAdapter.onItemListClick {

    private ConstraintLayout playerSheet;
    private BottomSheetBehavior bottomSheetBehavior;
    private RecyclerView audioList;
    private File[] allFiles;

    private AudioListAdapter audioListAdapter;

    //audio play
    private MediaPlayer mediaPlayer = null;
    private boolean isPlaying = false;
    private File fileToPlay;

    //UI elements buttons
    private ImageButton playButton;
    private TextView playerHeader;
    private TextView playerFileName;


    public AudioListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_audio_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        playerSheet = view.findViewById(R.id.player_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(playerSheet);
        audioList = view.findViewById(R.id.audio_list_view);

        playButton = view.findViewById(R.id.player_play_button);
        playerHeader = view.findViewById(R.id.player_header_title);
        playerFileName = view.findViewById(R.id.player_fileName);

        String recordPath = getActivity().getExternalFilesDir("/").getAbsolutePath();
        File directory = new File(recordPath);
        allFiles = directory.listFiles();

        audioListAdapter = new AudioListAdapter(allFiles, this);

        audioList.setHasFixedSize(true);
        audioList.setLayoutManager(new LinearLayoutManager(getContext()));
        audioList.setAdapter(audioListAdapter);

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                //nothing here 404
            }
        });
    }

    @Override
    public void onClickListener(File file, int position) {
        //Log.d("PLAY LOG", "File playing: " + file.getName());
        if (isPlaying) {
            stopAudio();
            playAudio(fileToPlay);
        } else {
            fileToPlay = file;
            playAudio(fileToPlay);
        }
    }

    private void stopAudio() {
        playButton.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.play, null));
        playerHeader.setText("Stopped");
        isPlaying = false;
    }

    private void playAudio(File fileToPlay) {
        mediaPlayer = new MediaPlayer();

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        try {
            mediaPlayer.setDataSource(fileToPlay.getAbsolutePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        playButton.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.pause, null));
        playerFileName.setText(fileToPlay.getName());
        playerHeader.setText("Playing");
        //Play audio file
        isPlaying = true;

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopAudio();
                playerHeader.setText("Finished;");
            }
        });
    }
}