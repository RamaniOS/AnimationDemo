package com.example.animationdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements Animation.AnimationListener, SeekBar.OnSeekBarChangeListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int DEFAULT_ANIM_SPEED = 1500;
    private ImageView imageIcon;
    private Button btnColor, btnStart, btnDirection;
    private boolean status = false;
    private boolean isLeftRight = false;
    private long tempDuration;

    // Animations
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        imageIcon = findViewById(R.id.icon);
        btnColor = findViewById(R.id.btnColor);
        btnStart = findViewById(R.id.btnStartStop);
        btnDirection = findViewById(R.id.direction);
        // start animation
        animation = topBottom();
        startAnimation();
        // seek bar
        SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(this);

        // button click event
        btnColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               changeColor();
            }
        });
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStopAnim();
            }
        });
        btnDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDirection();
            }
        });
    }

    private void changeDirection() {
        isLeftRight = !isLeftRight;
        tempDuration = animation.getDuration();
        imageIcon.clearAnimation();
        animation.cancel();
        animation = null;
        if (isLeftRight) {
            animation = leftRight();
            btnDirection.setText("TopBottom");
        } else {
            animation = topBottom();
            btnDirection.setText("LeftRight");
        }
        startAnimation();
        animation.setDuration(tempDuration);
    }

    private void startStopAnim() {
        status = !status;
        if (status) {
            btnStart.setText("Start");
            animation.cancel();
        } else {
            startAnimation();
            btnStart.setText("Stop");
        }
    }

    private void changeColor() {
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        imageIcon.setBackgroundColor(color);
    }

    private void startAnimation() {
        animation.setDuration(DEFAULT_ANIM_SPEED);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(2);
        animation.setFillAfter(true);
        animation.setAnimationListener(this);
        imageIcon.startAnimation(animation);
    }

    private Animation topBottom() {
        return new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT,
                0.0f, Animation.RELATIVE_TO_PARENT, 0.96f);
    }

    private Animation leftRight() {
        return new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -0.47f,
                Animation.RELATIVE_TO_PARENT, 0.47f, Animation.RELATIVE_TO_PARENT,
                0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
    }

    @Override
    public void onAnimationStart(Animation animation) {}

    @Override
    public void onAnimationEnd(Animation animation) {}

    @Override
    public void onAnimationRepeat(Animation animation) {}

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int progress = seekBar.getProgress();
        if (progress == 0) {
            animation.setDuration(DEFAULT_ANIM_SPEED);
            return;
        }
        animation.setDuration(DEFAULT_ANIM_SPEED/progress);
    }
}
