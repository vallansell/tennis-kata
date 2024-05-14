package fr.kata.tennis.acceptance.steps;

import fr.kata.tennis.TennisApplication;
import fr.kata.tennis.domain.error.DomainError;
import fr.kata.tennis.domain.error.AlreadyGameFinished;
import fr.kata.tennis.domain.error.NotFoundPlayer;
import fr.kata.tennis.domain.model.Game;
import fr.kata.tennis.domain.model.GameState;
import fr.kata.tennis.domain.model.Player;
import fr.kata.tennis.domain.usecase.AddPoint;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.vavr.control.Either;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ContextConfiguration(classes = {TennisApplication.class})
public class AddPointSteps {

    private AddPoint addPoint;

    private Game game;
    private Either<DomainError, String> result;

    @Before
    public void setUp() {
        Player player1 = new Player("A", 0);
        Player player2 = new Player("B", 0);
        game = new Game(player1, player2, GameState.IN_PROGRESS);
        addPoint = new AddPoint(game);
    }

    @Given("a new tennis game has started with player A and player B")
    public void aNewTennisGameHasStartedWithPlayerAndPlayer() {
        setUp();
    }

    @When("the user adds a point to player {string}")
    public void theUserAddsAPointToPlayer(String playerName) {
        result = addPoint.execute(playerName);
    }

    @Then("the score should be {string} and the game should be {string}")
    public void theScoreShouldBeForPlayer(String expectedScore, String gameState) {
        assertTrue(result.isRight());
        assertEquals(expectedScore, result.get());
        assertThat(game.getGameState().toString()).isEqualTo(gameState);
    }

    @Then("the point addition should fail because the player {string} is unknown")
    public void thePointAdditionShouldFailedBecauseUnknownPlayer(String unknownPlayer) {
        assertTrue(result.isLeft());
        assertThat(result.getLeft() instanceof NotFoundPlayer).isTrue();
        String expectedMessage = String.format("The player name %s does not match any current players", unknownPlayer);
        assertThat(result.getLeft().getMessage()).isEqualTo(expectedMessage);
    }

    @Then("the point addition should fail because the game is finished")
    public void thePointAdditionShouldFailedBecauseGameIsAlreadyFinished() {
        assertTrue(result.isLeft());
        assertThat(result.getLeft() instanceof AlreadyGameFinished).isTrue();
        assertThat(result.getLeft().getMessage()).isEqualTo("Unable to make this action because the game is finished");
    }
}
