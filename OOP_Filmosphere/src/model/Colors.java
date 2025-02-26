package model;

import java.awt.Color;

public enum Colors {
    MAIN_COLOR(0xEB, 0x5E, 0x55),
    DARK_COLOR(48, 52, 63),
    LIGHT_COLOR(204, 219, 220);

    private final Color color;

    Colors(int r, int g, int b) {
        this.color = new Color(r, g, b);
    }

    public Color getColor() {
        return color;
    }
}
