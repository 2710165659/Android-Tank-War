package com.main.tankwar.gameobj;


import com.main.tankwar.GameController;
import com.main.tankwar.GameView;
import com.main.tankwar.enums.Direction;
import com.main.tankwar.enums.MapElementType;
import com.main.tankwar.utlis.GameUtils;
import java.util.Iterator;


public class Bullet extends MoveObject {
    protected final GameController gc;
    public boolean visible;

    public Bullet(GameController gc) {
        super(Direction.UP, GameController.bulletSpeed, 0, 0, GameView.cellSize / 2);
        visible = false;
        this.gc = gc;
    }

    protected boolean hitMap(){
        boolean result = false;
        Iterator<MapElement> iterator = gc.map.iterator();
        while (iterator.hasNext()) {
            MapElement e = iterator.next();
            if (GameUtils.isColliding(e, this)) {
                if (e.type == MapElementType.STEEL) {
                    return true;
                }
                if (e.type == MapElementType.WALL) {
                    iterator.remove();
                    result = true;
                }
            }
        }
        return result;
    }
    protected boolean hitHome(){
        return GameUtils.isColliding(gc.home, this);
    }
    protected Tank hitEnemies(){
        for(EnemyTank e: gc.enemies){
            if(e.visible && GameUtils.isColliding(this,e)){
                return e;
            }
        }
        return null;
    }
    protected Tank hitEnemiesBullet(){
        for(EnemyTank e: gc.enemies){
            if(e.bullet.visible && GameUtils.isColliding(this,e.bullet)){
                return e;
            }
        }
        return null;
    }
    protected Tank hitPlayer(){
        if(GameUtils.isColliding(this,gc.playerTank)){
            return gc.playerTank;
        }
        return null;
    }

}
