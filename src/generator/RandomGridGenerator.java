package generator;


import model.Grid;
import model.Point;

import java.util.*;

public class RandomGridGenerator implements GridGenerator {
    private final int obstaclePercent;

    public RandomGridGenerator(int obstaclePercent) {
        this.obstaclePercent = Math.max(10, Math.min(40, obstaclePercent));
    }

    @Override
    public void generate(Grid grid) {
        grid.fillWithRoads();
        Random rand = new Random();

        Point start = new Point(rand.nextInt(grid.getHeight()), rand.nextInt(grid.getWidth()));
        Point goal;
        do {
            goal = new Point(rand.nextInt(grid.getHeight()), rand.nextInt(grid.getWidth()));
        } while (goal.equals(start) ||
                Math.abs(goal.getX() - start.getX()) + Math.abs(goal.getY() - start.getY()) < 10);

        grid.setStart(start);
        grid.setGoal(goal);

        int totalCells = grid.getHeight() * grid.getWidth();
        int obstacleCount = (totalCells * obstaclePercent) / 100;

        for (int i = 0; i < obstacleCount; i++) {
            int x = rand.nextInt(grid.getHeight());
            int y = rand.nextInt(grid.getWidth());

            if (!isNearStartOrGoal(grid, x, y)) {
                grid.setCell(x, y, '#');
            }
        }

        if (!isPathPossible(grid)) {
            generate(grid);
        }
    }

    private boolean isNearStartOrGoal(Grid grid, int x, int y) {
        Point p = new Point(x, y);
        if (p.equals(grid.getStart()) || p.equals(grid.getGoal())) return true;

        int[][] directions = {{-1,0}, {1,0}, {0,-1}, {0,1}};
        for (int[] dir : directions) {
            int nx = x + dir[0];
            int ny = y + dir[1];
            Point neighbor = new Point(nx, ny);
            if (neighbor.equals(grid.getStart()) || neighbor.equals(grid.getGoal())) return true;
        }
        return false;
    }

    private boolean isPathPossible(Grid grid) {
        Queue<Point> queue = new LinkedList<>();
        Set<Point> visited = new HashSet<>();

        queue.add(grid.getStart());
        visited.add(grid.getStart());

        while (!queue.isEmpty()) {
            Point current = queue.poll();
            if (current.equals(grid.getGoal())) return true;

            for (Point neighbor : grid.getNeighbors(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        return false;
    }
}