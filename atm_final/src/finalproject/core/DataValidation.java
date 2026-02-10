package finalproject.core;

import finalproject.dto.DataClientCreation;
import finalproject.exception.DataCustomException;
import finalproject.ui.DisplayInfo;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataValidation {

    private static final Logger LOGGER = Logger.getLogger(DataValidation.class.getName());

    public static final String EXIT = "Exit";

    public static final String LETTER_REGEX = "^[A-Z][A-Za-z]{1,20}$";
    public static final String PHONE_REGEX = "^\\d{7}$";
    public static final String CARD_NUMBER_REGEX = "^\\d{8}$";
    public static final String PASSWORD_REGEX = "^\\d{4}$";

    private static final int MAIN_MENU_MAX = 3;
    private static final int CLIENT_MENU_MAX = 8;

    private final DataClientCreation dataClientCreation;
    private final DisplayInfo displayInfo;
    private final Scanner inputData;

    public DataValidation(DataClientCreation dataClientCreation) {
        if (dataClientCreation == null) {
            throw new IllegalArgumentException("dataClientCreation must not be null");
        }
        this.dataClientCreation = dataClientCreation;
        this.displayInfo = new DisplayInfo();
        this.inputData = new Scanner(System.in);
    }

    /**
     * @deprecated Не использовать. Нужен только для обратной совместимости/тестов.
     *             В рабочем коде создаём DataValidation через конструктор с DataClientCreation.
     */
    @Deprecated(since = "2026-02", forRemoval = false)
    public DataValidation() {
        this(new DataClientCreation());
        LOGGER.warning("Использован устаревший конструктор DataValidation(); используйте DataValidation(DataClientCreation).");
    }

    public String getUserInputItem() {
        displayInfo.atmMainMenu();
        int firstMenuItem;

        while (true) {
            displayInfo.selectMenuOperation();
            try {
                firstMenuItem = Integer.parseInt(inputData.nextLine());

                if (firstMenuItem > MAIN_MENU_MAX || firstMenuItem < 1) {
                    throw new DataCustomException(
                            DataCustomException.ErrorCode.INVALID_MENU_SELECTION,
                            DisplayInfo.YELLOW
                            + "*****   Ошибка выбора операции! Неверно указан номер операции!    ********   *****"
                            + DisplayInfo.RESET
                    );
                }

                break;
            } catch (DataCustomException e) {
                LOGGER.log(Level.WARNING, "Некорректный выбор пункта главного меню: {0}", e.getMessage());
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                LOGGER.log(Level.FINE, "Ввод не является числом (главное меню).", e);
                displayInfo.isNotNumber();
            }
        }
        return String.valueOf(firstMenuItem - 1);
    }

    public String getUserInputMenu() {
        int secondMenuItem;

        while (true) {
            displayInfo.selectMenuOperation();
            try {
                secondMenuItem = Integer.parseInt(inputData.nextLine());

                if (secondMenuItem > CLIENT_MENU_MAX || secondMenuItem < 1) {
                    throw new DataCustomException(
                            DataCustomException.ErrorCode.INVALID_MENU_SELECTION,
                            DisplayInfo.YELLOW
                            + "*****   Неверно указан номер операции!\t"
                            + DisplayInfo.RESET
                    );
                }

                break;
            } catch (DataCustomException e) {
                LOGGER.log(Level.WARNING, "Некорректный выбор пункта меню клиента: {0}", e.getMessage());
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                LOGGER.log(Level.FINE, "Ввод не является числом (меню клиента).", e);
                displayInfo.isNotNumber();
            }
        }
        return String.valueOf(secondMenuItem - 1);
    }

    public String registrationUserFirstName() {
        displayInfo.enterFirstNameAndStart();
        String firstName = inputData.nextLine();

        if (firstName.equals(EXIT)) {
            return EXIT;
        }

        while (!firstName.matches(LETTER_REGEX)) {
            displayInfo.inputError();
            displayInfo.enterFirstName();
            firstName = inputData.nextLine();
            if (firstName.equals(EXIT)) {
                return EXIT;
            }
        }
        return firstName;
    }

    public String registrationUserLastName() {
        displayInfo.enterLastName();
        String lastName = inputData.nextLine();

        if (lastName.equals(EXIT)) {
            return EXIT;
        }

        while (!lastName.matches(LETTER_REGEX)) {
            displayInfo.inputError();
            displayInfo.enterLastName();
            lastName = inputData.nextLine();
            if (lastName.equals(EXIT)) {
                return EXIT;
            }
        }
        return lastName;
    }

    public String registrationUserCityOfResidence() {
        displayInfo.enterCity();
        String cityOfResidence = inputData.nextLine();

        if (cityOfResidence.equals(EXIT)) {
            return EXIT;
        }

        while (!cityOfResidence.matches(LETTER_REGEX)) {
            displayInfo.inputError();
            displayInfo.enterCity();
            cityOfResidence = inputData.nextLine();
            if (cityOfResidence.equals(EXIT)) {
                return EXIT;
            }
        }
        return cityOfResidence;
    }

    public String registrationUserPhoneNumber() {
        displayInfo.enterPhoneNumber();
        String phoneNumber = inputData.nextLine();

        while (!phoneNumber.matches(PHONE_REGEX)) {
            if (phoneNumber.equals(EXIT)) {
                return EXIT;
            }
            displayInfo.wrongNumber();
            displayInfo.enterPhoneNumber();
            phoneNumber = inputData.nextLine();
        }
        return phoneNumber;
    }

    public String registrationNumberUserCard() {
        displayInfo.enterCardNumber();
        String userCardNumber = inputData.nextLine();

        while (!userCardNumber.matches(CARD_NUMBER_REGEX)
               || dataClientCreation.getDataClient().containsKey(userCardNumber)) {
            if (userCardNumber.equals(EXIT)) {
                return EXIT;
            }
            displayInfo.wrongNumber();
            displayInfo.enterCardNumber();
            userCardNumber = inputData.nextLine();
        }
        return userCardNumber;
    }

    public String getNumberUserCard() {
        displayInfo.enterCardNumber();
        String userCardNumber = inputData.nextLine();

        while (!userCardNumber.matches(CARD_NUMBER_REGEX)) {
            if (userCardNumber.equals(EXIT)) {
                return EXIT;
            }
            displayInfo.wrongNumber();
            displayInfo.enterCardNumber();
            userCardNumber = inputData.nextLine();
        }

        while (!dataClientCreation.getDataClient().containsKey(userCardNumber)) {
            if (userCardNumber.equals(EXIT)) {
                return EXIT;
            }
            displayInfo.wrongUserCardNumber();
            displayInfo.enterCardNumber();
            userCardNumber = inputData.nextLine();
        }
        return userCardNumber;
    }

    public String registrationUserPassword() {
        displayInfo.enterPinNumber();
        String password = inputData.nextLine();

        while (!password.matches(PASSWORD_REGEX)) {
            if (password.equals(EXIT)) {
                return EXIT;
            }
            displayInfo.wrongPin();
            displayInfo.enterPinNumber();
            password = inputData.nextLine();
        }
        return password;
    }

    public String getUserPassword(String userCardNumber) {
        displayInfo.enterPinNumber();
        String password = inputData.nextLine();

        if (password.equals(EXIT)) {
            return EXIT;
        }

        int tryCount = 3;
        while (!password.matches(PASSWORD_REGEX)
               || !password.equals(dataClientCreation.getDataClient().get(userCardNumber).getPassword())) {

            if (tryCount < 1) {
                displayInfo.cardBlockInformation();
                inputErrorTimeDelay();
                break;
            }

            displayInfo.wrongPinEnter(tryCount);
            tryCount--;
            displayInfo.enterPinNumber();
            password = inputData.nextLine();

            if (password.equals(EXIT)) {
                return EXIT;
            }
        }
        return password;
    }

    public String getTransferCardSelection() {
        displayInfo.cardNumberForTransfer();
        String beneficiaryCardNumber = inputData.nextLine();

        while (!beneficiaryCardNumber.matches(CARD_NUMBER_REGEX)) {
            if (beneficiaryCardNumber.equals(EXIT)) {
                return EXIT;
            }
            displayInfo.wrongNumber();
            displayInfo.cardNumberForTransfer();
            beneficiaryCardNumber = inputData.nextLine();
        }
        return beneficiaryCardNumber;
    }

    private void inputErrorTimeDelay() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
                System.out.println(DisplayInfo.YELLOW
                                   + "*****   ***********\t\t\t\t\t\t"
                                   + (10 - i)
                                   + " сек.\t\t\t\t\t**********   *****"
                                   + DisplayInfo.RESET);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOGGER.log(Level.FINE, "Ожидание прервано (card block delay).", e);
                return;
            }
        }
    }

    public Double getCardRefill() {
        displayInfo.cardRefill();
        return readPositiveDouble();
    }

    public Double getCashWithdrawal() {
        displayInfo.cashWithdrawal();
        return readPositiveDouble();
    }

    public Double getTransferAmount() {
        displayInfo.transferAmount();
        return readPositiveDouble();
    }

    private Double readPositiveDouble() {
        while (true) {
            String line = inputData.nextLine();
            if (line.equals(EXIT)) {
                return 0.0;
            }
            try {
                double value = Double.parseDouble(line);
                if (value <= 0) {
                    displayInfo.wrongSum();
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                LOGGER.log(Level.FINE, "Некорректный ввод суммы: " + line, e);
                displayInfo.wrongSum();
            }
        }
    }
}
