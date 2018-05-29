package UI;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import Game.Game;
/**
 * status bar at bottom updates user score
 */
@SuppressWarnings("serial")
public class Status extends JPanel {
    Game game;

    //constructor
    private String status;
    public Status(Game game){
        super(new BorderLayout());
        this.game = game;
        //set text and observers and place in layout
        status = "Collected 0 of 5 cheese";
        JTextArea text = new JTextArea(status);
        this.setLayout(new BorderLayout());
        this.add(text,BorderLayout.SOUTH);
        registerAsObserver();
        updateStats();
    }

    //register observer
    private void registerAsObserver() {
        game.addObserver(
                () -> updateStats());
    }

    //update stats
    private void updateStats() {
        status = "Collected " + this.game.getNumCheese() + " of 5 cheese";
        JTextArea text = new JTextArea(status);
        this.removeAll();
        this.add(text,BorderLayout.SOUTH);
        revalidate();
        repaint();
    }


}
