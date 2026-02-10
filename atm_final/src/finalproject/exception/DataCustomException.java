package finalproject.exception;

/**
 * Пользовательское исключение для ошибок валидации/выбора пункта меню.
 * Используем там, где хотим показать понятное сообщение пользователю и/или
 * пробросить причину (cause) при оборачивании другого исключения. [web:510]
 */
public class DataCustomException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public enum ErrorCode {
        INVALID_MENU_SELECTION,
        INVALID_INPUT,
        DATA_INTEGRITY,
        IO_ERROR,
        UNKNOWN
    }

    private final ErrorCode code;

    public DataCustomException(String message) {
        super(message);
        this.code = ErrorCode.UNKNOWN;
    }

    public DataCustomException(String message, Throwable cause) {
        super(message, cause);
        this.code = ErrorCode.UNKNOWN;
    }

    public DataCustomException(ErrorCode code, String message) {
        super(message);
        this.code = (code != null) ? code : ErrorCode.UNKNOWN;
    }

    public DataCustomException(ErrorCode code, String message, Throwable cause) {
        super(message, cause);
        this.code = (code != null) ? code : ErrorCode.UNKNOWN;
    }

    public ErrorCode getCode() {
        return code;
    }
}
