package com.main.tankwar.gameobj;

import com.main.tankwar.Animations.AnimationManager;
import com.main.tankwar.GameController;
import com.main.tankwar.GameView;
import com.main.tankwar.enums.Direction;
import com.main.tankwar.enums.MapElementType;
import com.main.tankwar.utlis.GameUtils;

public abstract class Tank extends MoveObject {
    public Bullet bullet;
    protected GameController gc;
    public int remainingLives;
    public boolean visible;

    public Tank(Direction dir, int speed, int x, int y,int remainingLives) {
        super(dir, speed, GameView.cellSize * x, GameView.cellSize * y, 0);
        this.remainingLives = remainingLives;
    }

    // 返回是否成功移动
    @Override
    public final boolean move() {
        MoveObject obj = new MoveObject(dir, speed, x, y, size);
        obj.move();
        for (MapElement e : gc.map) {
            if (GameUtils.isColliding(e, obj) &&
                    (e.type == MapElementType.STEEL ||
                            e.type == MapElementType.WALL ||
                            e.type == MapElementType.WATER)) {
                return false;
            }
        }
        return super.move();
    }

    //返回子弹是否成功移动
    public abstract void bulletMove();

    public void hurt() {
        remainingLives -= 1;
        if(remainingLives == -1){
            AnimationManager.addTankDestroy(x,y);
            visible = false;
        }
    }

    protected final void createBullet(){
        bullet = new Bullet (gc);
    };

    public final void shootBullet() {
        if (!bullet.visible) {
            bullet.visible = true;
            int x = this.x + size / 3 - 2, y = this.y + size / 3;
            switch (dir) {
                case UP:
                    y -= size / 2 - 2;
                    break;
                case DOWN:
                    y += size / 2 - 2;
                    break;
                case LEFT:
                    x -= size / 2 - 2;
                    break;
                case RIGHT:
                    x += size / 2 - 2;
            }
            bullet.dir = dir;
            bullet.x = x;
            bullet.y = y;
        }
    }

    public final void setGC(GameController gameController){
        this.gc = gameController;
        createBullet();
    }
}
