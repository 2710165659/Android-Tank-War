package com.main.tankwar.gameobj;

import com.main.tankwar.Animations.AnimationManager;
import com.main.tankwar.GameController;
import com.main.tankwar.GameSoundManager;
import com.main.tankwar.GameView;
import com.main.tankwar.enums.Direction;
import com.main.tankwar.enums.EnemyType;


import java.util.Random;

public class EnemyTank extends Tank {
    private final Random random;
    private double p1,p2,p3;
    public int timerAI,timerAttack;
    public EnemyType type;

    public EnemyTank(int x, int y,EnemyType type) {
        super(Direction.DOWN, 0, x, y, 1);
        random = new Random();
        // 生成相关
        this.visible = false;
        this.type = type;
        this.size = (int)(GameView.cellSize * 1.5);
        loadEnemyData();
    }

    public void AI() {
        if (visible) {
            // 敌人发射子弹
            if (timerAttack > 50 && random.nextDouble() < p1) {
                timerAttack = 0;
                shootBullet();
            }
            // 敌人移动，每秒触发60次
            if (move()) {
                // 移动成功转向概率
                if(timerAI > 30){
                    timerAI = 0;
                    changeDir(p2);
                }
            } else {
                // 移动失败转向概率
                if(timerAI > 10){
                    timerAI = 0;
                    changeDir(p3);
                }
            }
        }
    }

    private void changeDir(double probability) {
        if (random.nextDouble() < probability) {
            int t = random.nextInt(100);
            if (t <= 25) dir = Direction.UP;
            else if (t <= 50) dir = Direction.DOWN;
            else if (t <= 75) dir = Direction.LEFT;
            else dir = Direction.RIGHT;
        }
    }

    private void loadEnemyData() {
        switch (type){
            case ENEMY1:
                speed = GameController.enemy1Speed;
                remainingLives = GameController.enemy1RemainLives;
                p1 = GameController.enemy1P1;
                p2 = GameController.enemy1P2;
                p3 = GameController.enemy1P3;
                this.size -= 4;
                break;
            case ENEMY2:
                speed = GameController.enemy2Speed;
                remainingLives = GameController.enemy2RemainLives;
                p1 = GameController.enemy2P1;
                p2 = GameController.enemy2P2;
                p3 = GameController.enemy2P3;
                this.size += 4;
                break;
            case ENEMY3:
                speed = GameController.enemy3Speed;
                remainingLives = GameController.enemy3RemainLives;
                p1 = GameController.enemy3P1;
                p2 = GameController.enemy3P2;
                p3 = GameController.enemy3P3;
                break;
        }
    }

    @Override
    public void hurt(){
        super.hurt();
        if(remainingLives < 0){
            gc.leftEnemyCount -= 1;
            gc.currentEnemyCount -= 1;
            GameSoundManager.playEnemyDeath();
        }
    }

    @Override
    public final void bulletMove(){
        if(bullet.visible){
            bullet.move();
            if(bullet.hitMap()){
                bullet.visible = false;
                AnimationManager.addBulletDestroy(bullet.x, bullet.y);
                return;
            }
            if(bullet.hitHome()){
                gc.playerTank.remainingLives = -1;
                bullet.visible = false;
                AnimationManager.addBulletDestroy(bullet.x, bullet.y);
                return;
            }
            PlayerTank e = (PlayerTank) bullet.hitPlayer();
            if(e!=null){
                e.hurt();
                bullet.visible = false;
                AnimationManager.addBulletDestroy(bullet.x, bullet.y);
            }
        }
    }
}
