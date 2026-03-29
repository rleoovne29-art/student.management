package raisetech.student.management.controller.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import raisetech.student.management.domain.ErrorDetail;
import raisetech.student.management.exception.TestException;
import raisetech.student.management.response.ErrorResponse;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TestException.class)
    public ResponseEntity<ErrorResponse> handleTestException(TestException ex) {
        String field = ex.getField() != null ? ex.getField() : "global";
        ErrorDetail detail = new ErrorDetail(
                field,
                ex.getRejectedValue(),
                ex.getMessage()
        );

        List<ErrorDetail> details = new ArrayList<>();
        details.add(detail);

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Test error occurred",
                details
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
