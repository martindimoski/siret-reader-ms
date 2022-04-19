package com.siretreader.ms.exceptions.handlers;

import com.siretreader.ms.common.dto.ApiError;
import com.siretreader.ms.exceptions.NotFoundException;
import com.siretreader.ms.exceptions.SireneServerDownException;
import com.siretreader.ms.exceptions.SireneTooManyRequestsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    private ResponseEntity<?> serverError(Exception ex) {
        LOGGER.error(ex.getMessage(), ex);
        return new ResponseEntity<>(createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<?> notFoundException(NotFoundException ex) {
        LOGGER.error(ex.getMessage());
        return new ResponseEntity<>(
                createErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SireneServerDownException.class)
    private ResponseEntity<?> sireneServerDownExceptionException(SireneServerDownException ex) {
        LOGGER.error(ex.getMessage());
        return new ResponseEntity<>(
                createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SireneTooManyRequestsException.class)
    private ResponseEntity<?> sireneTooManyRequestsExceptionException(SireneTooManyRequestsException ex) {
        LOGGER.error(ex.getMessage());
        return new ResponseEntity<>(
                createErrorResponse(HttpStatus.TOO_MANY_REQUESTS.value(), ex.getMessage()),
                HttpStatus.TOO_MANY_REQUESTS);
    }

    private ApiError createErrorResponse(int status, String message) {
        return createErrorResponse(status, message, null);
    }

    private ApiError createErrorResponse(int status, String message, Integer errorCode) {
        return ApiError.builder()
                .withStatus(status)
                .withMessage(message)
                .withError(errorCode)
                .build();
    }
}
