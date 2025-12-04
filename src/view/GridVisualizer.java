package view;



import model.Grid;
import model.PathfindingResult;
import model.Point;

import java.util.*;

public class GridVisualizer {
    private final Grid grid;

    public GridVisualizer(Grid grid) {
        this.grid = grid;
    }

    public void printGrid(Set<Point> visited, List<Point> path) {
        System.out.println("\n  ┌" + "─".repeat(grid.getWidth() * 2) + "┐");

        for (int i = 0; i < grid.getHeight(); i++) {
            System.out.print("  │");
            for (int j = 0; j < grid.getWidth(); j++) {
                Point p = new Point(i, j);

                if (p.equals(grid.getStart())) {
                    System.out.print(ConsoleColors.GREEN + GridSymbols.TRUCK + ConsoleColors.RESET);
                } else if (p.equals(grid.getGoal())) {
                    System.out.print(ConsoleColors.RED + GridSymbols.WAREHOUSE + ConsoleColors.RESET);
                } else if (path.contains(p)) {
                    System.out.print(ConsoleColors.YELLOW + GridSymbols.PATH + ConsoleColors.RESET);
                } else if (visited.contains(p)) {
                    System.out.print(ConsoleColors.BLUE + GridSymbols.VISITED + ConsoleColors.RESET);
                } else if (grid.getCell(i, j) == '#') {
                    System.out.print(GridSymbols.OBSTACLE);
                } else {
                    System.out.print(GridSymbols.ROAD);
                }
            }
            System.out.println("│");
        }

        System.out.println("  └" + "─".repeat(grid.getWidth() * 2) + "┘");
        printLegend();
    }

    private void printLegend() {
        System.out.println("\n  Легенда:");
        System.out.println("  " + ConsoleColors.GREEN + GridSymbols.TRUCK + ConsoleColors.RESET + " - Грузовик (старт)");
        System.out.println("  " + ConsoleColors.RED + GridSymbols.WAREHOUSE + ConsoleColors.RESET + " - Склад (цель)");
        System.out.println("  " + GridSymbols.OBSTACLE + " - Препятствия");
        System.out.println("  " + ConsoleColors.BLUE + GridSymbols.VISITED + ConsoleColors.RESET + " - Посещённые узлы");
        System.out.println("  " + ConsoleColors.YELLOW + GridSymbols.PATH + ConsoleColors.RESET + " - Оптимальный путь");
    }

    public void animatePath(PathfindingResult result) throws InterruptedException {
        List<Point> path = result.getPath();
        Set<Point> visited = result.getVisitedNodes();

        for (int i = 0; i < path.size(); i++) {
            clearScreen();
            System.out.println(ConsoleColors.GREEN + "Оптимальный путь найден!" + ConsoleColors.RESET);
            printStatisticsBox(result, i + 1);
            printGrid(visited, path.subList(0, i + 1));
            Thread.sleep(200);
        }
    }

    private void printStatisticsBox(PathfindingResult result, int currentStep) {
        System.out.println("\n╔═══════════════════════════════════════════════╗");
        System.out.println("║            ФИНАЛЬНАЯ СТАТИСТИКА               ║");
        System.out.println("╠═══════════════════════════════════════════════╣");
        System.out.printf("║  Длина пути:           %6d узлов          ║%n", result.getPathLength());
        System.out.printf("║  Посещено узлов:       %6d узлов          ║%n", result.getVisitedNodesCount());
        System.out.printf("║  Пройдено узлов:       %6d / %d           ║%n", currentStep, result.getPathLength());
        System.out.printf("║  Эффективность:        %.2f%%                ║%n", result.getEfficiency());
        System.out.println("╚═══════════════════════════════════════════════╝");
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}