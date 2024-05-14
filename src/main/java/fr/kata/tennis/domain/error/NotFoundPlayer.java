package fr.kata.tennis.domain.error;

public class NotFoundPlayer extends DomainError{
    public NotFoundPlayer(String player) {
        super(String.format("The player name %s does not match any current players", player));
    }
}
