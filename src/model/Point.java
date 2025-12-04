package model;

import java.util.Objects;

public class Point {
    private final int x;
    private final int y;
    private Point parent;
    private double g, h, f;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        this.g = Double.MAX_VALUE;
        this.h = 0;
        this.f = 0;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public Point getParent() { return parent; }
    public double getG() { return g; }
    public double getH() { return h; }
    public double getF() { return f; }

    public void setParent(Point parent) { this.parent = parent; }
    public void setG(double g) { this.g = g; }
    public void setH(double h) { this.h = h; }
    public void setF(double f) { this.f = f; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public String toKey() {
        return x + "," + y;
    }
}