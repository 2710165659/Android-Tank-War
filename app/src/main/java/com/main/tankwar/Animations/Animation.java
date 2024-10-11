package com.main.tankwar.Animations;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;

public class Animation {
    protected  ArrayList<Bitmap> frames;

    public int timer; // 计时器
    private final int x,y,size,frameDuration;
    // frameDuration越大播放越慢
    public Animation(int x, int y, int size, int frameDuration, ArrayList<Bitmap> frames) {
        this.timer = 0;
        this.x = x;
        this.y = y;
        this.size = size;
        this.frameDuration = frameDuration;
        this.frames = frames;
    }

    public boolean draw(Canvas canvas) {
        timer++;
        int index = timer / frameDuration;
        if (index >= frames.size()) {
            return false;
        } else {
            canvas.drawBitmap(frames.get(index),null,new Rect(x,y,x+size,y+size),null);
        }
        return true;
    }

}
