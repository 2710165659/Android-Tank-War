package com.main.tankwar;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;

public class GameSoundManager {

    private static SoundPool soundPool;

    // 初始化方法，需要传入Context
    public static void init(Context ctx) {
        Context context = ctx.getApplicationContext();

        // 初始化SoundPool
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setMaxStreams(6) // 最多同时播放5个音效
                .setAudioAttributes(audioAttributes)
                .build();
        // 加载音效到SoundPool
        soundPool.load(context, R.raw.game_start, 1);
        soundPool.load(context, R.raw.game_over, 2);
        soundPool.load(context, R.raw.player_shoot, 3);
        soundPool.load(context, R.raw.player_death, 4);
        soundPool.load(context, R.raw.enemy_death, 5);
        soundPool.load(context, R.raw.bullet_destroy, 6);


    }
    // 播放游戏开始声音的方法
    public static void playGameStart() {
        soundPool.play(1, 0.13f, 0.13f, 0, 0, 1.0f);
    }

    // 播放游戏结束声音的方法
    public static void playGameOver() {
        soundPool.play(2, 0.5f, 0.5f, 0, 0, 1.0f);
    }

    // 播放玩家射击声音的方法
    public static void playPlayerShoot() {
        soundPool.play(3, 0.1f, 0.1f, 0, 0, 1.0f);
    }

    // 播放玩家死亡声音的方法
    public static void playPlayerDeath() {
        soundPool.play(4, 1.0f, 1.0f, 0, 0, 1.0f);
    }

    // 播放敌人死亡声音的方法
    public static void playEnemyDeath() {
        soundPool.play(5, 1.0f, 1.0f, 0, 0, 0.5f);
    }

    // 播放子弹销毁声音的方法
    public static void playBulletDestroy() {
        soundPool.play(6, 0.8f, 0.8f, 0, 0, 10.0f);
    }

    // 释放所有资源
    public static void release() {
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
    }
}