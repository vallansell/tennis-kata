package fr.kata.tennis.api.requestModel.addPoint;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddPointRequest {
    @NotEmpty(message = "Player name must not be empty")
    private String player;
}
