package model;

import java.util.List;
import java.util.Set;

public class PathfindingResult {
    private final List<Point> path;
    private final Set<Point> visitedNodes;
    private final long executionTimeMs;
    private final String algorithmName;

    public PathfindingResult(List<Point> path, Set<Point> visitedNodes,
                             long executionTimeMs, String algorithmName) {
        this.path = path;
        this.visitedNodes = visitedNodes;
        this.executionTimeMs = executionTimeMs;
        this.algorithmName = algorithmName;
    }

    public List<Point> getPath() { return path; }
    public Set<Point> getVisitedNodes() { return visitedNodes; }
    public long getExecutionTimeMs() { return executionTimeMs; }
    public double getExecutionTimeSec() { return executionTimeMs / 1000.0; }
    public String getAlgorithmName() { return algorithmName; }
    public int getPathLength() { return path != null ? path.size() : 0; }
    public int getVisitedNodesCount() { return visitedNodes.size(); }

    public double getEfficiency() {
        if (path == null || visitedNodes.isEmpty()) return 0.0;
        return (path.size() * 100.0) / visitedNodes.size();
    }

    public boolean isSuccessful() {
        return path != null && !path.isEmpty();
    }
}