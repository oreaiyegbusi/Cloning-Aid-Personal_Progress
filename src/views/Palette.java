package views;

import java.awt.*;

public enum Palette {
    cytosine(0x8db737),
    guanine(0x4f67ad),
    adenine(0xd24942),
    thymine(0xe9a118),
    denaturedBackbone(0x248ca8),
    annealedBackbone(0x00e2d3),
    primer(0xf46d00);

    public final int hexCode;

    Palette(int hexCode) {
        this.hexCode = hexCode;
    }

    Color color() {
        return new Color(hexCode);
    }
}
