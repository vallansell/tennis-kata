package fr.kata.tennis;

import fr.kata.tennis.domain.model.Game;
import fr.kata.tennis.domain.model.GameState;
import fr.kata.tennis.domain.model.Player;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TennisApplication {

	public static void main(String[] args) {
		SpringApplication.run(TennisApplication.class, args);
	}

	@Bean
	public Game game() {
		Player playerA = new Player("A", 0);
		Player playerB = new Player("B", 0);
		return new Game(playerA, playerB, GameState.IN_PROGRESS);
	}

}
