package fr.kata.tennis.integration.api.usecase.addpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.kata.tennis.api.requestModel.addPoint.AddPointRequest;
import fr.kata.tennis.api.usecase.AddPointController;
import fr.kata.tennis.domain.error.AlreadyGameFinished;
import fr.kata.tennis.domain.error.InvalidScore;
import fr.kata.tennis.domain.error.NotFoundPlayer;
import fr.kata.tennis.domain.usecase.AddPoint;
import io.vavr.control.Either;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Tag("integration")
@Tag("restApi")
@WebMvcTest(AddPointController.class)
@DisplayName("Add point endpoint should")
public class AddPointControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AddPoint addPoint;

    private static final String ADD_POINT_ENDPOINT = "/api/v1/game/point";

    @Test
    @DisplayName("should return OK when a point is added to a player")
    void shouldReturnOKWhenAPointIsAddedToAPlayer() throws Exception {
        String player = "A";
        AddPointRequest request = new AddPointRequest(player);
        when(addPoint.execute(player)).thenReturn(Either.right("Player A: 15 / Player B: 0"));

        mvc.perform(
                        MockMvcRequestBuilders.post(ADD_POINT_ENDPOINT)
                                .content(new ObjectMapper().writeValueAsString(request))
                                .contentType(APPLICATION_JSON_VALUE)
                                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Player A: 15 / Player B: 0"));
    }

    @Test
    @DisplayName("should return NOT FOUND when a point is added to an unknown player")
    void shouldReturnNOTFOUNDWhenAPointIsAddedToAnUnKnownPlayer() throws Exception {
        String player = "UNKNOWN_PLAYER";
        AddPointRequest request = new AddPointRequest(player);
        when(addPoint.execute(player)).thenReturn(Either.left(new NotFoundPlayer(player)));

        mvc.perform(
                        MockMvcRequestBuilders.post(ADD_POINT_ENDPOINT)
                                .content(new ObjectMapper().writeValueAsString(request))
                                .contentType(APPLICATION_JSON_VALUE)
                                .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.exceptionCode.code").value("NOT_FOUND"))
                .andExpect(jsonPath("$.exceptionCode.message").value("The player name UNKNOWN_PLAYER does not match any current players"));
    }

    @Test
    @DisplayName("should return BAD REQUEST when a score is invalid")
    void shouldReturnBADREQUESTWhenAScoreIsInvalid() throws Exception {
        String player = "UNKNOWN_PLAYER";
        AddPointRequest request = new AddPointRequest(player);
        when(addPoint.execute(player)).thenReturn(Either.left(new InvalidScore()));

        mvc.perform(
                        MockMvcRequestBuilders.post(ADD_POINT_ENDPOINT)
                                .content(new ObjectMapper().writeValueAsString(request))
                                .contentType(APPLICATION_JSON_VALUE)
                                .accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exceptionCode.code").value("INVALID_SCORE"))
                .andExpect(jsonPath("$.exceptionCode.message").value("The provided score is invalid"));
    }

    @Test
    @DisplayName("should return BAD REQUEST when a point is added to an already finihed game")
    void shouldReturnBADREQUESTWhenAPointIsAddedToAnAlreadyFinishedGame() throws Exception {
        String player = "UNKNOWN_PLAYER";
        AddPointRequest request = new AddPointRequest(player);
        when(addPoint.execute(player)).thenReturn(Either.left(new AlreadyGameFinished()));

        mvc.perform(
                        MockMvcRequestBuilders.post(ADD_POINT_ENDPOINT)
                                .content(new ObjectMapper().writeValueAsString(request))
                                .contentType(APPLICATION_JSON_VALUE)
                                .accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.exceptionCode.code").value("ALREADY_FINISHED_GAME"))
                .andExpect(jsonPath("$.exceptionCode.message").value("Unable to make this action because the game is finished"));
    }
}
