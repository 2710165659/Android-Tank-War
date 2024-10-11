package com.main.tankwar.gameobj;

import com.main.tankwar.GameView;
import com.main.tankwar.enums.MapElementType;

public final class MapElement extends GameObject {
    public MapElementType type;

    public MapElement(MapElementType type, int x, int y) {
        super(x * GameView.cellSize, y * GameView.cellSize, GameView.cellSize);
        this.type = type;
    }
}
