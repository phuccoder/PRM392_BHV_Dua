package com.example.login;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

public class DuaChoActivity extends AppCompatActivity {

    ImageView dog1, dog2, dog3, dog4, dog5;
    Button btnStartRace;
    TextView textView4;
    Random random;
    MediaPlayer nhacCho;
    MediaPlayer nhacBatDau;

    boolean raceFinished = false;

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
        textView4 = findViewById(R.id.textView4);  // Di chuyển dòng này lên đây
        random = new Random();

        // Thiết lập số tiền
        int soTien = 50000;
        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        String formattedMoney = numberFormat.format(soTien) + " đ";
        textView4.setText(formattedMoney);

        // Khởi tạo âm thanh
        nhacCho = MediaPlayer.create(this, R.raw.nhaccho);
        nhacBatDau = MediaPlayer.create(this, R.raw.nhaccho2);

        nhacCho.start();

        // Thiết lập hình ảnh cho các con chó
        Glide.with(this).load(R.drawable.alaska).into(dog1);
        Glide.with(this).load(R.drawable.chihuahua).into(dog2);
        Glide.with(this).load(R.drawable.corgi).into(dog3);
        Glide.with(this).load(R.drawable.dalmatian).into(dog4);
        Glide.with(this).load(R.drawable.husky).into(dog5);

        btnStartRace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRace();
            }
        });
    }

    private void startRace() {
        nhacCho.pause();
        nhacBatDau.seekTo(0);  // Đặt lại vị trí phát về đầu
        raceFinished = false;
        nhacBatDau.start();

        int screenWidth = getResources().getDisplayMetrics().widthPixels - dog1.getWidth();

        // Tạo các animator cho từng con chó
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(dog1, "translationX", screenWidth);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(dog2, "translationX", screenWidth);
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(dog3, "translationX", screenWidth);
        ObjectAnimator anim4 = ObjectAnimator.ofFloat(dog4, "translationX", screenWidth);
        ObjectAnimator anim5 = ObjectAnimator.ofFloat(dog5, "translationX", screenWidth);

        anim1.setDuration(5000 + random.nextInt(3000));
        anim2.setDuration(5000 + random.nextInt(3000));
        anim3.setDuration(5000 + random.nextInt(3000));
        anim4.setDuration(5000 + random.nextInt(3000));
        anim5.setDuration(5000 + random.nextInt(3000));

        // kiểm tra xem con nào về đích
        addRaceFinishListener(anim1, "Chó Alaska");
        addRaceFinishListener(anim2, "Chó Chihuahua");
        addRaceFinishListener(anim3, "Chó Corgi");
        addRaceFinishListener(anim4, "Chó Dalmatian");
        addRaceFinishListener(anim5, "Chó Husky");

        // Bắt đầu chạy animator
        anim1.start();
        anim2.start();
        anim3.start();
        anim4.start();
        anim5.start();
    }

    private void addRaceFinishListener(ObjectAnimator animator, final String dogName) {
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (!raceFinished) { // Nếu chưa có ai về đích
                    raceFinished = true; // Đánh dấu cuộc đua đã kết thúc
                    nhacBatDau.pause();
                    stopAllDogs();

                    Toast.makeText(DuaChoActivity.this, dogName + " đã về đích!", Toast.LENGTH_SHORT).show();

                    // 3 giây trước khi reset
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            resetDogs();
                        }
                    }, 3000);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
    }

    // dừng tất cả dog
    private void stopAllDogs() {
        dog1.clearAnimation();
        dog2.clearAnimation();
        dog3.clearAnimation();
        dog4.clearAnimation();
        dog5.clearAnimation();
    }

    // reset tất cả dog
    private void resetDogs() {
        nhacCho.seekTo(0);
        dog1.animate().translationX(0).setDuration(1000);
        dog2.animate().translationX(0).setDuration(1000);
        dog3.animate().translationX(0).setDuration(1000);
        dog4.animate().translationX(0).setDuration(1000);
        dog5.animate().translationX(0).setDuration(1000);
        nhacCho.start();
    }
}
