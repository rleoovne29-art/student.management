package raisetech.student.management.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

/**
 * 単一のエラー情報を表す DTO です。
 * フィールド名、拒否された値、エラーメッセージを保持します。
 * rejectedValue が null の場合は JSON に出力されません。
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDetail {

    private String field;
    private Object rejectedValue;
    private String message;

    public ErrorDetail(String field, Object rejectedValue, String message) {
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.message = message;
    }

}
