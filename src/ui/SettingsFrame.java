package ui;

import logic.Game;
import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsFrame extends JFrame {
    private static final String WINDOW_TITLE = "Settings";
    JSpinner fieldSizeSpinner;
    JSpinner goalSpinner;
    JCheckBox setGoalRandomlyCheckbox;
    JSpinner movesSpinner;
    JCheckBox setMovesRandomlyCheckbox;
    static String difficulty;
    public SettingsFrame() {
        // Constructor of the settings frame

        setTitle(WINDOW_TITLE);

        // Panel outerPanel, which includes all other setting panels and sets the window indentation for beauty
        JPanel outerPanel = new JPanel(new BorderLayout());
        int topMargin = 20;
        int leftMargin = 20;
        int bottomMargin = 20;
        int rightMargin = 20;
        // Set borders for beauty
        outerPanel.setBorder(BorderFactory.createEmptyBorder(topMargin, leftMargin, bottomMargin, rightMargin));

        // Add setting panels in outerPanel
        outerPanel.add(getFieldSizePanel(), BorderLayout.NORTH);
        outerPanel.add(getDifficultyPanel(), BorderLayout.CENTER);
        outerPanel.add(getStartGamePanel(), BorderLayout.SOUTH);

        // add outerPanel itself
        add(outerPanel);

        // Packing and showing frame
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        pack();
        Utils.centerFrameOnScreen(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JPanel getDifficultyPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        panel.add(new JLabel("Difficulty: "));

        final String[] DIFFICULTIES = {"Easy", "Medium", "Hard"};
        JComboBox<String> comboBox = new JComboBox<>(DIFFICULTIES);

        difficulty = DIFFICULTIES[1];
        comboBox.setSelectedItem(difficulty);

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulty = (String) comboBox.getSelectedItem();
            }
        });

        panel.add(comboBox);
        return panel;
    }

    private JPanel getFieldSizePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // First inner panel
        JPanel fieldSizePanel = new JPanel();
        fieldSizePanel.setLayout(new FlowLayout());

        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(10, 10, 20, 1);
        fieldSizeSpinner = new JSpinner(spinnerModel);

        fieldSizePanel.add(new JLabel("Field size (10 to 20): "));
        fieldSizePanel.add(fieldSizeSpinner);

        // Second inner panel
        JPanel goalPanel = new JPanel();
        goalPanel.setLayout(new FlowLayout());

        JLabel goalLabel = new JLabel("Set goal (1 to 1000):");
        goalSpinner = new JSpinner(new SpinnerNumberModel(50, 1, 1000, 1));
        setGoalRandomlyCheckbox = new JCheckBox("Set randomly");

        goalPanel.add(goalLabel);
        goalPanel.add(goalSpinner);
        goalPanel.add(setGoalRandomlyCheckbox);

        // Third inner panel
        JPanel movesPanel = new JPanel();
        movesPanel.setLayout(new FlowLayout());

        JLabel movesLabel = new JLabel("Set max. moves (1 to 400):");
        movesSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 20*20, 1));
        setMovesRandomlyCheckbox = new JCheckBox("Set randomly");

        movesPanel.add(movesLabel);
        movesPanel.add(movesSpinner);
        movesPanel.add(setMovesRandomlyCheckbox);

        panel.add(fieldSizePanel);
        panel.add(goalPanel);
        panel.add(movesPanel);

        return panel;
    }

    private JPanel getStartGamePanel() {
        JPanel panel = new JPanel();

        JButton button = new JButton("Start game");
        button.addActionListener(e -> {
            dispose();

            int goal = (int) goalSpinner.getValue();
            if (setGoalRandomlyCheckbox.isSelected()) {
                // Value is set to -1 so that the game understands that a random value should be selected
                goal = -1;
            }

            int moves = (int) movesSpinner.getValue();
            if (setMovesRandomlyCheckbox.isSelected()) {
                // Value is set to -1 so that the game understands that a random value should be selected
                moves = -1;
            }

            if (goal != -1 && moves != -1) {
                // If both goal and moves are set manually, then the difficulty is Custom.
                difficulty = "Custom";
            }

            // Initialize game
            Game game = new Game((int) fieldSizeSpinner.getValue(), goal, moves, difficulty);

            // Initialize a game frame
            GameFrame gameFrame = new GameFrame(game);
        });

        panel.add(button);
        return panel;
    }

}
