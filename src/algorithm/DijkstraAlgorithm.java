package algorithm;



import model.Grid;
import model.Point;
import view.GridVisualizer;

import java.util.*;

public class DijkstraAlgorithm extends PathfindingAlgorithm {
    public DijkstraAlgorithm(Grid grid, GridVisualizer visualizer) {
        super(grid, visualizer);
    }

    @Override
    public String getName() {
        return "Алгоритм Дейкстры";
    }

    @Override
    protected List<Point> search(Set<Point> visited) {
        PriorityQueue<Point> queue = new PriorityQueue<>(Comparator.comparingDouble(Point::getG));
        Map<String, Double> distances = new HashMap<>();

        Point start = grid.getStart();
        start.setG(0);
        queue.add(start);
        distances.put(start.toKey(), Double.valueOf(0.0));

        while (!queue.isEmpty()) {
            Point current = queue.poll();

            if (current.getG() > distances.getOrDefault(current.toKey(), Double.valueOf(Double.MAX_VALUE))) {
                continue;
            }

            if (visited.contains(current)) continue;
            visited.add(current);

            visualizeStep(visited, "Исследование узлов");

            if (current.equals(grid.getGoal())) {
                return reconstructPath(current);
            }

            for (Point neighbor : grid.getNeighbors(current)) {
                double newDist = current.getG() + 1;

                if (newDist < distances.getOrDefault(neighbor.toKey(), Double.valueOf(Double.MAX_VALUE))) {
                    distances.put(neighbor.toKey(), Double.valueOf(newDist));
                    neighbor.setG(newDist);
                    neighbor.setParent(current);
                    queue.add(neighbor);
                }
            }
        }

        return null;
    }
}