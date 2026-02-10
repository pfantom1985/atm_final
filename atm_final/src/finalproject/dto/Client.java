package finalproject.dto;

import finalproject.ui.DisplayInfo;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Client implements SafekeepingAccount {

    private static final int MAX_TRANSACTIONS = 5;

    private String firstName;
    private String lastName;
    private String cityOfResidence;
    private String phoneNumber;
    private String userCardNumber;
    private String password;
    private double balance;
    private LocalDateTime createdAt;
    private LinkedList<ClientTransaction> transactions = new LinkedList<>();

    public Client(String firstName,
                  String lastName,
                  String cityOfResidence,
                  String phoneNumber,
                  String password,
                  String userCardNumber,
                  double balance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cityOfResidence = cityOfResidence;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.userCardNumber = userCardNumber;
        this.balance = balance;
        this.createdAt = LocalDateTime.now();
    }

    public Client() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCityOfResidence() {
        return cityOfResidence;
    }

    public void setCityOfResidence(String cityOfResidence) {
        this.cityOfResidence = cityOfResidence;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserCardNumber() {
        return userCardNumber;
    }

    public void setUserCardNumber(String userCardNumber) {
        this.userCardNumber = userCardNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<ClientTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(LinkedList<ClientTransaction> transactions) {
        if (transactions == null) {
            this.transactions = new LinkedList<>();
        } else {
            this.transactions = transactions;
            trimTransactions();
        }
    }

    private void addTransaction(String message, LocalDateTime dateTime) {
        if (transactions == null) {
            transactions = new LinkedList<>();
        }
        transactions.addFirst(new ClientTransaction(message, dateTime));
        trimTransactions();
    }

    private void trimTransactions() {
        while (transactions.size() > MAX_TRANSACTIONS) {
            transactions.removeLast();
        }
    }

    @Override
    public void deposit(double clientCash, LocalDateTime dateTime) {
        balance += clientCash;

        String msg = "Счет пополнен на сумму: ( "
                     + NumberFormat.getCurrencyInstance().format(clientCash)
                     + " ). Баланс карты < "
                     + NumberFormat.getCurrencyInstance().format(balance)
                     + " >.";

        addTransaction(msg, dateTime);
    }

    @Override
    public void withdraw(double clientCash, LocalDateTime dateTime) {
        if (clientCash > balance) {
            System.out.println(DisplayInfo.RED + "Недостаточно средств на карт-счете!" + DisplayInfo.RESET);
            return;
        }

        balance -= clientCash;

        String msg = "С карт-счета списано: ( "
                     + NumberFormat.getCurrencyInstance().format(clientCash)
                     + " ). Баланс карты < "
                     + NumberFormat.getCurrencyInstance().format(balance)
                     + " >.";

        addTransaction(msg, dateTime);
    }

    /**
     * Сравнение клиентов считаем достаточным по userCardNumber (как ты предлагал). [web:360]
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(userCardNumber, client.userCardNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userCardNumber);
    }

    @Override
    public String toString() {
        return "Client{" +
               "firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", cityOfResidence='" + cityOfResidence + '\'' +
               ", phoneNumber='" + phoneNumber + '\'' +
               ", userCardNumber='" + userCardNumber + '\'' +
               ", balance=" + balance +
               ", createdAt=" + createdAt +
               ", transactions=" + transactions +
               '}';
    }
}
