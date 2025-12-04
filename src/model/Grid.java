package model;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    private final int width;
    private final int height;
    private final char[][] cells;
    private Point start;
    private Point goal;

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new char[height][width];
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public Point getStart() { return start; }
    public Point getGoal() { return goal; }
    public char getCell(int x, int y) { return cells[x][y]; }

    public void setCell(int x, int y, char value) {
        if (isValid(x, y)) {
            cells[x][y] = value;
        }
    }

    public void setStart(Point start) {
        this.start = start;
        setCell(start.getX(), start.getY(), 'S');
    }

    public void setGoal(Point goal) {
        this.goal = goal;
        setCell(goal.getX(), goal.getY(), 'G');
    }

    public boolean isValid(int x, int y) {
        return x >= 0 && x < height && y >= 0 && y < width;
    }

    public boolean isObstacle(int x, int y) {
        return isValid(x, y) && cells[x][y] == '#';
    }

    public boolean isWalkable(int x, int y) {
        return isValid(x, y) && cells[x][y] != '#';
    }

    public void fillWithRoads() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j] = '.';
            }
        }
    }

    public List<Point> getNeighbors(Point p) {
        List<Point> neighbors = new ArrayList<>();
        int[][] directions = {{-1,0}, {1,0}, {0,-1}, {0,1}};

        for (int[] dir : directions) {
            int newX = p.getX() + dir[0];
            int newY = p.getY() + dir[1];

            if (isWalkable(newX, newY)) {
                neighbors.add(new Point(newX, newY));
            }
        }

        return neighbors;
    }
}