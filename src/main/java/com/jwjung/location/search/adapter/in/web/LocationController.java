package com.jwjung.location.search.adapter.in.web;

import com.jwjung.location.search.adapter.in.web.dto.LocationResultsDTO;
import com.jwjung.location.search.application.port.in.LocationSearchUseCase;
import com.jwjung.location.search.application.port.in.SearchCommand;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotEmpty;

@RestController
@Validated
@RequiredArgsConstructor
public class LocationController {
    private final LocationSearchUseCase locationSearchUseCase;

    @GetMapping("/v1/locations")
    public LocationResultsDTO getLocations(@RequestParam("query") @NotEmpty String query) {

        return LocationResultsDTO.of(locationSearchUseCase.getLocationItems(new SearchCommand(query)));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @Getter
    static class ErrorMessage {
        private final HttpStatus status;
        private final String message;

        public ErrorMessage(String message, HttpStatus status) {
            this.status = status;
            this.message = message;
        }
    }
}
