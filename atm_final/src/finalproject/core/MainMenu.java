package finalproject.core;

public enum MainMenu {

    REGISTER_CLIENT(1),
    INSERT_CARD_INTO_ATM(2),
    COMPLETE_ALL_OPERATIONS(3);

    private final int mainMenuSelection;

    MainMenu(int mainMenuSelection) {
        this.mainMenuSelection = mainMenuSelection;
    }

    public int getMainMenuSelection() {
        return mainMenuSelection;
    }

}
