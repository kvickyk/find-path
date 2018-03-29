package com.findapath;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

import static java.util.Objects.isNull;

public class FindPathParallel {
    private Grid grid;
    private Point start;
    private List<Path> result = Lists.newArrayList();
    private ConcurrentLinkedQueue<Future<?>> queue = new ConcurrentLinkedQueue<>();
    private ExecutorService executor = Executors.newFixedThreadPool(8);
    public static void main(String[] args) {
        try {
            new FindPathParallel(args).run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FindPathParallel(String[] args) throws IOException {
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
        Stopwatch timer = Stopwatch.createStarted();
        queue.add(executor.submit(new Worker(Path.newInstance(start, null))));

        while(!queue.isEmpty()) {
            try {
                queue.remove().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }


        System.out.println("Time: " + timer.toString());
        System.out.println("Results: " + result.size());
        //print result
        result.forEach(v -> {
            System.out.println(v.toString());
        });
        executor.shutdown();
        try {
            executor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void processPoint(Point p, Path currentPath) {
        if (!isNull(p) && grid.isValidPoint(p) && !currentPath.contains(p)) {
            Path newPath = Path.newInstance(p, currentPath);
            if (newPath.getLength() == grid.numPoints()) {
                synchronized (result) {
                    result.add(newPath);
                }
            }
            queue.add(executor.submit(new Worker(newPath)));
        }
    }

    private class Worker implements Runnable {
        private final Path path;

        public Worker(Path path) {
            this.path = path;
        }

        @Override
        public void run() {
            Point tip = path.getTip();

            Point up = Point.up(tip);
            Point left = Point.left(tip);
            Point right = Point.right(tip);
            Point down = Point.down(tip);

            processPoint(up, path);
            processPoint(left, path);
            processPoint(right, path);
            processPoint(down, path);
        }
    }
}
