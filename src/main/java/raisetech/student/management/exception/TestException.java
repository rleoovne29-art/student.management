package raisetech.student.management.exception;

/**
 * 業務ロジック上のエラーを表すカスタム例外です。
 * 任意のフィールド名と拒否された値を保持でき、
 * バリデーション以外のドメインエラーにも柔軟に対応できます。
 */
public class TestException extends RuntimeException{

    private final String field;
    private final Object rejectedValue;

    public TestException(String field, Object rejectedValue, String message) {
        super(message);
        this.field = field;
        this.rejectedValue = rejectedValue;
    }

    public String getField() {
        return field;
    }

    public Object getRejectedValue() {
        return rejectedValue;
    }

}
