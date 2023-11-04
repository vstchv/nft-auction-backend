package nftauction.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(InputException.class)
  ResponseEntity<InputError> handleInputException(InputException exception) {
    HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    switch (exception.getErrorCode()) {
      case USERNAME_ALREADY_EXISTS:
        httpStatus = HttpStatus.BAD_REQUEST;
        break;
      case WRONG_PASSWORD,
          USERNAME_NOT_FOUND:
        httpStatus = HttpStatus.UNAUTHORIZED;
        break;
    }
    InputError inputError = new InputError(exception.getMessage(),
                                           exception.getErrorCode());
    return new ResponseEntity<>(inputError, httpStatus);
  }
}
