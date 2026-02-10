package finalproject.core;

public enum ClientMenu {

    REFILL_CARD_ACCOUNT(1),
    CASH_WITHDRAWAL(2),
    TRANSFER_FROM_CARD_TO_CARD(3),
    BALANCE_CHECK(4),
    MINI_STATEMENT(5),
    CLIENT_INFORMATION(6),
    ACCOUNT_CREATED_AT(7),
    RETURN_CARD(8);

    private final int accountCardMenuSelection;

    ClientMenu(int accountCardMenuSelection) {
        this.accountCardMenuSelection = accountCardMenuSelection;
    }

    public int getAccountCardMenuSelection() {
        return accountCardMenuSelection;
    }
}
