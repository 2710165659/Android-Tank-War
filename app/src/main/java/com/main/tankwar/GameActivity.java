package com.main.tankwar;

import android.os.Bundle;
import android.os.Vibrator;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
    private TextView tvEnemyCount, tvPlayerLive, currentLevel;
    private CustomImageButton vkUp, vkDown, vkLeft, vkRight;
    private ImageButton vkAttack;
    public static Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        // 全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 获取控件并初始化
        tvEnemyCount = findViewById(R.id.tvEnemyCount);
        tvPlayerLive = findViewById(R.id.tvLivesCount);
        vkUp = findViewById(R.id.vk_up);
        vkDown = findViewById(R.id.vk_down);
        vkLeft = findViewById(R.id.vk_left);
        vkRight = findViewById(R.id.vk_right);
        vkAttack = findViewById(R.id.vk_attack);
        currentLevel = findViewById(R.id.currentLevel);
        // 将控件添加到GameView中
        GameView gv = findViewById(R.id.game_view);
        gv.flushText();
        // 获取 Vibrator 服务
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
    }


    // 公开的get方法
    public TextView getTvEnemyCount() {
        return tvEnemyCount;
    }

    public TextView getTvPlayerLive() {
        return tvPlayerLive;
    }

    public TextView getCurrentLevel() {
        return currentLevel;
    }

    public CustomImageButton getVkUp() {
        return vkUp;
    }

    public CustomImageButton getVkDown() {
        return vkDown;
    }

    public CustomImageButton getVkLeft() {
        return vkLeft;
    }

    public CustomImageButton getVkRight() {
        return vkRight;
    }

    public ImageButton getVkAttack() {
        return vkAttack;
    }
}