package com.example.login;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DuaChoActivity extends AppCompatActivity {

    ImageView dog1, dog2, dog3, dog4, dog5;
    Button btnStartRace;
    TextView textView4, boDem;
    Random random;
    MediaPlayer nhacCho, nhacBatDau, beepSound, nhacChienThang;
    boolean raceFinished = false;
    Handler handler = new Handler();
    int countdown = 3;
    @Override
    protected void onPostResume() {
        nhacChienThang = MediaPlayer.create(this, R.raw.nhacchienthang);
        super.onPostResume();
        if (!nhacChienThang.isPlaying()){
            nhacCho.start();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duacho);

        // Ánh xạ các view
        dog1 = findViewById(R.id.dog1);
        dog2 = findViewById(R.id.dog2);
        dog3 = findViewById(R.id.dog3);
        dog4 = findViewById(R.id.dog4);
        dog5 = findViewById(R.id.dog5);
        btnStartRace = findViewById(R.id.btnStartRace);
        textView4 = findViewById(R.id.textView4);
        boDem = findViewById(R.id.boDem);
        random = new Random();

        // Thiết lập hình ảnh cho các con chó
        Glide.with(this).load(R.drawable.alaska).into(dog1);
        Glide.with(this).load(R.drawable.corgi).into(dog2);
        Glide.with(this).load(R.drawable.dalmatian).into(dog3);
        Glide.with(this).load(R.drawable.husky).into(dog4);
        Glide.with(this).load(R.drawable.chihuahua).into(dog5);

        // Âm thanh
        nhacCho = MediaPlayer.create(this, R.raw.nhaccho);
        nhacBatDau = MediaPlayer.create(this, R.raw.nhaccho2);
        beepSound = MediaPlayer.create(this, R.raw.beep);
        nhacChienThang = MediaPlayer.create(this, R.raw.nhacchienthang);// Add beep sound
        nhacCho.start();

        btnStartRace.setOnClickListener(v -> startCountdown());
    }

    private void startCountdown() {
        nhacCho.pause();
        countdown = 3; // Start countdown from 3
        handler.postDelayed(updateCountdownRunnable, 1000);
    }

    private final Runnable updateCountdownRunnable = new Runnable() {
        @Override
        public void run() {
            if (countdown > 0) {
                boDem.setText(String.valueOf(countdown));
                beepSound.start(); // Play beep sound
                countdown--;
                handler.postDelayed(this, 1000);
            } else {
                boDem.setText("BẮT ĐẦU!");
                nhacBatDau.start(); // Play start sound
                startRace(); // Start the race after countdown
            }
        }
    };

    private void startRace() {
        // Delay to clear countdown text
        handler.postDelayed(() -> boDem.setText(""), 1000);

        raceFinished = false;

        // Khởi tạo khoảng cách đích
        int raceDistance = 510;

        // Tạo các ObjectAnimator để di chuyển từng chú chó
        ObjectAnimator dog1Animator = ObjectAnimator.ofFloat(dog1, "translationX", raceDistance);
        ObjectAnimator dog2Animator = ObjectAnimator.ofFloat(dog2, "translationX", raceDistance);
        ObjectAnimator dog3Animator = ObjectAnimator.ofFloat(dog3, "translationX", raceDistance);
        ObjectAnimator dog4Animator = ObjectAnimator.ofFloat(dog4, "translationX", raceDistance);


        // Thời gian chạy ngẫu nhiên cho từng chú chó
        long dog1Time = random.nextInt(5000) + 3000;
        long dog2Time = random.nextInt(5000) + 3000;
        long dog3Time = random.nextInt(5000) + 3000;
        long dog4Time = random.nextInt(5000) + 3000;


        // Set thời gian cho các ObjectAnimator
        dog1Animator.setDuration(dog1Time);
        dog2Animator.setDuration(dog2Time);
        dog3Animator.setDuration(dog3Time);
        dog4Animator.setDuration(dog4Time);


        // Start animations
        dog1Animator.start();
        dog2Animator.start();
        dog3Animator.start();
        dog4Animator.start();


        // Đợi tất cả chó về đích và hiển thị thứ hạng
        handler.postDelayed(() -> displayRaceResults(dog1Time, dog2Time, dog3Time, dog4Time), Math.max(dog1Time, Math.max(dog2Time, Math.max(dog3Time, dog4Time))));
    }

    private void displayRaceResults(long dog1Time, long dog2Time, long dog3Time, long dog4Time) {
        // Create a map of dog names and their finish times
        Map<String, Long> dogTimes = new HashMap<>();
        dogTimes.put("Chó 1", dog1Time);
        dogTimes.put("Chó 2", dog2Time);
        dogTimes.put("Chó 3", dog3Time);
        dogTimes.put("Chó 4", dog4Time);

        // Create a mapping of dog names to drawable resource names
        Map<String, String> dogDrawableMap = new HashMap<>();
        dogDrawableMap.put("Chó 1", "alaska");
        dogDrawableMap.put("Chó 2", "corgi");
        dogDrawableMap.put("Chó 3", "dalmatian");
        dogDrawableMap.put("Chó 4", "husky");


        // Sort the dogs by their finish times
        List<Map.Entry<String, Long>> sortedDogs = new ArrayList<>(dogTimes.entrySet());
        Collections.sort(sortedDogs, Map.Entry.comparingByValue());

        // Display the ranking
        StringBuilder ranking = new StringBuilder("Kết quả: \n");
        int position = 1;
        for (Map.Entry<String, Long> entry : sortedDogs) {
            ranking.append("Hạng ").append(position).append(": ").append(entry.getKey()).append("\n");
            position++;
        }

        // Show the ranking as a Toast message
        Toast.makeText(this, ranking.toString(), Toast.LENGTH_LONG).show();

        // Get the top 3 dogs
        List<DogResult> top3Results = new ArrayList<>();
        for (int i = 0; i < 3 && i < sortedDogs.size(); i++) {
            String dogName = sortedDogs.get(i).getKey();
            String drawableName = dogDrawableMap.get(dogName);
            int imageResId = getResources().getIdentifier(drawableName, "drawable", getPackageName());
            top3Results.add(new DogResult(dogName, imageResId));
        }

        nhacBatDau.stop();
        nhacChienThang.start();

        Intent intent = new Intent(DuaChoActivity.this, KetquaThangActivity.class);
        intent.putExtra("top3Results", (ArrayList<DogResult>) top3Results);

        startActivity(intent);
    }
}

