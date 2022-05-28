package me.sploky.ssm.gui.utils;

import net.minecraft.util.Tuple;

import java.util.Objects;

public class Center {
    public static final Center TOP_LEFT_CORNER = new Center(0, 1);
    public static final Center BOTTOM_LEFT_CORNER = new Center(0, 0);
    public static final Center TOP_RIGHT_CORNER = new Center(1, 1);
    public static final Center BOTTOM_RIGHT_CORNER = new Center(1, 0);
    public static final Center CENTER = new Center(.5f, .5f);
    public static final Center BOTTOM_CENTER = new Center(.5f, 0);
    public static final Center TOP_CENTER = new Center(.5f, 1);
    public static final Center LEFT_CENTER = new Center(0, .5f);
    public static final Center RIGHT_CENTER = new Center(1, .5f);

    public float pivotX;
    public float pivotY;

    public Center(float x, float y) {
        this.pivotX = x;
        this.pivotY =  y;
    }

    public Tuple<Integer, Integer> pivotCoords(int x, int y, float width, float height) {
        x -= width * pivotX;
        y -= height * (1 - pivotY);
        return new Tuple<>(x, y);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Center center = (Center) o;
        return Float.compare(center.pivotX, pivotX) == 0 && Float.compare(center.pivotY, pivotY) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pivotX, pivotY);
    }
}
