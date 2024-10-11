package com.main.tankwar.levels;

import com.main.tankwar.enums.EnemyType;
import com.main.tankwar.enums.MapElementType;
import com.main.tankwar.gameobj.EnemyTank;
import com.main.tankwar.gameobj.MapElement;
import com.main.tankwar.utlis.GameUtils;

import java.util.ArrayList;

public class Level2 implements Level{
    @Override
    public void loadLevel(ArrayList<MapElement> map, ArrayList<EnemyTank> enemies) {
        // 获取地图边界
        GameUtils.getBorder(map);
        GameUtils.getHomeBorder(map);
        //GameUtils.getTestBorder(map);


        // 添加敌人 9
        enemies.add(new EnemyTank(1 , 1 , EnemyType.ENEMY3));
        enemies.add(new EnemyTank(1 , 1 , EnemyType.ENEMY1));
        enemies.add(new EnemyTank(13 , 1, EnemyType.ENEMY1));
        enemies.add(new EnemyTank(25 , 1, EnemyType.ENEMY3));
        enemies.add(new EnemyTank(1 , 1 , EnemyType.ENEMY2));
        enemies.add(new EnemyTank(13 , 1 , EnemyType.ENEMY2));
        enemies.add(new EnemyTank(1 , 1 , EnemyType.ENEMY1));
        enemies.add(new EnemyTank(25 , 1, EnemyType.ENEMY1));
        enemies.add(new EnemyTank(25 , 1, EnemyType.ENEMY2));


        //地图元素
        GameUtils.setMapRect(map, MapElementType.GRASS,1,3,2,6);
        GameUtils.setMapRect(map, MapElementType.GRASS,3,1,4,4);
        GameUtils.setMapRect(map, MapElementType.GRASS,23,1,24,2);
        GameUtils.setMapRect(map, MapElementType.GRASS,25,3,26,4);
        GameUtils.setMapRect(map, MapElementType.GRASS,1,23,2,24);
        GameUtils.setMapRect(map, MapElementType.GRASS,3,25,4,26);
        GameUtils.setMapRect(map, MapElementType.GRASS,21,25,22,26);
        GameUtils.setMapRect(map, MapElementType.GRASS,23,23,24,26);
        GameUtils.setMapRect(map, MapElementType.GRASS,25,21,26,24);

        GameUtils.setMapRect(map, MapElementType.WATER,1,11,2,12);
        GameUtils.setMapRect(map, MapElementType.WATER,23,13,26,14);

        GameUtils.setMapRect(map, MapElementType.STEEL,1,7,2,7);
        GameUtils.setMapRect(map, MapElementType.STEEL,25,5,26,5);
        GameUtils.setMapRect(map, MapElementType.STEEL,9,11,9,12);
        GameUtils.setMapRect(map, MapElementType.STEEL,13,11,13,12);
        GameUtils.setMapRect(map, MapElementType.STEEL,1,25,2,26);
        GameUtils.setMapRect(map, MapElementType.STEEL,25,25,26,25);
        GameUtils.setMapRect(map, MapElementType.STEEL,25,26,25,26);

        GameUtils.setMapRect(map, MapElementType.WALL,11,3,12,3);
        GameUtils.setMapRect(map, MapElementType.WALL,9,4,18,4);
        GameUtils.setMapRect(map, MapElementType.WALL,8,5,20,5);
        GameUtils.setMapRect(map, MapElementType.WALL,8,6,22,6);
        GameUtils.setMapRect(map, MapElementType.WALL,7,7,23,8);
        GameUtils.setMapRect(map, MapElementType.WALL,6,9,8,9);
        GameUtils.setMapRect(map, MapElementType.WALL,15,9,20,9);
        GameUtils.setMapRect(map, MapElementType.WALL,23,9,23,9);
        GameUtils.setMapRect(map, MapElementType.WALL,6,10,6,10);
        GameUtils.setMapRect(map, MapElementType.WALL,17,10,20,10);
        GameUtils.setMapRect(map, MapElementType.WALL,23,10,23,10);
        GameUtils.setMapRect(map, MapElementType.WALL,6,11,6,12);
        GameUtils.setMapRect(map, MapElementType.WALL,17,11,19,12);
        GameUtils.setMapRect(map, MapElementType.WALL,5,13,6,13);
        GameUtils.setMapRect(map, MapElementType.WALL,17,13,19,13);
        GameUtils.setMapRect(map, MapElementType.WALL,5,14,6,14);
        GameUtils.setMapRect(map, MapElementType.WALL,9,14,11,14);
        GameUtils.setMapRect(map, MapElementType.WALL,16,14,19,14);
        GameUtils.setMapRect(map, MapElementType.WALL,5,15,20,16);
        GameUtils.setMapRect(map, MapElementType.WALL,4,17,21,18);
        GameUtils.setMapRect(map, MapElementType.WALL,3,19,22,19);
        GameUtils.setMapRect(map, MapElementType.WALL,7,20,18,20);
        GameUtils.setMapRect(map, MapElementType.WALL,9,21,16,21);
        GameUtils.setMapRect(map, MapElementType.WALL,3,21,6,21);
        GameUtils.setMapRect(map, MapElementType.WALL,19,21,22,21);
        GameUtils.setMapRect(map, MapElementType.WALL,11,22,14,22);
        GameUtils.setMapRect(map, MapElementType.WALL,3,22,8,22);
        GameUtils.setMapRect(map, MapElementType.WALL,17,22,22,22);
        GameUtils.setMapRect(map, MapElementType.WALL,5,23,8,23);
        GameUtils.setMapRect(map, MapElementType.WALL,17,23,20,23);

    }
}
