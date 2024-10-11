package com.main.tankwar.gameobj;

import com.main.tankwar.enums.Direction;

public class MoveObject extends GameObject {
    public Direction dir;
    public int speed;

    public MoveObject(Direction dir, int speed, int x, int y,int size) {
        super(x, y, 0);
        this.size = size;
        this.dir = dir;
        this.speed = speed;
    }

    // 返回是否成功移动
    public boolean move() {
        switch (dir) {
            case UP:
                y -= speed;
                break;
            case DOWN:
                // 向下移动，增加 y 坐标
                y += speed;
                break;
            case LEFT:
                // 向左移动，减少 x 坐标
                x -= speed;
                break;
            case RIGHT:
                // 向右移动，增加 x 坐标
                x += speed;
                break;
        }
        return true;
    }
}
