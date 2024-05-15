package com.example.yemenfood;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;


public class Player extends AppCompatActivity {
    MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        VideoView videoView = findViewById(R.id.video_view);
        ImageView close = findViewById(R.id.btnClose);
        close.setOnClickListener(v -> finish());
        Uri uri = Uri.parse(getIntent().getStringExtra("video"));
        videoView.setVideoURI(uri);

        mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        videoView.start();
        videoView.setOnCompletionListener(mp -> finish());

    }
    @Override
    protected void onPause() {
        super.onPause();
        if (mediaController != null) {
            mediaController.hide();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaController != null) {
            mediaController.hide();
            mediaController = null;
        }
    }
}