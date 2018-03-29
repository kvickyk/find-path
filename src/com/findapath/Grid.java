package com.findapath;

import java.text.MessageFormat;
import java.util.*;

public class Grid {
    private final Set<Point> blockedPoints = new HashSet<>();

    private int rows;
    private int columns;

    public Grid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        print("creating grid({0}, {1})", rows, columns);
    }

    public void addBlockedPoint(int x, int y) {
        print("adding blocking point ({0}, {1})", x, y);
        blockedPoints.add(Point.getPoint(x, y));
    }

    public boolean isValidPoint(Point p) {
        int x = p.getX();
        int y = p.getY();
        return (x >= 0 && x < this.rows && y >= 0 && y < this.columns && !blockedPoints.contains(p));
    }

    public int numPoints() {
        return (this.rows * this.columns) - this.blockedPoints.size();
    }

    private void print(String s, Object ...args) {
        System.out.println(MessageFormat.format("Grid: " + s, args));
    }


}
