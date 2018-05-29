package Game;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
/**
 * Maze Class
 * Generates 20 x 15 maze using depth search algorithm
 * has methods to return valid moves and setting cheese, cats and players in blocks
 */
public class Maze {
    //constants
    private final int MAZE_WIDTH = 17;
    private final int MAZE_HEIGHT = 13;
    private final int MAZE_MAX_WIDTH = MAZE_WIDTH + 2;
    private final int MAZE_MAX_HEIGHT = MAZE_HEIGHT + 2;
    private final int FIRST_ROW = 0;
    private final int LAST_ROW = MAZE_HEIGHT + 1;
    private final int FIRST_COLUMN = 0;
    private final int LAST_COLUMN = MAZE_WIDTH + 2;
    private final int SECOND_WALL_CHANCE = 5;
    //location for cheese
    protected Point cheesePoint;
    //block array base for maze
    private Block blockArr[][];
    //Constructor
    public Maze() {
        //create a 2d array of blocks
        this.blockArr = new Block[MAZE_MAX_WIDTH + 1][MAZE_MAX_HEIGHT];
        for (int i = 0; i < MAZE_MAX_HEIGHT; i++) {
            for (int j = 0; j < MAZE_MAX_WIDTH + 1; j++) {
                blockArr[j][i] = new Block();
            }
        }
        generateMaze();
    }
    //return current state of maze (for display)
    public Block[][] getBlockArr() {
        return this.blockArr;
    }
    //maze generation algorithm using depth first search
    private void generateMaze() {
        Random rand = new Random();
        // 7 x 9 cell grid;
        int visitedGrid = 1;
        Point startPoint = new Point(1, 1);
        //stack to store visited cells
        ArrayList<Point> mazePathing = new ArrayList<>(63);
        mazePathing.add(startPoint);
        //fill maze with walls
        fillMaze();
        //remove top left corner
        blockArr[startPoint.x][startPoint.y].removeWall();
        //create maze path
        createMazePath(visitedGrid, mazePathing, rand, 1, 1);
        revealMazeBorder();
        generateLastCol();
    }
    //fill maze with walls
    private void fillMaze() {
        for (int i = 0; i < MAZE_MAX_HEIGHT; i++) {
            for (int j = 0; j < MAZE_MAX_WIDTH + 1; j++) {
                blockArr[j][i].setWall();
            }
        }
    }
    //reveal maze border
    private void revealMazeBorder() {
        for (int i = 0; i < MAZE_MAX_WIDTH + 1; i++) {
            this.blockArr[i][FIRST_ROW].setRevealed();
            this.blockArr[i][LAST_ROW].setRevealed();
        }
        for (int i = 0; i < MAZE_MAX_HEIGHT; i++) {
            this.blockArr[FIRST_COLUMN][i].setRevealed();
            this.blockArr[LAST_COLUMN][i].setRevealed();
        }
    }
    //method to generate last column
    private void generateLastCol() {
        Random rand = new Random();
        //randomly delete walls in last collumn
        for (int i = 3; i <= MAZE_HEIGHT - 2; i += 2) {
            if (rand.nextInt(Integer.SIZE - 1) % 2 == 0) {
                if (!blockArr[MAZE_WIDTH][i].isWall()) {
                    blockArr[MAZE_WIDTH + 1][i].removeWall();
                    blockArr[MAZE_WIDTH + 1][i + rand.nextInt() % 2].removeWall();
                    blockArr[MAZE_WIDTH + 1][i + rand.nextInt() % 2].removeWall();
                }
            }
            blockArr[MAZE_WIDTH + 1][i + rand.nextInt() % 2].removeWall();
        }
        //open space for corners
        blockArr[MAZE_WIDTH + 1][1].removeWall();
        blockArr[MAZE_WIDTH + 1][MAZE_HEIGHT].removeWall();
    }
    //create maze path using depth first search
    private void createMazePath(int visited, ArrayList<Point> stack, Random randomPick, int x, int y) {
        if (visited != (MAZE_HEIGHT + 1) * (MAZE_WIDTH + 1) / 4) {
            ArrayList<Point> possibleMoves = new ArrayList<>();
            //conditions for finding next unvisited cell
            if (x > 1) {
                if (blockArr[x - 2][y].isWall()) {
                    possibleMoves.add(new Point(x - 2, y));
                } else if (randomPick.nextInt(Integer.SIZE - 1) % SECOND_WALL_CHANCE == 0) {
                    blockArr[x - 1][y].removeWall();
                }
            }
            if (x < MAZE_WIDTH) {
                if (blockArr[x + 2][y].isWall()) {
                    possibleMoves.add(new Point(x + 2, y));
                } else if (randomPick.nextInt(Integer.SIZE - 1) % SECOND_WALL_CHANCE == 0) {
                    blockArr[x + 1][y].removeWall();
                }
            }
            if (y > 1) {
                if (blockArr[x][y - 2].isWall()) {
                    possibleMoves.add(new Point(x, y - 2));
                } else if (randomPick.nextInt(Integer.SIZE - 1) % SECOND_WALL_CHANCE == 0) {
                    blockArr[x][y - 1].removeWall();
                }
            }
            if (y < MAZE_HEIGHT) {
                if (blockArr[x][y + 2].isWall()) {
                    possibleMoves.add(new Point(x, y + 2));
                } else if (randomPick.nextInt(Integer.SIZE - 1) % SECOND_WALL_CHANCE == 0) {
                    blockArr[x][y + 1].removeWall();
                }
            }
            //if num possible moves 0, backtrack
            if (possibleMoves.size() == 0) {
                Point backStep = stack.get(stack.size() - 2);
                stack.remove(stack.size() - 1);
                createMazePath(visited, stack, randomPick, backStep.x, backStep.y);
            } else {
                //find the next unvisited cell randomly
                Point randomMove = possibleMoves.get(randomPick.nextInt(Integer.SIZE - 1) % possibleMoves.size());
                blockArr[randomMove.x][randomMove.y].removeWall();
                if (x > randomMove.x) {
                    blockArr[randomMove.x + 1][randomMove.y].removeWall();
                } else if (x < randomMove.x) {
                    blockArr[randomMove.x - 1][randomMove.y].removeWall();
                }
                if (y > randomMove.y) {
                    blockArr[randomMove.x][randomMove.y + 1].removeWall();
                } else if (y < randomMove.y) {
                    blockArr[randomMove.x][randomMove.y - 1].removeWall();
                }
                visited += 1;
                stack.add(randomMove);
                //recursively call until no more moves left
                createMazePath(visited, stack, randomPick, randomMove.x, randomMove.y);
            }
        }
    }
    //set player dead
    public void setPlayerDead(Point point) {
        this.blockArr[point.x][point.y].setPlayerDead();
    }
    //boolean isWall
    public boolean isWall(Point point) {
        return this.blockArr[point.x][point.y].isWall();
    }
    //set revealed
    public void setRevealed(Point point) {
        this.blockArr[point.x][point.y].setRevealed();
    }
    //boolean iscorner
    public boolean isCorner(Point point) {
        return this.blockArr[point.x][point.y].isCorner();
    }
    //set cheese
    public void setCheese(Point point) {
        this.blockArr[point.x][point.y].setCheese();
        this.cheesePoint = new Point(point);
    }
    //remove cheese
    public void removeCheese() {
        this.blockArr[cheesePoint.x][cheesePoint.y].unsetCheese();
        this.cheesePoint = new Point();
    }
}