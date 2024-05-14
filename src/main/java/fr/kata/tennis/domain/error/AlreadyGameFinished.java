package fr.kata.tennis.domain.error;

public class AlreadyGameFinished extends DomainError{
    public AlreadyGameFinished() {
        super("Unable to make this action because the game is finished");
    }
}
