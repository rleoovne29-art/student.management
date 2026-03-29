package raisetech.student.management.controller.handler;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import raisetech.student.management.domain.ErrorDetail;
import raisetech.student.management.exception.TestException;
import raisetech.student.management.response.ErrorResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * アプリケーション全体の例外をハンドリングするクラスです。
 * PracticeException やバリデーション例外を捕捉し、
 * 統一されたエラーレスポンス形式でクライアントへ返却します。
 */
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

    /**
     * @RequestBody に対する @Valid のバリデーション失敗時に発生する例外をハンドリングします。
     * フィールド単位のエラーを ErrorDetail として収集し、
     * 統一形式のエラーレスポンスとして返却します。
     *
     * @param ex MethodArgumentNotValidException
     * @return バリデーションエラーのレスポンス
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

        List<ErrorDetail> details = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String field = error.getField();
            Object rejectedValue = error.getRejectedValue();
            String message = error.getDefaultMessage();

            details.add(new ErrorDetail(field, rejectedValue, message));
        });

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation error occurred",
                details
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * @PathVariable や @RequestParam に対するバリデーション失敗時に発生する例外をハンドリングします。
     * ConstraintViolation からフィールド名・不正値・メッセージを抽出し、
     * 統一形式のエラーレスポンスとして返却します。
     *
     * @param ex ConstraintViolationException
     * @return バリデーションエラーのレスポンス
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {

        List<ErrorDetail> details = new ArrayList<>();

        ex.getConstraintViolations().forEach(violation -> {
            String field = violation.getPropertyPath().toString();
            Object rejectedValue = violation.getInvalidValue();
            String message = violation.getMessage();

            details.add(new ErrorDetail(field, rejectedValue, message));
        });

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation error occurred",
                details
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }



}
