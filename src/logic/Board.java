package logic;

import java.util.ArrayList;
import java.util.List;

public class Board {
    Game game;
    public NumField[][] values; // 2D array of NumFields
    public final int SUM_OF_VALUES; // The sum of all values on the field
    Board(Game game, int fieldSize) {
        // Constructor for the board of game, which is a class that contains field array and methods that make game playable
        this.game = game;
        int tempSum = 0;
        values = new NumField[fieldSize][fieldSize];
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                NumField number = new NumField();
                values[i][j] = number;
                tempSum += number.getValue();
            }
        }

        SUM_OF_VALUES = tempSum;
    }

    Board(Game game, int fieldSize, NumField[][] values) {
        this.game = game;
        int tempSum = 0;
        this.values = values;
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                NumField number = new NumField();
                values[i][j] = number;
                tempSum += number.getValue();
            }
        }
        SUM_OF_VALUES = tempSum;
    }

    public List<Integer> getNumberDivisibleRows(int number){
        List<Integer> activePositions = new ArrayList<>();
        if (number == 0)
            return null;
        for (int i = number; i <= game.FIELD_SIZE; i++) {
            if (i % number == 0) {
                activePositions.add(i);
            }
        }
        return activePositions;
    }

    public void updateFields() {
        List<Integer> divisiblePrevious = this.getNumberDivisibleRows(game.previous);
        List<Integer> divisibleCurrent = this.getNumberDivisibleRows(game.current);
        if (divisiblePrevious == null) {
            for (int i = 0; i < game.FIELD_SIZE; i++) {
                for (int j = 0; j < game.FIELD_SIZE; j++) {
                    NumField numField = values[i][j];
                    numField.isActive =
                            (divisibleCurrent.contains(i+1) && divisibleCurrent.contains(j+1))
                            && !numField.isPressed;
                    }
                }
            }
        else {
            for (int i = 0; i < game.FIELD_SIZE; i++) {
                for (int j = 0; j < game.FIELD_SIZE; j++) {
                    NumField numField = values[i][j];
                    numField.isActive =
                            (
                                    (divisibleCurrent.contains(i+1) && divisiblePrevious.contains(i+1))
                                    || (divisibleCurrent.contains(i+1) && divisiblePrevious.contains(j+1))
                                    || (divisibleCurrent.contains(j+1) && divisiblePrevious.contains(i+1))
                                    || (divisibleCurrent.contains(j+1) && divisiblePrevious.contains(j+1))
                            )
                            && !numField.isPressed;
                }
            }
        }
    }

    public boolean hasActiveFields() {
        for (int i = 0; i < game.FIELD_SIZE; i++) {
            for (int j = 0; j < game.FIELD_SIZE; j++) {
                NumField numField = values[i][j];
                if (
                        numField.isActive
                ) {
                    return true;
                }
            }
        }
        return false;
    }
}
