package raisetech.student.management.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

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
