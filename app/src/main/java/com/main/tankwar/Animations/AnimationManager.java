package com.main.tankwar.Animations;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.main.tankwar.GameView;

import java.util.ArrayList;

public class AnimationManager {

    private static final ArrayList<Animation> animations = new ArrayList<Animation>();
    public static ArrayList<Bitmap> bulletDestroy = new ArrayList<Bitmap>();
    public static ArrayList<Bitmap> enemyDestroy = new ArrayList<Bitmap>();
    public static ArrayList<Bitmap> enemyBorn = new ArrayList<Bitmap>();


    public static void drawAnimations(Canvas c) {
        animations.removeIf(a -> !a.draw(c));
    }


    public static void addBulletDestroy(int x, int y) {
        int size = 150;
        // x + GameView.cellSize/2 子弹正中心
        // xy有点偏差(图片问题)
        animations.add(new Animation(x + GameView.cellSize / 2 - size / 2 - 5, y + GameView.cellSize / 2 - size / 2 - 3, size, 4, bulletDestroy));
    }

    public static void addTankDestroy(int x, int y) {
        int size = 200;
        // x + GameView.cellSize/2 子弹正中心
        // xy有点偏差(图片问题)
        animations.add(new Animation(x + GameView.cellSize - size / 2, y + GameView.cellSize - size / 2, size, 6, bulletDestroy));
    }

    public static void addEnemyBorn(int x, int y) {
        int size = GameView.cellSize * 2;
        animations.add(new Animation(x - 10, y - 10, size, 7, enemyBorn));
    }

    public static void reStart() {
        animations.clear();
    }
}
