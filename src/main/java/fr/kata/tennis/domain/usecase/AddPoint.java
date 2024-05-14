package fr.kata.tennis.domain.usecase;

import fr.kata.tennis.domain.error.DomainError;
import fr.kata.tennis.domain.error.AlreadyGameFinished;
import fr.kata.tennis.domain.error.NotFoundPlayer;
import fr.kata.tennis.domain.model.Game;
import fr.kata.tennis.domain.model.GameState;
import fr.kata.tennis.domain.model.Player;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AddPoint {

    private final Game game;
    private static final int ADVANTAGE_POINT = 4;

    public Either<DomainError, String> execute(String playerScoringPoint) {

        if (game.getGameState() == GameState.FINISHED) {
            return Either.left(new AlreadyGameFinished());
        }

        return game.getPlayers().stream()
                .filter(player -> player.getName().equals(playerScoringPoint))
                .findFirst()
                    .map(scoringPlayer -> {
                        resetAdvantageIfNecessary(playerScoringPoint);
                        scoringPlayer.scorePoint();
                        return game.getScore();
                    })
                .orElse(Either.left(new NotFoundPlayer(playerScoringPoint)));
    }

    private void resetAdvantageIfNecessary(String playerScoringPoint) {
        game.getPlayers().stream()
                .filter(playerNotScoringPoint -> !playerNotScoringPoint.getName().equals(playerScoringPoint) && playerNotScoringHasAdvantage(playerNotScoringPoint))
                .findFirst()
                .ifPresent(Player::removePoint);
    }

    private static boolean playerNotScoringHasAdvantage(Player playerNotScoringPoint) {
        return playerNotScoringPoint.getPoints() == ADVANTAGE_POINT;
    }
}
