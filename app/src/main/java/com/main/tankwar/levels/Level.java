package com.main.tankwar.levels;

import com.main.tankwar.gameobj.MapElement;
import com.main.tankwar.gameobj.EnemyTank;
import java.util.ArrayList;

public interface Level {
    void loadLevel(ArrayList<MapElement> map, ArrayList<EnemyTank> enemies);
}