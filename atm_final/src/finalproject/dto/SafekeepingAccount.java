package finalproject.dto;

import java.time.LocalDateTime;

public interface SafekeepingAccount {

    void deposit(double clientCash, LocalDateTime dateTime);

    void withdraw(double clientCash, LocalDateTime dateTime);

}
