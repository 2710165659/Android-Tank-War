package com.main.tankwar.gameobj;

import android.os.VibrationEffect;

import com.main.tankwar.Animations.AnimationManager;
import com.main.tankwar.GameActivity;
import com.main.tankwar.GameController;
import com.main.tankwar.GameSoundManager;
import com.main.tankwar.GameView;
import com.main.tankwar.enums.Direction;


public class PlayerTank extends Tank {
    public int timer;

    public PlayerTank() {
        super(Direction.UP, GameController.playerSpeed, 0, 0, GameView.playerLiveCount - 1);
        this.size = (int) (GameView.cellSize * 1.5);
        this.visible = true;
        spawnPoint();
    }

    private void spawnPoint() {
        x = 9 * GameView.cellSize;
        y = 25 * GameView.cellSize;
        dir = Direction.UP;
    }

    public void move(Direction dir) {
        this.dir = dir;
        if (super.move()) {
            // 角色成功移动，触发震动
            if (GameActivity.vibrator != null && GameActivity.vibrator.hasVibrator()) {
                // 创建一个持续 50 毫秒，振幅为 32 的震动效果
                VibrationEffect vibrationEffect = VibrationEffect.createOneShot(50, 32);
                GameActivity.vibrator.vibrate(vibrationEffect);
            }
        }
    }

    public void attack() {
        if (timer > 10) {
            timer = 0;
            if (!bullet.visible) {
                shootBullet();
                GameSoundManager.playPlayerShoot();
                // 震动
                GameActivity.vibrator.vibrate(VibrationEffect.createOneShot(50, 128));
            }
        }
    }

    @Override
    public void hurt() {
        spawnPoint();
        GameSoundManager.playPlayerDeath();
        GameView.playerLiveCount -= 1;
        super.hurt();
    }

    @Override
    public final void bulletMove() {
        if (bullet.visible) {
            bullet.move();
            if (bullet.hitMap()) {
                bullet.visible = false;
                GameSoundManager.playBulletDestroy();
                AnimationManager.addBulletDestroy(bullet.x, bullet.y);
                return;
            }
            if (bullet.hitHome()) {
                this.remainingLives = -1;
                bullet.visible = false;
                GameSoundManager.playBulletDestroy();
                AnimationManager.addBulletDestroy(bullet.x, bullet.y);
                return;
            }
            EnemyTank e = (EnemyTank) bullet.hitEnemies();
            if (e != null) {
                e.hurt();
                bullet.visible = false;
                GameSoundManager.playBulletDestroy();
                AnimationManager.addBulletDestroy(bullet.x, bullet.y);
                return;
            }
            e = (EnemyTank) bullet.hitEnemiesBullet();
            if (e != null) {
                e.bullet.visible = false;
                bullet.visible = false;
                GameSoundManager.playBulletDestroy();
                AnimationManager.addBulletDestroy(bullet.x, bullet.y);
            }
        }
    }
}
