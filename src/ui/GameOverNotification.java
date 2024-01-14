package ui;

import logic.GameEndStatus;

import javax.swing.*;

public class GameOverNotification extends JFrame {
    public GameOverNotification(GameEndStatus gameEndStatus) {
        // Shows message with a result of the game
        JOptionPane.showMessageDialog(this, gameEndStatus.message);
    }
}