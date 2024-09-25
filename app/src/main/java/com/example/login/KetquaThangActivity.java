package com.example.login;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

public class KetquaThangActivity extends AppCompatActivity {
    MediaPlayer nhacChienThang;
    MediaPlayer nhacCho;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);
        Button restartButton = findViewById(R.id.buttonBack);
        ImageView hinhCho1 = findViewById(R.id.image1st);
        ImageView hinhCho2 = findViewById(R.id.image2nd);
        ImageView hinhCho3 = findViewById(R.id.image3rd);
        ImageView winBanner = findViewById(R.id.imageWinBanner);
        Glide.with(this).asGif().load(R.drawable.win_banner).override(500, 700).into(winBanner);
        TextView firstPlace = findViewById(R.id.first_place);
        TextView secondPlace = findViewById(R.id.second_place);
        TextView thirdPlace = findViewById(R.id.third_place);

        nhacChienThang = MediaPlayer.create(this, R.raw.nhacchienthang);
        nhacCho = MediaPlayer.create(this, R.raw.nhaccho);


        restartButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                Intent intent = new Intent(KetquaThangActivity.this, DuaChoActivity.class);
                startActivity(intent);
                finish();
                nhacCho.seekTo(0); // Reset nhacCho to the beginning
            }
        });

        ArrayList<DogResult> top3Results = (ArrayList<DogResult>) getIntent().getSerializableExtra("top3Results");

        if (top3Results != null) {
            if (top3Results.size() > 0) {
                firstPlace.setText(top3Results.get(0).name);
                Glide.with(this)
                        .asGif()
                        .load(top3Results.get(0).imageResId)
                        .listener(new RequestListener<GifDrawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        })
                        .into(hinhCho1);
            }
            if (top3Results.size() > 1) {
                secondPlace.setText(top3Results.get(1).name);
                Glide.with(this)
                        .asGif()
                        .load(top3Results.get(1).imageResId)
                        .listener(new RequestListener<GifDrawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                                // Handle image loading failure, e.g., log the error
                                return false; // Let Glide handle the error
                            }

                            @Override
                            public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false; // Let Glide handle the success
                            }
                        })
                        .into(hinhCho2);
            }
            if (top3Results.size() > 2) {
                thirdPlace.setText(top3Results.get(2).name);
                Glide.with(this)
                        .asGif()
                        .load(top3Results.get(2).imageResId)
                        .listener(new RequestListener<GifDrawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                                // Handle image loading failure, e.g., log the error
                                return false; // Let Glide handle the error
                            }

                            @Override
                            public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false; // Let Glide handle the success
                            }
                        })
                        .into(hinhCho3);
            }
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        nhacChienThang.stop();
        nhacChienThang.release();
    }

}