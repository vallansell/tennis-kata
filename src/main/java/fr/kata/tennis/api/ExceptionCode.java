package fr.kata.tennis.api;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class ExceptionCode {
    private String code;
    private String message;
}
