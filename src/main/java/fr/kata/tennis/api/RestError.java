package fr.kata.tennis.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@Getter
public class RestError {
    private HttpStatus status;
    private ExceptionCode exceptionCode;
}