package raisetech.student.management.exception;


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
