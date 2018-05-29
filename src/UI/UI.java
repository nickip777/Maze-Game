package UI;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import Game.Game;
import Game.Block;

import javax.sound.sampled.*;
import javax.swing.*;

/**
 * User Interface for mouse and cat maze
 * prompts user inputs, error checks and displays maze
 */
public class UI {
    public static void main(String[] args){
        Game game = new Game();

        //initilize frame
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());

        //set panels
        Status status = new Status(game);
        MazeUI userInterface = new MazeUI(game);
        Controller controller = new Controller(game);

        //add panels and controller to frame
        frame.addKeyListener(controller);
        frame.setFocusable(true);
        frame.add(userInterface,BorderLayout.CENTER);
        frame.add(status, BorderLayout.SOUTH);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    //play sound file
    static public void playSound(File sound) {
        try {
            AudioInputStream audioInputStream =
                    AudioSystem.getAudioInputStream(sound);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

}