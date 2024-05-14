package fr.kata.tennis.api;

import fr.kata.tennis.domain.error.AlreadyGameFinished;
import fr.kata.tennis.domain.error.DomainError;
import fr.kata.tennis.domain.error.InvalidScore;
import fr.kata.tennis.domain.error.NotFoundPlayer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DomainErrorToRestError {

    public static RestError getRestErrorByDomainError(DomainError e) {
        return switch (e) {
            case NotFoundPlayer n ->
                    new RestError(HttpStatus.NOT_FOUND, new ExceptionCode("NOT_FOUND", e.getMessage()));
            case InvalidScore n ->
                    new RestError(HttpStatus.BAD_REQUEST, new ExceptionCode("INVALID_SCORE", e.getMessage()));
            case AlreadyGameFinished n ->
                    new RestError(HttpStatus.BAD_REQUEST, new ExceptionCode("ALREADY_FINISHED_GAME", e.getMessage()));
            default ->
                    new RestError(HttpStatus.INTERNAL_SERVER_ERROR, new ExceptionCode("INTERNAL_SERVER_ERROR", e.getMessage()));
        };
    }
}
