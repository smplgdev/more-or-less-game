package ui;

import logic.Game;

import javax.swing.*;
import java.awt.*;

public class CurrentSumPanel extends JPanel {
    // This panel contains panel with the current sum label
    // and methods to refresh it after each move
    Game game;
    private final JLabel labelSumOfNumbers;
    private String textSumOfNumbers() {
        return "Current sum: " + game.currentSum;
    }
    CurrentSumPanel(Game game) {
        this.game = game;
        setLayout(new FlowLayout());

        labelSumOfNumbers = new JLabel(textSumOfNumbers());
        add(BorderLayout.CENTER, labelSumOfNumbers);
    }

    void updateSumOfNumbersLabel() {
        labelSumOfNumbers.setText(textSumOfNumbers());
    }

    void refresh() {
        updateSumOfNumbersLabel();
    }
}
