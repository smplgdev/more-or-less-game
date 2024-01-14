package ui;

import logic.Game;
import logic.NumField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SaveFieldPanel extends JPanel {
    // Panel that contains "New game", "Save game" and "Load game" buttons
    Game game;
    final String GAME_SAVES_PATH = "game-saves";
    SaveButtonListener saveButtonListener;
    LoadButtonListener loadButtonListener;
    NewGameButtonListener newGameButtonListener;

    public SaveFieldPanel(Game game) {
        this.game = game;

        setLayout(new FlowLayout());

        JButton newGameButton = new JButton("New game");
        JButton saveFieldButton = new JButton("Save game");
        JButton loadFieldButton = new JButton("Load game");

        newGameButtonListener = new NewGameButtonListener();
        newGameButton.addActionListener(newGameButtonListener);

        saveButtonListener = new SaveButtonListener();
        saveFieldButton.addActionListener(saveButtonListener);

        loadButtonListener = new LoadButtonListener();
        loadFieldButton.addActionListener(loadButtonListener);

        add(newGameButton);
        add(saveFieldButton);
        add(loadFieldButton);
        setVisible(true);
    }

    static class NewGameButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            closeParentFrame(e);
            SettingsFrame settingsFrame = new SettingsFrame();
        }
    }

    class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // "Save game" button is pressed
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH-mm-ss");
            String formattedDateTime = now.format(formatter);
            String fileName = GAME_SAVES_PATH + "/" + formattedDateTime + ".txt";
            try {
                FileWriter writer = getFileWriter(fileName);
                writer.flush();
                JOptionPane.showMessageDialog(null, String.format("A game has been wrote to the file %s", fileName));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        }
    }

    class LoadButtonListener implements ActionListener {
        // "Load game" button is pressed
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(SaveFieldPanel.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getPath();
                File file = new File(filePath);
                try {
                    FileReader fr = new FileReader(file);
                    BufferedReader br = new BufferedReader(fr);

                    int goal = -1;
                    int moves = -1;
                    int fieldSize = -1;
                    NumField[][] board = new NumField[0][0];

                    int goalLine = 0;
                    int movesLine = 1;
                    int fieldStartLine = 3;

                    int currentLine = 0;
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (currentLine == goalLine)
                            goal = Integer.parseInt(line);
                        if (currentLine == movesLine)
                            moves = Integer.parseInt(line);
                        if (currentLine == fieldStartLine) {
                            fieldSize = line.split(" ").length;
                            board = new NumField[fieldSize][fieldSize];
                            String row;
                            while ((row = br.readLine()) != null) {
                                for (int i = 0; i < fieldSize; i++) {
                                    String[] values = row.trim().split(" ");
                                    for (int j = 0; j < fieldSize; j++) {
                                        board[i][j] = new NumField(Integer.parseInt(values[j]));
                                    }
                                }
                            }
                        }
                        currentLine++;
                    }

                    Game game = new Game(fieldSize, goal, moves, board);
                    GameFrame gameFrame = new GameFrame(game);

                    br.close();
                    fr.close();
                } catch (IOException error) {
                    error.printStackTrace();
                }
            }
            closeParentFrame(e);
        }
    }

    private static void closeParentFrame(ActionEvent e) {
        // close the previous game window
        if (e.getSource() instanceof JButton) {
            // get the parent frame of the button
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor((JButton) e.getSource());

            // dispose the frame
            if (parentFrame != null) {
                parentFrame.dispose();
            }
        }
    }

    private FileWriter getFileWriter(String fileName) throws IOException {
        FileWriter writer = new FileWriter(fileName);
        writer.write(game.GOAL + "\n");
        writer.write(game.MOVES + "\n\n");
        for (int i = 0; i < game.FIELD_SIZE; i++) {
            for (int j = 0; j < game.FIELD_SIZE; j++) {
                NumField numField = game.board.values[i][j];
                writer.write(numField.getValue() + " ");
            }
            writer.write("\n");
        }
        return writer;
    }

}
