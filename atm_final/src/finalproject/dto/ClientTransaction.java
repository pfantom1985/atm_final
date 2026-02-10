package finalproject.dto;

import java.time.LocalDateTime;

public class ClientTransaction {

    private String message;
    private LocalDateTime dateTime;

    public ClientTransaction() {
    }

    public ClientTransaction(String message, LocalDateTime dateTime) {
        this.message = message;
        this.dateTime = dateTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return dateTime + " | " + message;
    }
}
