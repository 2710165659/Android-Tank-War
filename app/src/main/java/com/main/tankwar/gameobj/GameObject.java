package com.main.tankwar.gameobj;

public abstract class GameObject {
    public int x, y, size;
    GameObject(int x,int y,int size){
        this.x = x;
        this.y = y;
        this.size = size;
    }
}
