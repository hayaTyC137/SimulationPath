package algorithm;



import model.Grid;
import model.Point;
import view.GridVisualizer;

import java.util.*;

public class GreedyAlgorithm extends PathfindingAlgorithm {
    public GreedyAlgorithm(Grid grid, GridVisualizer visualizer) {
        super(grid, visualizer);
    }

    @Override
    public String getName() {
        return "Жадный алгоритм";
    }

    @Override
    protected List<Point> search(Set<Point> visited) {
        PriorityQueue<Point> queue = new PriorityQueue<>(Comparator.comparingDouble(Point::getH));
        Set<String> inQueue = new HashSet<>();

        Point start = grid.getStart();
        start.setH(heuristic(start, grid.getGoal()));
        queue.add(start);
        inQueue.add(start.toKey());

        while (!queue.isEmpty()) {
            Point current = queue.poll();
            inQueue.remove(current.toKey());

            if (visited.contains(current)) continue;
            visited.add(current);

            visualizeStep(visited, "Движение к цели (выбор узла с минимальной эвристикой)");

            if (current.equals(grid.getGoal())) {
                return reconstructPath(current);
            }

            for (Point neighbor : grid.getNeighbors(current)) {
                if (!visited.contains(neighbor) && !inQueue.contains(neighbor.toKey())) {
                    neighbor.setH(heuristic(neighbor, grid.getGoal()));
                    neighbor.setParent(current);
                    queue.add(neighbor);
                    inQueue.add(neighbor.toKey());
                }
            }
        }

        return null;
    }
}