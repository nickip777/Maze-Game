package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import Game.Game;
import Game.Block;
import Game.Maze;
import Game.Cat;
import Game.GameObserver;

/**
 * main ui for actual game
 * displays gridlayout with icons
 * updates using observers
 */
@SuppressWarnings("serial")
public class MazeUI extends JPanel {
    //initialize constants
    public static final int MAX_NUM_HEIGHT = 15;
    public static final int MAX_NUM_WIDTH = 20;
    public static final int SCALED_NUM = 45;
    Game game;

    //constructor
    public MazeUI(Game game) {
        super(new GridLayout());
        this.setLayout(new GridLayout(MAX_NUM_HEIGHT, MAX_NUM_WIDTH, 10, 10));
        this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        this.game = game;
        registerAsObserver();
        updateUI(game);
    }

    //update ui
    public void updateUI(Game game) {
        //clear grid
        this.removeAll();
        //get cats
        Cat[] cats = game.getCats();
        //get block array
        Block[][] blockArr = game.getBlockArr();
        for (int j = 0; j < MAX_NUM_HEIGHT; j++) {
            for (int i = 0; i < MAX_NUM_WIDTH; i++) {
                JLabel label = new JLabel();
                //if player dead, update icon
                if (blockArr[i][j].isPlayerDead()) {
                    ImageIcon image = new ImageIcon("src/images/dead.png");
                    label.setIcon(getScaleImageIcon(image, SCALED_NUM, SCALED_NUM));
                    this.add(label);
                    //if cat update icon
                } else if (i == cats[0].getLocation().x && j == cats[0].getLocation().y) {
                    ImageIcon image = new ImageIcon("src/images/cat1.png");
                    label.setIcon(getScaleImageIcon(image, SCALED_NUM, SCALED_NUM));
                    this.add(label);
                } else if (i == cats[1].getLocation().x && j == cats[1].getLocation().y) {
                    ImageIcon image = new ImageIcon("src/images/cat2.png");
                    label.setIcon(getScaleImageIcon(image, SCALED_NUM, SCALED_NUM));
                    this.add(label);
                } else if (i == cats[2].getLocation().x && j == cats[2].getLocation().y) {
                    ImageIcon image = new ImageIcon("src/images/cat3.png");
                    label.setIcon(getScaleImageIcon(image, SCALED_NUM, SCALED_NUM));
                    this.add(label);
                } else if (i == cats[3].getLocation().x && j == cats[3].getLocation().y) {
                    ImageIcon image = new ImageIcon("src/images/cat4.png");
                    label.setIcon(getScaleImageIcon(image, SCALED_NUM, SCALED_NUM));
                    this.add(label);
                } else if (i == cats[4].getLocation().x && j == cats[4].getLocation().y) {
                    ImageIcon image = new ImageIcon("src/images/cat5.png");
                    label.setIcon(getScaleImageIcon(image, SCALED_NUM, SCALED_NUM));
                    this.add(label);
                } else if (i == cats[5].getLocation().x && j == cats[5].getLocation().y) {
                    ImageIcon image = new ImageIcon("src/images/cat6.png");
                    label.setIcon(getScaleImageIcon(image, SCALED_NUM, SCALED_NUM));
                    this.add(label);
                    //if player, update icon
                } else if (i == game.getPlayerLocation().x && j == game.getPlayerLocation().y) {
                    ImageIcon image = new ImageIcon("src/images/user.png");
                    label.setIcon(getScaleImageIcon(image, SCALED_NUM, SCALED_NUM));
                    this.add(label);
                    //if cheese, update icon
                } else if (blockArr[i][j].isCheese()) {
                    ImageIcon image = new ImageIcon("src/images/cheese.png");
                    label.setIcon(getScaleImageIcon(image, SCALED_NUM, SCALED_NUM));
                    this.add(label);
                    //if revealed, update icon
                } else if (!blockArr[i][j].isRevealed()) {
                    ImageIcon image = new ImageIcon("src/images/block.png");
                    label.setIcon(getScaleImageIcon(image, SCALED_NUM, SCALED_NUM));
                    this.add(label);
                    //if wall, update icon
                } else if (blockArr[i][j].isWall()) {
                    ImageIcon image = new ImageIcon("src/images/wall.png");
                    label.setIcon(getScaleImageIcon(image, SCALED_NUM, SCALED_NUM));
                    this.add(label);
                } else {
                    //if blank, make blank
                    ImageIcon image = new ImageIcon("src/images/blank.png");
                    label.setIcon(getScaleImageIcon(image, SCALED_NUM, SCALED_NUM));
                    this.add(label);
                }
            }
        }
        //refresh gui
        revalidate();
        repaint();
    }

    //check if lost
    private void check() {
        //play sounds and open dialog box
        if (this.game.isLose()) {
            File file = new File("src/sounds/lose.wav");
            UI.playSound(file);
            JOptionPane.showMessageDialog(this,
                    "You Lose.");
            System.exit(0);

        }
        if (this.game.isWin()) {
            File file = new File("src/sounds/win.wav");
            UI.playSound(file);
            JOptionPane.showMessageDialog(this,
                    "You Win!");
            System.exit(0);
        }
    }

    //methods for scaling image
    static public ImageIcon getScaleImageIcon(ImageIcon icon, int width, int height) {
        return new ImageIcon(getScaledImage(icon.getImage(), width, height));
    }

    static private Image getScaledImage(Image srcImg, int width, int height) {
        BufferedImage resizedImg =
                new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, width, height, null);
        g2.dispose();
        return resizedImg;
    }

    //register observers and implement functions
    private void registerAsObserver() {
        game.addObserver(new GameObserver() {
            @Override
            public void stateChanged() {
                updateUI(game);
                check();
            }
        });
    }

}
