package Game;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
/**
 * Cat class
 * <p>
 * acts like a "blind" cat and wanders maze
 * only back tracks steps if no moves left
 */
public class Cat {
    private Point lastLocation;
    private Point location;
    //Constructor
    public Cat(Point point) {
        this.lastLocation = point;
        location = point;
    }
    //Location setter
    public void setLocation(Point location) {
        this.location = location;
    }
    //Location getter
    public Point getLocation() {
        return location;
    }
    //Last Location Getter
    public Point getLastLocation() {
        return lastLocation;
    }
    public void move(Game thisGame) {
        Random random = new Random();
        Block map[][] = thisGame.getBlockArr();
        //save moves in array list
        ArrayList<Point> possibleMoves = new ArrayList<>();
        //checks surrounding blocks for walls and prevent back tracking
        if (!map[this.location.x - 1][this.location.y].isWall()) {
            if (!(lastLocation.x == this.location.x - 1 && lastLocation.y == this.location.y)) {
                possibleMoves.add(new Point(this.location.x - 1, this.location.y));
            }
        }
        if (!map[this.location.x + 1][this.location.y].isWall()) {
            if (!(lastLocation.x == this.location.x + 1 && lastLocation.y == this.location.y)) {
                possibleMoves.add(new Point(this.location.x + 1, this.location.y));
            }
        }
        if (!map[this.location.x][this.location.y - 1].isWall()) {
            if (!(lastLocation.x == this.location.x && lastLocation.y == this.location.y - 1)) {
                possibleMoves.add(new Point(this.location.x, this.location.y - 1));
            }
        }
        if (!map[this.location.x][this.location.y + 1].isWall()) {
            if (!(lastLocation.x == this.location.x && lastLocation.y == this.location.y + 1)) {
                possibleMoves.add(new Point(this.location.x, this.location.y + 1));
            }
        }
        //if no more moves, backtrack
        if (possibleMoves.size() == 0) {
            Point temp = lastLocation;
            lastLocation = location;
            location = temp;
        } else { //if there is valid move, do not backtrack, take next step from possibleMoves
            lastLocation = location;
            location = possibleMoves.get(random.nextInt(Integer.SIZE - 1) % possibleMoves.size());
        }
    }
}