package Game;
/**
 * Block class for each "cell" in the maze
 * has booleans for if block is a wall, corner, if its revealed,
 * or if the player is dead in the current location
 * or if current location contains a cheese
 */
public class Block {
    private boolean isWall;
    private boolean isRevealed;
    private boolean isCorner;
    private boolean isPlayerDead;
    private boolean isCheese;
    //constructor
    public Block() {
        this.isWall = false;
        this.isRevealed = false;
        this.isCorner = false;
        this.isPlayerDead = false;
    }
    //set player dead location
    public void setPlayerDead() {
        this.isPlayerDead = true;
    }
    //boolean for if player is dead
    public boolean isPlayerDead() {
        return this.isPlayerDead;
    }
    //if block is corner
    public boolean isCorner() {
        return isCorner;
    }
    //if block is cheese
    public boolean isCheese() {
        return this.isCheese;
    }
    //place cheese in block
    public void setCheese() {
        this.isCheese = true;
        this.isRevealed = true;
    }
    //remove cheese
    public void unsetCheese() {
        this.isCheese = false;
        this.isRevealed = true;
    }
    //set wall in block
    public void setWall() {
        this.isWall = true;
    }
    //remove a wall
    public void removeWall() {
        this.isWall = false;
    }
    //return if block is a wall
    public boolean isWall() {
        return this.isWall;
    }
    //make block revealed
    public void setRevealed() {
        this.isRevealed = true;
    }
    //boolean for if block is revealed
    public boolean isRevealed() {
        return this.isRevealed;
    }
}