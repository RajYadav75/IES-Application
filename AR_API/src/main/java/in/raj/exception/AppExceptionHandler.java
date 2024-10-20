package in.raj.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler(value = SSAWebException.class)
    public ResponseEntity<AppException> handleSsaWebEx(SSAWebException ex){
        AppException appEx = new AppException();
        appEx.setExCode("EX0001");
        appEx.setExDesc(ex.getMessage());
        return new ResponseEntity<AppException>(appEx, HttpStatus.INTERNAL_SERVER_ERROR);
    }    
}
