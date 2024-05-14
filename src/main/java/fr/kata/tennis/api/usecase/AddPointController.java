package fr.kata.tennis.api.usecase;

import fr.kata.tennis.api.RestError;
import fr.kata.tennis.api.requestModel.addPoint.AddPointRequest;
import fr.kata.tennis.domain.error.DomainError;
import fr.kata.tennis.domain.usecase.AddPoint;
import io.vavr.control.Either;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static fr.kata.tennis.api.DomainErrorToRestError.getRestErrorByDomainError;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/api/v1/game/point", produces = {APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class AddPointController {

    private final AddPoint addPoint;

    @PostMapping
    public ResponseEntity<Object> addPoint(@RequestBody @Valid AddPointRequest request) {
        Either<DomainError, String> result = addPoint.execute(request.getPlayer());
        if (result.isLeft()) {
            RestError restError = getRestErrorByDomainError(result.getLeft());
            return new ResponseEntity<>(restError, restError.getStatus());
        } else {
            return new ResponseEntity<>(result.get(), HttpStatus.OK);
        }
    }
}
