package UI;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.security.Key;

import Game.Game;

/**
 * Controller class for GUI
 * implements keylistener class
 */
public class Controller extends JPanel implements KeyListener {
    Game game;

    //constructor
    public Controller(Game game) {
        this.game = game;
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);
    }

    //key event
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        boolean isValid = true;
        //user press up
        if (code == KeyEvent.VK_UP) {
            isValid = game.userInput('w');
        }
        //user press down
        if (code == KeyEvent.VK_DOWN) {
            isValid = game.userInput('s');
        }
        //user press right
        if (code == KeyEvent.VK_RIGHT) {
            isValid = game.userInput('d');
        }
        //user press left
        if (code == KeyEvent.VK_LEFT) {
            isValid = game.userInput('a');
        }
        //if move is invalid play sound
        if (!isValid) {
            File file = new File("src/sounds/invalid.wav");
            UI.playSound(file);
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }
}
