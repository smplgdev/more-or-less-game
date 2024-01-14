package logic;

public class GameEndStatus {
    Game game;
    public String message; // Message information about game status
    boolean isWin;
    GameEndStatus(Game game, String message) {
        this.game = game;
        int finalDifference = game.countGoalDifference();
        this.isWin = finalDifference == 0; // if sum == goal then win. otherwise — lose
        this.message = message + "\n\nFinal difference: " + finalDifference + "\n\n" + ((isWin) ?
                "YOU WIN!" : "You lost :(");
    }

    static GameEndStatus movesAreOver(Game game) {
        return new GameEndStatus(game, "Moves are over!");
    }

    static GameEndStatus overGoal(Game game) {
        return new GameEndStatus(game, "You're over the limit!");
    }

    static GameEndStatus noPossibleMoves(Game game) {
        return new GameEndStatus(game, "You don't have any possible moves!");
    }

    static GameEndStatus goalReached(Game game) {
        return new GameEndStatus(game, "You've reached your goal!");
    }
}
