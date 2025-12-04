package algorithm;



import model.Grid;
import model.Point;
import view.GridVisualizer;

import java.util.*;

public class AStarAlgorithm extends PathfindingAlgorithm {
    public AStarAlgorithm(Grid grid, GridVisualizer visualizer) {
        super(grid, visualizer);
    }

    @Override
    public String getName() {
        return "Алгоритм A*";
    }

    @Override
    protected List<Point> search(Set<Point> visited) {
        PriorityQueue<Point> openSet = new PriorityQueue<>(Comparator.comparingDouble(Point::getF));
        Map<String, Double> gScores = new HashMap<>();

        Point start = grid.getStart();
        start.setG(0);
        start.setH(heuristic(start, grid.getGoal()));
        start.setF(start.getG() + start.getH());
        openSet.add(start);
        gScores.put(start.toKey(), Double.valueOf(0.0));

        while (!openSet.isEmpty()) {
            Point current = openSet.poll();

            if (current.getG() > gScores.getOrDefault(current.toKey(), Double.valueOf(Double.MAX_VALUE))) {
                continue;
            }

            if (visited.contains(current)) continue;
            visited.add(current);

            visualizeStep(visited, "Исследование с эвристикой (f = g + h)");

            if (current.equals(grid.getGoal())) {
                return reconstructPath(current);
            }

            for (Point neighbor : grid.getNeighbors(current)) {
                double tentativeG = current.getG() + 1;

                if (tentativeG < gScores.getOrDefault(neighbor.toKey(), Double.valueOf(Double.MAX_VALUE))) {
                    gScores.put(neighbor.toKey(), Double.valueOf(tentativeG));
                    neighbor.setParent(current);
                    neighbor.setG(tentativeG);
                    neighbor.setH(heuristic(neighbor, grid.getGoal()));
                    neighbor.setF(neighbor.getG() + neighbor.getH());
                    openSet.add(neighbor);
                }
            }
        }

        return null;
    }
}