package logic;

import java.util.Random;

public class Game {
    public final int FIELD_SIZE;
    public final int GOAL;
    public final int MOVES;
    public final String DIFFICULTY;
    public int previous;
    public int current;
    public Board board;
    public int movesLeft;
    public int currentSum;

    public Game(int fieldSize, int goal, int moves, String difficulty) {
        // Constructor for the new game
        this.FIELD_SIZE = fieldSize;
        this.board = new Board(this, fieldSize);
        this.DIFFICULTY = difficulty;

        // Set a random goal if it is not set by user manually
        this.GOAL = (goal == -1) ? setRandomGoal() : goal;

        // Set a random max moves value if it is not set by user manually
        this.MOVES = (moves == -1) ? setRandomMaxMoves() : moves;
        this.movesLeft = MOVES;
    }

    public Game(int fieldSize, int goal, int moves, NumField[][] values) {
        // This constructor is used when the game was loaded from the file
        this.FIELD_SIZE = fieldSize;
        this.board = new Board(this, fieldSize, values);
        this.DIFFICULTY = "Manual";
        this.GOAL = goal;
        this.MOVES = moves;
        this.movesLeft = MOVES;
    }

    public GameEndStatus getGameFinishStatus() {
        // Check if game was finished and return GameEndStatus if so.
        if (movesLeft == 0) {
            return GameEndStatus.movesAreOver(this);
        }
        if (currentSum > GOAL) {
            return GameEndStatus.overGoal(this);
        }
        if (!board.hasActiveFields()){
            return GameEndStatus.noPossibleMoves(this);
        }
        if (currentSum == GOAL) {
            return GameEndStatus.goalReached(this);
        }
        return null;
    }

    private int setRandomGoal() {
        // Simple algorithm for random goal
        Random r = new Random();
        int multiplier = switch (DIFFICULTY) {
            case ("Easy") -> r.nextInt(2, 9+1);
            case ("Medium") -> r.nextInt(10, 15+1);
            case ("Hard") -> r.nextInt(15, 30+1);
            default -> 4;
        };
        int randomGoal = FIELD_SIZE * multiplier + r.nextInt(1, 10);
        if (randomGoal > board.SUM_OF_VALUES) {
            return setRandomGoal();
        }
        return randomGoal;
    }

    private int setRandomMaxMoves() {
        // Simple algorithm for max moves
        Random r = new Random();
        int multiplier = switch (DIFFICULTY) {
            case ("Easy") -> r.nextInt(3, 4+1);
            case ("Medium") -> r.nextInt(4, 6+1);
            case ("Hard") -> r.nextInt(6, 7+1);
            default -> 1;
        };
        return GOAL / multiplier;
    }

    public int countGoalDifference() {
        return Math.abs(GOAL - currentSum);
    }
}
