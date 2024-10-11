package com.main.tankwar.levels;

import com.main.tankwar.enums.EnemyType;
import com.main.tankwar.enums.MapElementType;
import com.main.tankwar.gameobj.EnemyTank;
import com.main.tankwar.gameobj.MapElement;
import com.main.tankwar.utlis.GameUtils;

import java.util.ArrayList;

public class Level3 implements Level{
    @Override
    public void loadLevel(ArrayList<MapElement> map, ArrayList<EnemyTank> enemies) {
        // 获取地图边界
        GameUtils.getBorder(map);
        GameUtils.getHomeBorder(map);
        //GameUtils.getTestBorder(map);

        // 添加敌人 12
        enemies.add(new EnemyTank(1 , 1 , EnemyType.ENEMY1));
        enemies.add(new EnemyTank(13 , 1 , EnemyType.ENEMY2));
        enemies.add(new EnemyTank(25 , 1, EnemyType.ENEMY3));
        enemies.add(new EnemyTank(1 , 1 , EnemyType.ENEMY3));
        enemies.add(new EnemyTank(13 , 1 , EnemyType.ENEMY2));
        enemies.add(new EnemyTank(25 , 1, EnemyType.ENEMY1));
        enemies.add(new EnemyTank(1 , 1 , EnemyType.ENEMY2));
        enemies.add(new EnemyTank(13 , 1 , EnemyType.ENEMY1));
        enemies.add(new EnemyTank(25 , 1, EnemyType.ENEMY3));
        enemies.add(new EnemyTank(1 , 1 , EnemyType.ENEMY2));
        enemies.add(new EnemyTank(13 , 1 , EnemyType.ENEMY3));
        enemies.add(new EnemyTank(25 , 1, EnemyType.ENEMY1));


        //地图元素
        GameUtils.setMapRect(map, MapElementType.WATER,3,7,22,8);
        GameUtils.setMapRect(map, MapElementType.WATER,25,7,26,8);
        GameUtils.setMapRect(map, MapElementType.WATER,1,17,4,18);
        GameUtils.setMapRect(map, MapElementType.WATER,7,17,16,18);
        GameUtils.setMapRect(map, MapElementType.WATER,19,17,26,18);


        GameUtils.setMapRect(map, MapElementType.GRASS,1,3,2,8);
        GameUtils.setMapRect(map, MapElementType.GRASS,3,5,6,6);
        GameUtils.setMapRect(map, MapElementType.GRASS,17,13,18,14);
        GameUtils.setMapRect(map, MapElementType.GRASS,15,15,22,16);
        GameUtils.setMapRect(map, MapElementType.GRASS,1,19,4,22);
        GameUtils.setMapRect(map, MapElementType.GRASS,1,23,2,24);

        GameUtils.setMapRect(map, MapElementType.STEEL,15,4,16,4);
        GameUtils.setMapRect(map, MapElementType.STEEL,23,11,26,11);
        GameUtils.setMapRect(map, MapElementType.STEEL,21,14,24,14);
        GameUtils.setMapRect(map, MapElementType.STEEL,7,15,8,16);
        GameUtils.setMapRect(map, MapElementType.STEEL,11,16,12,16);
        GameUtils.setMapRect(map, MapElementType.STEEL,19,22,20,22);
        GameUtils.setMapRect(map, MapElementType.STEEL,3,24,4,24);

        GameUtils.setMapRect(map, MapElementType.WALL,5,1,6,2);
        GameUtils.setMapRect(map, MapElementType.WALL,3,3,8,4);
        GameUtils.setMapRect(map, MapElementType.WALL,11,1,12,5);
        GameUtils.setMapRect(map, MapElementType.WALL,15,2,16,2);
        GameUtils.setMapRect(map, MapElementType.WALL,19,1,20,5);
        GameUtils.setMapRect(map, MapElementType.WALL,21,3,21,4);
        GameUtils.setMapRect(map, MapElementType.WALL,15,5,16,6);
        GameUtils.setMapRect(map, MapElementType.WALL,24,5,25,6);
        GameUtils.setMapRect(map, MapElementType.WALL,3,9,4,10);
        GameUtils.setMapRect(map, MapElementType.WALL,5,11,6,12);
        GameUtils.setMapRect(map, MapElementType.WALL,1,13,4,14);
        GameUtils.setMapRect(map, MapElementType.WALL,7,13,8,14);
        GameUtils.setMapRect(map, MapElementType.WALL,13,10,16,10);
        GameUtils.setMapRect(map, MapElementType.WALL,12,11,16,14);
        GameUtils.setMapRect(map, MapElementType.WALL,17,11,22,11);
        GameUtils.setMapRect(map, MapElementType.WALL,19,12,20,14);
        GameUtils.setMapRect(map, MapElementType.WALL,25,13,26,14);
        GameUtils.setMapRect(map, MapElementType.WALL,5,21,6,24);
        GameUtils.setMapRect(map, MapElementType.WALL,8,19,8,20);
        GameUtils.setMapRect(map, MapElementType.WALL,9,21,9,24);
        GameUtils.setMapRect(map, MapElementType.WALL,13,20,16,20);
        GameUtils.setMapRect(map, MapElementType.WALL,16,21,16,22);
        GameUtils.setMapRect(map, MapElementType.WALL,19,23,20,23);
        GameUtils.setMapRect(map, MapElementType.WALL,19,25,20,25);
        GameUtils.setMapRect(map, MapElementType.WALL,21,22,22,22);
        GameUtils.setMapRect(map, MapElementType.WALL,23,21,24,24);

    }
}
