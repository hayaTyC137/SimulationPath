package generator;

import model.Grid;
import model.Point;

import java.util.Random;

public class FixedGridGenerator implements GridGenerator {
    @Override
    public void generate(Grid grid) {
        grid.fillWithRoads();

        Random rand = new Random(42);
        int obstacles = 40;
        for (int i = 0; i < obstacles; i++) {
            int x = rand.nextInt(grid.getHeight());
            int y = rand.nextInt(grid.getWidth());
            if (!(x == 1 && y == 1) && !(x == grid.getHeight()-2 && y == grid.getWidth()-2)) {
                grid.setCell(x, y, '#');
            }
        }

        grid.setStart(new Point(1, 1));
        grid.setGoal(new Point(grid.getHeight() - 2, grid.getWidth() - 2));
    }
}