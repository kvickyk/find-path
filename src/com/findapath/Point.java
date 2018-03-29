package com.findapath;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Point {
    private static final Map<Integer, Map<Integer, Point>> allPoints = new HashMap<>();
    private final int x;
    private final int y;

    private Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static final Point getPoint(int x, int y) {
        return allPoints.getOrDefault(x, new HashMap<>()).getOrDefault(y, new Point(x, y));
    }

    public static Point up(Point p) {
        int row = p.getX();
        int col = p.getY();

        --row;
        return getPoint(row, col);
    }

    public static Point left(Point p) {
        int row = p.getX();
        int col = p.getY();

        --col;
        return getPoint(row, col);
    }

    public static Point right(Point p) {
        int row = p.getX();
        int col = p.getY();

        ++col;
        return getPoint(row, col);
    }

    public static Point down(Point p) {
        int row = p.getX();
        int col = p.getY();

        ++row;
        return getPoint(row, col);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x &&
                y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return MessageFormat.format("[{0},{1}]", x, y);
    }
}
