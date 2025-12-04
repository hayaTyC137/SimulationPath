package algorithm;


import model.Grid;
import model.PathfindingResult;
import model.Point;
import view.GridVisualizer;

import java.util.*;

public abstract class PathfindingAlgorithm {
    protected final Grid grid;
    protected final GridVisualizer visualizer;

    public PathfindingAlgorithm(Grid grid, GridVisualizer visualizer) {
        this.grid = grid;
        this.visualizer = visualizer;
    }

    public abstract String getName();

    public PathfindingResult findPath() {
        Set<Point> visited = new HashSet<>();
        long startTime = System.currentTimeMillis();

        List<Point> path = search(visited);

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        return new PathfindingResult(path, visited, executionTime, getName());
    }

    protected abstract List<Point> search(Set<Point> visited);

    protected List<Point> reconstructPath(Point end) {
        List<Point> path = new ArrayList<>();
        Point current = end;

        while (current != null) {
            path.add(current);
            current = current.getParent();
        }

        Collections.reverse(path);
        return path;
    }

    protected double heuristic(Point a, Point b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }

    protected void visualizeStep(Set<Point> visited, String info) {
        try {
            visualizer.clearScreen();
            System.out.println("\u001B[36m" + getName() + " - " + info + "\u001B[0m");
            System.out.println("Посещено узлов: " + visited.size());
            visualizer.printGrid(visited, new ArrayList<>());
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}