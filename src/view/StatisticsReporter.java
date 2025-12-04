package view;


import model.PathfindingResult;

public class StatisticsReporter {
    public void printDetailedStatistics(PathfindingResult result) {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║          ПОДРОБНАЯ СТАТИСТИКА ВЫПОЛНЕНИЯ                  ║");
        System.out.println("╠═══════════════════════════════════════════════════════════╣");
        System.out.printf("║  Алгоритм:              %-30s║%n", result.getAlgorithmName());
        System.out.println("╠═══════════════════════════════════════════════════════════╣");
        System.out.printf("║  Время выполнения:      %-6d мс  (%.3f сек)        ║%n",
                result.getExecutionTimeMs(), result.getExecutionTimeSec());
        System.out.printf("║  Длина найденного пути: %-6d узлов                   ║%n",
                result.getPathLength());
        System.out.printf("║  Всего посещено узлов:  %-6d узлов                   ║%n",
                result.getVisitedNodesCount());
        System.out.printf("║  Эффективность:         %-6.2f%%                      ║%n",
                result.getEfficiency());
        System.out.println("╠═══════════════════════════════════════════════════════════╣");

        double nodesPerMs = result.getExecutionTimeMs() > 0 ?
                (double) result.getVisitedNodesCount() / result.getExecutionTimeMs() : 0;
        System.out.printf("║  Узлов в миллисекунду:  %-6.2f узлов/мс              ║%n", nodesPerMs);

        double pathRatio = result.getVisitedNodesCount() > 0 ?
                (double) result.getPathLength() / result.getVisitedNodesCount() : 0;
        System.out.printf("║  Соотношение путь/поиск: %-5.2f%%                      ║%n",
                pathRatio * 100);

        System.out.println("╚═══════════════════════════════════════════════════════════╝");
    }

    public void printComparisonHeader() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    СРАВНИТЕЛЬНАЯ СТАТИСТИКА                                ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════════╝");
    }
}