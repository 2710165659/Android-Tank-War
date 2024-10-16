package com.main.tankwar.utlis;


import com.main.tankwar.enums.MapElementType;
import com.main.tankwar.gameobj.MapElement;
import com.main.tankwar.gameobj.GameObject;
import java.util.ArrayList;

public class GameUtils {
    public static boolean isColliding(GameObject a, GameObject b) {
        // 计算每个对象的右下角位置
        int aRight = a.x + a.size;
        int aBottom = a.y + a.size;

        int bRight = b.x + b.size;
        int bBottom = b.y + b.size;

        // 检查是否有重叠
        return (a.x < bRight && aRight > b.x && a.y < bBottom && aBottom > b.y);
    }


    public static void getBorder(ArrayList<MapElement> tmp) {
        for (int j = 0; j < 28; j++) {
            tmp.add(new MapElement(MapElementType.STEEL, 0, j));
            tmp.add(new MapElement(MapElementType.STEEL, 27, j));
        }
        for (int i = 1; i < 27; i++) {
            tmp.add(new MapElement(MapElementType.STEEL, i, 0));
            tmp.add(new MapElement(MapElementType.STEEL, i, 27));
        }
    }

    public static void getHomeBorder(ArrayList<MapElement> tmp) {
        tmp.add(new MapElement(MapElementType.WALL, 12, 24));
        tmp.add(new MapElement(MapElementType.WALL, 12, 25));
        tmp.add(new MapElement(MapElementType.WALL, 12, 26));

        tmp.add(new MapElement(MapElementType.WALL, 13, 24));
        tmp.add(new MapElement(MapElementType.WALL, 14, 24));

        tmp.add(new MapElement(MapElementType.WALL, 15, 24));
        tmp.add(new MapElement(MapElementType.WALL, 15, 25));
        tmp.add(new MapElement(MapElementType.WALL, 15, 26));
    }

    public static void setMapRect(ArrayList<MapElement> tmp, MapElementType type, int x1, int y1, int x2, int y2) {
        for (int x=x1; x <= x2; x++) {
            for (int y=y1; y <= y2; y++) {
                tmp.add(new MapElement(type, x, y));
            }
        }
    }

    public static void getTestBorder(ArrayList<MapElement> tmp) {
        tmp.add(new MapElement(MapElementType.STEEL, 12, 24));
        tmp.add(new MapElement(MapElementType.STEEL, 12, 25));
        tmp.add(new MapElement(MapElementType.STEEL, 12, 26));

        tmp.add(new MapElement(MapElementType.STEEL, 13, 24));
        tmp.add(new MapElement(MapElementType.STEEL, 14, 24));

        tmp.add(new MapElement(MapElementType.STEEL, 15, 24));
        tmp.add(new MapElement(MapElementType.STEEL, 15, 25));
        tmp.add(new MapElement(MapElementType.STEEL, 15, 26));
    }
}
