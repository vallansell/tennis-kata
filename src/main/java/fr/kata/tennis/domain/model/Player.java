package fr.kata.tennis.domain.model;

import fr.kata.tennis.domain.error.DomainError;
import fr.kata.tennis.domain.error.InvalidScore;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class Player {
    @Setter
    String name;
    int points;

    public void scorePoint() {
        this.points++;
    }
    public void removePoint() {
        this.points--;
    }

    public Either<DomainError, String> getFormattedPlayerScore() {
        return switch (this.points) {
            case 0 -> Either.right("0");
            case 1 -> Either.right("15");
            case 2 -> Either.right("30");
            case 3 -> Either.right("40");
            default -> Either.left(new InvalidScore());
        };
    }
}
