package ui;

import logic.Game;
import utils.Utils;

import javax.swing.*;

public class GameFrame extends JFrame {
    // Game Frame class
    static final String FRAME_TITLE = "More or Less, Less is More";
    Game game;
    UpperPanel upperPanel;
    ButtonsPanel buttonsPanel;
    CurrentSumPanel currentSumPanel;
    SaveFieldPanel saveFieldPanel;
    public GameFrame(Game game) {
        // Constructor for the game frame

        this.game = game; // this Frame is based on Game class instance
        upperPanel = new UpperPanel(game);
        buttonsPanel = new ButtonsPanel(game, this);
        currentSumPanel = new CurrentSumPanel(game);
        saveFieldPanel = new SaveFieldPanel(game);

        add(upperPanel);
        add(buttonsPanel);
        add(currentSumPanel);
        add(saveFieldPanel);

        setTitle(FRAME_TITLE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        pack();
        Utils.centerFrameOnScreen(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    void refreshFrame() {
        // This method is using for refreshing game panels after each turn.
        upperPanel.refresh();
        buttonsPanel.refresh();
        currentSumPanel.refresh();
    }
}
