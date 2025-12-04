package Practice.pathfinding;


import algorithm.AStarAlgorithm;
import algorithm.DijkstraAlgorithm;
import algorithm.GreedyAlgorithm;
import algorithm.PathfindingAlgorithm;
import generator.FixedGridGenerator;
import generator.GridGenerator;
import generator.RandomGridGenerator;
import model.Grid;
import model.PathfindingResult;
import view.ConsoleColors;
import view.GridVisualizer;
import view.StatisticsReporter;

import java.util.*;

public class PathfindingApplication {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 15;

    private final Scanner scanner;
    private Grid grid;
    private GridVisualizer visualizer;
    private final StatisticsReporter reporter;

    public PathfindingApplication() {
        this.scanner = new Scanner(System.in);
        this.reporter = new StatisticsReporter();
    }

    public void run() throws InterruptedException {
        while (true) {
            clearScreen();
            printHeader();

            if (!setupGrid()) break;

            PathfindingAlgorithm algorithm = selectAlgorithm();
            if (algorithm == null) break;

            executeAlgorithm(algorithm);

            System.out.println("\nНажмите Enter для продолжения...");
            scanner.nextLine();
            scanner.nextLine();
        }

        scanner.close();
        System.out.println("\nСпасибо за использование программы!");
    }

    private void printHeader() {
        System.out.println("╔═══════════════════════════════════════════════╗");
        System.out.println("║   ВИЗУАЛИЗАЦИЯ АЛГОРИТМОВ ПОИСКА ПУТИ          ║");
        System.out.println("╚═══════════════════════════════════════════════╝\n");
    }

    private boolean setupGrid() {
        System.out.println("Настройка карты:");
        System.out.println("1. Использовать фиксированную карту");
        System.out.println("2. Сгенерировать случайную карту");
        System.out.print("\nВаш выбор: ");

        int mapChoice = scanner.nextInt();

        grid = new Grid(WIDTH, HEIGHT);
        visualizer = new GridVisualizer(grid);

        GridGenerator generator;
        if (mapChoice == 2) {
            System.out.print("Введите процент препятствий (10-40): ");
            int obstaclePercent = scanner.nextInt();
            generator = new RandomGridGenerator(obstaclePercent);
        } else {
            generator = new FixedGridGenerator();
        }

        generator.generate(grid);
        return true;
    }

    private PathfindingAlgorithm selectAlgorithm() {
        clearScreen();
        printHeader();

        System.out.println("Выберите алгоритм:");
        System.out.println("1. Алгоритм Дейкстры");
        System.out.println("2. Алгоритм A*");
        System.out.println("3. Жадный алгоритм");
        System.out.println("4. Выход");
        System.out.print("\nВаш выбор: ");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1: return new DijkstraAlgorithm(grid, visualizer);
            case 2: return new AStarAlgorithm(grid, visualizer);
            case 3: return new GreedyAlgorithm(grid, visualizer);
            default: return null;
        }
    }

    private void executeAlgorithm(PathfindingAlgorithm algorithm) throws InterruptedException {
        clearScreen();
        System.out.println("\n" + ConsoleColors.CYAN + "Начальная карта:" + ConsoleColors.RESET);
        visualizer.printGrid(new HashSet<>(), new ArrayList<>());
        Thread.sleep(1500);

        System.out.println("\n" + ConsoleColors.YELLOW + "Запуск " +
                algorithm.getName() + "..." + ConsoleColors.RESET);

        PathfindingResult result = algorithm.findPath();

        displayResults(result);
    }

    private void displayResults(PathfindingResult result) throws InterruptedException {
        clearScreen();

        if (result.isSuccessful()) {
            System.out.println(ConsoleColors.GREEN + "\n✓ Путь найден!" + ConsoleColors.RESET);
            reporter.printDetailedStatistics(result);
            visualizer.animatePath(result);
        } else {
            System.out.println(ConsoleColors.RED + "\n✗ Путь не найден!" + ConsoleColors.RESET);
            reporter.printDetailedStatistics(result);
        }
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String[] args) throws InterruptedException {
        PathfindingApplication app = new PathfindingApplication();
        app.run();
    }
}
