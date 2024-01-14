package ui;

import logic.Board;
import logic.Game;
import logic.GameEndStatus;
import logic.NumField;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonsPanel extends JPanel {
    NumberButtonsListener listener;
    Game game;
    GameFrame gameFrame;
    Board board;
    JButton[][] buttons;
    static final String INACTIVE_BUTTON_COLOR = "#5388C9";
    static final String ACTIVE_BUTTON_COLOR = "#A5D5FA";
    static final int BUTTONS_SIZE = 50;

    ButtonsPanel(Game game, GameFrame gameFrame) {
        this.game = game;
        this.gameFrame = gameFrame;
        buttons = new JButton[game.FIELD_SIZE][game.FIELD_SIZE];
        board = this.game.board;
        listener = new NumberButtonsListener();
        refresh();
    }

    void refresh() {
        removeAll();
        refreshGameField();
        revalidate();
        repaint();
    }

    void refreshGameField() {
        setLayout(new GridLayout(game.FIELD_SIZE, game.FIELD_SIZE));
        for (int i = 0; i < game.FIELD_SIZE; i++) {
            for (int j = 0; j < game.FIELD_SIZE; j++) {
                JButton button = new JButton();
                NumField numField = board.values[i][j];
                setButtonProperties(button, numField);
                buttons[i][j] = button;
                add(button);
            }
        }
    }

    private void setButtonProperties(JButton button, NumField numField){
        if (numField.isActive && !numField.isPressed) {
            button.addActionListener(listener);
            button.setBackground(Color.decode(ACTIVE_BUTTON_COLOR));
        }
        else if (numField.isPressed) {
            button.setEnabled(false);
        }
        else {
            button.setBackground(Color.WHITE);
        }
        button.setText(String.valueOf(numField.getValue()));
        button.setOpaque(true);
        button.setBorder(new LineBorder(Color.decode(INACTIVE_BUTTON_COLOR), 1));
        button.setPreferredSize(new Dimension(BUTTONS_SIZE, BUTTONS_SIZE));
    }

    private void processPressedButtonField(NumField numField) {
        int buttonValue = numField.getValue();
        game.previous = game.current;
        game.current = buttonValue;
        game.currentSum += buttonValue;
        game.movesLeft--;
        numField.isPressed = true;
    }

    class NumberButtonsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < game.FIELD_SIZE; i++) {
                for (int j = 0; j < game.FIELD_SIZE; j++) {
                    JButton button = buttons[i][j];
                    if (button == e.getSource()) {
                        NumField pressedNumField = board.values[i][j];
                        processPressedButtonField(pressedNumField);
                    }
                }
            }

            board.updateFields();

            // After each turn the field should be updated
            gameFrame.refreshFrame();
            GameEndStatus gameFinished = game.getGameFinishStatus();
            if (gameFinished != null) {
                new GameOverNotification(gameFinished);
            }
        }
    }
}
