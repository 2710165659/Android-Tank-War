package com.main.tankwar.levels;

import com.main.tankwar.enums.EnemyType;
import com.main.tankwar.enums.MapElementType;
import com.main.tankwar.gameobj.MapElement;
import com.main.tankwar.gameobj.EnemyTank;
import com.main.tankwar.utlis.GameUtils;

import java.util.ArrayList;

public class Level1 implements Level {
    @Override
    public void loadLevel(ArrayList<MapElement> map, ArrayList<EnemyTank> enemies) {
        // 获取地图边界
        GameUtils.getBorder(map);
        GameUtils.getHomeBorder(map);
        //GameUtils.getTestBorder(map);

        // 添加敌人 6
        enemies.add(new EnemyTank(1, 1, EnemyType.ENEMY1));
        enemies.add(new EnemyTank(13, 1, EnemyType.ENEMY1));
        enemies.add(new EnemyTank(25, 1, EnemyType.ENEMY3));
        enemies.add(new EnemyTank(1, 1, EnemyType.ENEMY2));
        enemies.add(new EnemyTank(13, 1, EnemyType.ENEMY2));
        enemies.add(new EnemyTank(1, 1, EnemyType.ENEMY1));


        //地图元素
        GameUtils.setMapRect(map, MapElementType.WALL, 3, 3, 4, 10);
        GameUtils.setMapRect(map, MapElementType.WALL, 7, 3, 8, 10);
        GameUtils.setMapRect(map, MapElementType.WALL, 11, 3, 12, 8);
        GameUtils.setMapRect(map, MapElementType.STEEL, 13, 6, 14, 7);
        GameUtils.setMapRect(map, MapElementType.WALL, 15, 3, 16, 8);
        GameUtils.setMapRect(map, MapElementType.WALL, 19, 3, 20, 10);
        GameUtils.setMapRect(map, MapElementType.WALL, 23, 3, 24, 10);

        GameUtils.setMapRect(map, MapElementType.WALL, 1, 13, 2, 13);
        GameUtils.setMapRect(map, MapElementType.STEEL, 1, 14, 2, 14);
        GameUtils.setMapRect(map, MapElementType.WALL, 5, 13, 8, 14);
        GameUtils.setMapRect(map, MapElementType.WALL, 11, 11, 12, 12);
        GameUtils.setMapRect(map, MapElementType.WALL, 15, 11, 16, 12);
        GameUtils.setMapRect(map, MapElementType.WALL, 19, 13, 22, 14);
        GameUtils.setMapRect(map, MapElementType.WALL, 25, 13, 26, 13);
        GameUtils.setMapRect(map, MapElementType.STEEL, 25, 14, 26, 14);

        GameUtils.setMapRect(map, MapElementType.WALL, 3, 17, 4, 24);
        GameUtils.setMapRect(map, MapElementType.WALL, 7, 17, 8, 24);
        GameUtils.setMapRect(map, MapElementType.WALL, 11, 15, 12, 21);
        GameUtils.setMapRect(map, MapElementType.WALL, 13, 16, 14, 17);
        GameUtils.setMapRect(map, MapElementType.WALL, 15, 15, 16, 21);
        GameUtils.setMapRect(map, MapElementType.WALL, 19, 17, 20, 24);
        GameUtils.setMapRect(map, MapElementType.WALL, 23, 17, 24, 24);

    }
}
