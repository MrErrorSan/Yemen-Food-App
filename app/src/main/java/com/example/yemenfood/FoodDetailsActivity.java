package com.example.yemenfood;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.yemenfood.databinding.ActivityFoodDetailsBinding;

public class FoodDetailsActivity extends AppCompatActivity {
    ActivityFoodDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityFoodDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        intent.putExtra("name", food.getName());
//        intent.putExtra("img", food.getImage());
//        intent.putExtra("ingredients", food.getIngredients());
//        intent.putExtra("price", food.getPrice());
        String name = getIntent().getStringExtra("name");
        String img = getIntent().getStringExtra("img");
        String video = getIntent().getStringExtra("video");
        String ingredients = getIntent().getStringExtra("ingredients");
        float price = getIntent().getFloatExtra("price",0.0f);

        binding.btnBack.setOnClickListener(v -> finish());

        binding.boxName.setText(name);
        binding.boxItems.setText("Ingredients : \n"+ingredients);
        binding.boxPrice.setText(price+" RM");
        RequestOptions requestOptions = new RequestOptions().transform(new CenterCrop(), new RoundedCorners(30)).placeholder(R.drawable.logo);
        Glide.with(this).load(img).apply(requestOptions).into(binding.img);
        binding.btnMinus.setOnClickListener(v -> {
            binding.count.setText(String.valueOf((Integer.parseInt (binding.count.getText().toString().trim()))-1));
        });
        binding.btnPlus.setOnClickListener(v -> {
            binding.count.setText(String.valueOf((Integer.parseInt (binding.count.getText().toString().trim()))+1));
        });
        binding.btnPlay.setOnClickListener(v -> {
            //Implementation for video player here
//            VideoPlayerDialogBinding videoPlayerDialogBinding= VideoPlayerDialogBinding.inflate(LayoutInflater.from(this));
//            AlertDialog dialog = new AlertDialog.Builder(this).setView(videoPlayerDialogBinding.getRoot()).create();
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.noColor)));
//            dialog.setCancelable(false);
//            videoPlayerDialogBinding.btnClose.setOnClickListener(vi ->dialog.dismiss());
//            String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.fasah;
//            Uri uri = Uri.parse(videoPath);
//            videoPlayerDialogBinding.qqq.setVideoURI(uri);
//
//            MediaController mediaController = new MediaController(this);
//            videoPlayerDialogBinding.qqq.setMediaController(mediaController);
//            mediaController.setAnchorView(videoPlayerDialogBinding.qqq);
//            videoPlayerDialogBinding.qqq.start();
//            videoPlayerDialogBinding.qqq.setOnCompletionListener(mp -> {
//                videoPlayerDialogBinding.btnPause1.setVisibility(View.GONE);
//                videoPlayerDialogBinding.btnPlay1.setVisibility(View.VISIBLE);
//            });
//            videoPlayerDialogBinding.btnPlay1.setOnClickListener(vi->{
//                videoPlayerDialogBinding.qqq.start();
//                videoPlayerDialogBinding.btnPlay1.setVisibility(View.GONE);
//                videoPlayerDialogBinding.btnPause1.setVisibility(View.VISIBLE);
//            });
//            videoPlayerDialogBinding.btnPause1.setOnClickListener(vi->{
//                videoPlayerDialogBinding.qqq.pause();
//                videoPlayerDialogBinding.btnPause1.setVisibility(View.GONE);
//                videoPlayerDialogBinding.btnPlay1.setVisibility(View.VISIBLE);
//            });
//            dialog.show();
            Intent intent = new Intent(getApplicationContext(),Player.class);
            intent.putExtra("video",video);
            startActivity(intent);

        });
    }
}