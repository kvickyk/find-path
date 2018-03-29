package com.findapath;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static java.util.Objects.isNull;

public class FindPath {
    private Grid grid;
    private Point start;
    private List<Path> result = Lists.newArrayList();
    private LinkedList<Path> queue = new LinkedList<>();
    public static void main(String[] args) {
        try {
            new FindPath(args).run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FindPath(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
        String line = null;

        while ((line = reader.readLine()) != null) {
            if(line.contains("grid")) {
                String[] values = line.split(" ");
                grid = new Grid(Integer.valueOf(values[1]), Integer.valueOf(values[2]));
            } else if (line.contains("blocked")) {
                String[] values = line.split(" ");
                grid.addBlockedPoint(Integer.valueOf(values[1]), Integer.valueOf(values[2]));
            } else if (line.contains("start")) {
                String[] values = line.split(" ");
                start = Point.getPoint(Integer.valueOf(values[1]), Integer.valueOf(values[2]));
            }
        }
    }

    public void run() {
        queue.add(Path.newInstance(start, null));

        Stopwatch timer = Stopwatch.createStarted();
        while (!queue.isEmpty()) {
            Path currentPath = queue.remove();
            Point tip = currentPath.getTip();

            // up
            Point up = Point.up(tip);
            Point left = Point.left(tip);
            Point right = Point.right(tip);
            Point down = Point.down(tip);
            processPoint(up, currentPath);
            processPoint(left, currentPath);
            processPoint(right, currentPath);
            processPoint(down, currentPath);

        }

        System.out.println("Time: " + timer.toString());
        System.out.println("Results: " + result.size());
        //print result
        result.forEach(v -> {
            System.out.println(v.toString());
        });
    }


    private void processPoint(Point p, Path currentPath) {
        if (!isNull(p) && grid.isValidPoint(p) && !currentPath.contains(p)) {
            Path newPath = Path.newInstance(p, currentPath);
            if (newPath.getLength() == grid.numPoints()) {
                result.add(newPath);
            }
            queue.add(newPath);
        }
    }
}
