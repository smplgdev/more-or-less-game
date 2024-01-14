package ui;

import logic.Game;

import javax.swing.*;
import java.awt.*;

public class UpperPanel extends JPanel {
    // This panel contains to Labels and methods to refresh them after each move
    Game game;
    private final JLabel labelMovesLeft;
    private String textMovesLeft() {
        return "Moves left: " + game.movesLeft;
    }
    UpperPanel(Game game) {
        this.game = game;
        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel labelGoalScore = new JLabel("Goal: " + this.game.GOAL);
        centerPanel.add(labelGoalScore);

        JPanel rightPanel = new JPanel();
        labelMovesLeft = new JLabel(textMovesLeft());
        rightPanel.add(labelMovesLeft);

        add(centerPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
    }

    void refresh() {
        updateMovesLeft();
        revalidate();
        repaint();
    }

    void updateMovesLeft() {
        labelMovesLeft.setText(textMovesLeft());
    }
}
