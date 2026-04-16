package raisetech.student.management.response;

import lombok.Getter;
import raisetech.student.management.domain.ErrorDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * API のエラーレスポンス全体を表す DTO です。
 * ステータスコード、外側メッセージ、エラー詳細のリストを保持します。
 * errors は null の場合、JSON に出力されません。
 */
@Getter
public class ErrorResponse {

    private int status;
    private String message;
    private List<ErrorDetail> errors;

    public ErrorResponse(int status, String message, List<ErrorDetail> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ErrorResponse(int status, String message, ErrorDetail error) {
        this.status = status;
        this.message = message;
        this.errors = new ArrayList<>();
        this.errors.add(error);
    }

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.errors = null;
    }

}
