package fr.kata.tennis.domain.model;

import fr.kata.tennis.domain.error.DomainError;
import io.vavr.control.Either;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.List;

@Getter
public class Game {

    private static final int WINNING_POINT = 4;
    private static final int TWO_POINTS_DIFFERENCE = 2;
    private final List<Player> players;
    @Setter
    private GameState gameState;

    public Game(Player playerA, Player playerB, GameState gameState) {
        this.players = List.of(playerA, playerB);
        this.gameState = gameState;
    }

    private boolean hasWinner() {
        return players.stream()
                .anyMatch(p -> p.getPoints() >= WINNING_POINT &&
                        Math.abs(players.getFirst().getPoints() - players.getLast().getPoints()) >= TWO_POINTS_DIFFERENCE);
    }

    private boolean isDeuce() {
        return players.stream().allMatch(player -> player.getPoints() == 3);
    }

    private boolean hasAdvantage() {
        return players.stream().allMatch(p -> p.getPoints() >= 3) &&
                Math.abs(players.getFirst().getPoints() - players.getLast().getPoints()) == 1;
    }

    private String getPlayerWithMaxPoint() {
        return players.stream()
                .max(Comparator.comparingInt(Player::getPoints))
                .map(Player::getName).get();
    }

    public Either<DomainError, String> getScore() {
        if (hasWinner()) {
            this.gameState = GameState.FINISHED;
            return Either.right("Player " + getPlayerWithMaxPoint() + " wins the game");
        }
        if (isDeuce()) return Either.right("Deuce");
        if (hasAdvantage()) return Either.right("Advantage " + getPlayerWithMaxPoint());
        return formatGameScore();
    }

    private Either<DomainError, String> formatGameScore() {
        var playersScore = players.stream()
                .map(Player::getFormattedPlayerScore).toList();
        if (playersScore.stream().anyMatch(Either::isLeft)) {
            return playersScore.getFirst();
        } else {
            return Either.right(String.format("Player A: %s / Player B: %s", playersScore.getFirst().get(), playersScore.getLast().get()));
        }
    }

}
