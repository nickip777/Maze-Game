package Game;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Iterator;

/**
 * Game Class
 * handles game logic
 * determines score, lose, win, player actions, and how the cats move
 */
public class Game {
    //observer
    private List<GameObserver> observers = new ArrayList<GameObserver>();
    //constants
    private static final int MAX_NUM_ROW = 18;
    private static final int MAX_NUM_COL = 13;
    private static final int OFFSET = 1;
    private static final Point TOP_LEFT_CORNER = new Point(1, 1);
    private static final Point TOP_RIGHT_CORNER = new Point(1, 13);
    private static final Point BOTTOM_LEFT_CORNER = new Point(18, 1);
    private static final Point BOTTOM_RIGHT_CORNER = new Point(18, 13);
    //cats
    private Cat cat1;
    private Cat cat2;
    private Cat cat3;
    private Cat cat4;
    private Cat cat5;
    private Cat cat6;
    //maze
    private Maze maze;
    //player locations
    private Point playerLocation;
    private Point playerLastLocation;
    //num cheese
    private int numCheese;

    private boolean lose=false;

    public boolean isLose() {
        return lose;
    }

    public boolean isWin(){
        return won;
    }
    private boolean won = false;
    //CONSTRUCTOR
    public Game() {
        this.playerLocation = TOP_LEFT_CORNER;
        this.playerLastLocation = TOP_LEFT_CORNER;
        this.maze = new Maze();
        setCheese();
        setCats();
        revealBlocksAroundPlayer();
        this.numCheese = 0;
        notifyObservers();
    }

    //getter
    public Cat[] getCats(){
        Cat[] cats = new Cat[6];
        cats[0] = cat1;
        cats[1] = cat2;
        cats[2] = cat3;
        cats[3] = cat4;
        cats[4] = cat5;
        cats[5] = cat6;
        return cats;

    }
    //game logic using user input
    public boolean userInput(char c) {
        boolean isValid = true;
        switch (c) {
            case 'w':
                isValid = move(0, -1);
                break;
            case 'a':
                isValid = move(-1, 0);
                break;
            case 's':
                isValid = move(0, 1);
                break;
            case 'd':
                isValid = move(1, 0);
                break;
            case 'm':
                revealMap();
                return true;
        }
        moveCats();
        revealBlocksAroundPlayer();
        checkIfPlayerOnCheese();
        if(isLost()){
            lose = true;
        }
        if(isWon()){
            won = true;
        }
        notifyObservers();
        return isValid;
    }
    //check for valid move and move player
    private boolean move(int x, int y) {
        //check valid move from map
        Point point = new Point(this.playerLocation.x + x, this.playerLocation.y + y);
        if (maze.isWall(point)) {
            return false;
        } else {
            this.playerLastLocation = new Point(playerLocation);
            this.playerLocation.translate(x, y);
            return true;
        }

    }
    public Point getPlayerLocation() {
        return this.playerLocation;
    }
    //set cats in corners
    private void setCats() {
        this.cat1 = new Cat(TOP_RIGHT_CORNER);
        this.cat2 = new Cat(BOTTOM_LEFT_CORNER);
        this.cat3 = new Cat(BOTTOM_RIGHT_CORNER);
        this.cat4 = new Cat(TOP_RIGHT_CORNER);
        this.cat5 = new Cat(BOTTOM_LEFT_CORNER);
        this.cat6 = new Cat(BOTTOM_RIGHT_CORNER);
    }
    //move the cats
    private void moveCats() {
        this.cat1.move(this);
        this.cat2.move(this);
        this.cat3.move(this);
        this.cat4.move(this);
        this.cat5.move(this);
        this.cat6.move(this);
    }

    //get the state of the maze for display
    public Block[][] getBlockArr() {
        return this.maze.getBlockArr();
    }
    //function to reveal around player
    private void revealBlocksAroundPlayer() {
        for (int i = playerLocation.x - 1; i <= playerLocation.x + 1; i++) {
            for (int j = playerLocation.y - 1; j <= playerLocation.y + 1; j++) {
                this.getBlockArr()[i][j].setRevealed();
            }
        }
    }
    //reveal entire map
    private void revealMap() {
        for (int i = 1; i < MAX_NUM_ROW + 1; i++) {
            for (int j = 1; j < MAX_NUM_COL + 1; j++) {
                Point point = new Point(i, j);
                this.maze.setRevealed(point);
            }
        }
    }
    //num cheese getter
    public int getNumCheese() {
        return numCheese;
    }
    //check if player on cheese for winning or losing condition
    private void checkIfPlayerOnCheese() {
        if (playerLocation.equals(maze.cheesePoint)) {
            maze.removeCheese();
            setCheese();
        }
    }
    //place the cheese on the map
    private void setCheese() {
        //randomize int array
        ArrayList<Integer> row = new ArrayList<>();
        ArrayList<Integer> col = new ArrayList<>();
        for (int i = 0; i < MAX_NUM_ROW; i++) {
            row.add(i + OFFSET);
        }
        for (int i = 0; i < MAX_NUM_COL; i++) {
            col.add(i + OFFSET);
        }
        Collections.shuffle(row);
        Collections.shuffle(col);
        //place 1 cheese using randomized array
        int i = 0;
        while (i < col.size()) {
            Point point = new Point(row.get(i), col.get(i));
            if (!this.maze.isCorner(point) && !this.maze.isWall(point) && !this.playerLocation.equals(point)) {
                this.maze.setCheese(point);
                break;
            }
            i++;
        }
        this.numCheese++;
    }
    //check winning condition
    public boolean isWon() {
        return (getNumCheese() == 5);
    }
    //check losing condition
    public boolean isLost() {
        if (isPlayerDead(cat1)) return true;
        if (isPlayerDead(cat2)) return true;
        if (isPlayerDead(cat3)) return true;
        if (isPlayerDead(cat4)) return true;
        if (isPlayerDead(cat5)) return true;
        if (isPlayerDead(cat6)) return true;
        return false;
    }
    //check if player dead and set death location
    private boolean isPlayerDead(Cat cat) {
        if (playerLocation.equals(cat.getLocation())) {
            cat.setLocation(playerLocation);
            maze.setPlayerDead(playerLocation);
            return true;
        } else if (playerLastLocation.equals(cat.getLocation()) && cat.getLastLocation().equals(playerLocation)) {
            maze.setPlayerDead(playerLocation);
            cat.setLocation(playerLocation);
            return true;
        }
        return false;
    }

    /*
     * Functions to support being iterable.
     * ------------------------------------------------------
     *
    @Override
    public Iterator<Integer> iterator() {
        return Collections.unmodifiableList(list).iterator();
    }*/


    /*
	 * Functions to support being observable.
	 * ------------------------------------------------------
	 */
    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }
    private void notifyObservers() {
        for (GameObserver observer : observers) {
            observer.stateChanged();
        }
    }

}