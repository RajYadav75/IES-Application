package in.raj.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<AppException> handleException(String exMsg){
        AppException ex = new AppException();
        ex.setExCode("EX0003");
        ex.setExDesc(exMsg);
        ex.setExDate(LocalDateTime.now());

        return new ResponseEntity<AppException>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
