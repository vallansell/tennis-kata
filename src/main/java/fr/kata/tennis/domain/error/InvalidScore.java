package fr.kata.tennis.domain.error;

public class InvalidScore extends DomainError{
    public InvalidScore() {
        super("The provided score is invalid");
    }
}
