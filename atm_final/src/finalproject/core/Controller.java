package finalproject.core;

import finalproject.dto.Client;
import finalproject.dto.ClientTransaction;
import finalproject.dto.DataClientCreation;
import finalproject.dto.FileTransfer;
import finalproject.ui.DisplayInfo;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controller {

    private double clientCash = 0;

    String firstName;
    String lastName;
    String cityOfResidence;
    String phoneNumber;
    String userCardNumber;
    String password;
    String beneficiaryCardNumber;
    double balance;

    Client client;

    DataClientCreation dataClientCreation;

    DisplayInfo displayInfo = new DisplayInfo();
    DataValidation dataValidation;

    public Controller() {
        dataClientCreation = new DataClientCreation();
        dataValidation = new DataValidation(dataClientCreation);
    }

    public void start() {

        Map<String, Client> loadedClients = FileTransfer.loadClients();
        dataClientCreation.putAll(loadedClients);

        if (!loadedClients.isEmpty()) {
            displayInfo.dataSavedToFile();
        } else {
            displayInfo.emptyDatabase();
        }

        refundPosition:
        while (true) {

            MainMenu mainMenuSelection =
                    MainMenu.values()[Integer.parseInt(dataValidation.getUserInputItem())];

            switch (mainMenuSelection) {
                case REGISTER_CLIENT:
                    firstName = dataValidation.registrationUserFirstName();
                    if (firstName.equals(DataValidation.EXIT)) {
                        break;
                    }
                    lastName = dataValidation.registrationUserLastName();
                    if (lastName.equals(DataValidation.EXIT)) {
                        break;
                    }
                    cityOfResidence = dataValidation.registrationUserCityOfResidence();
                    if (cityOfResidence.equals(DataValidation.EXIT)) {
                        break;
                    }
                    phoneNumber = dataValidation.registrationUserPhoneNumber();
                    if (phoneNumber.equals(DataValidation.EXIT)) {
                        break;
                    }
                    userCardNumber = dataValidation.registrationNumberUserCard();
                    if (userCardNumber.equals(DataValidation.EXIT)) {
                        break;
                    }
                    password = dataValidation.registrationUserPassword();
                    if (password.equals(DataValidation.EXIT)) {
                        break;
                    }

                    dataClientCreation.addDataClient(
                            userCardNumber,
                            new Client(firstName, lastName, cityOfResidence, phoneNumber, password, userCardNumber, balance)
                    );

                    displayInfo.successfulClientRegistration();
                    break;

                case INSERT_CARD_INTO_ATM:
                    userCardNumber = dataValidation.getNumberUserCard();
                    if (userCardNumber.equals(DataValidation.EXIT)) {
                        break;
                    }
                    password = dataValidation.getUserPassword(userCardNumber);
                    if (password.equals(DataValidation.EXIT)) {
                        break;
                    }

                    if (dataClientCreation.containsCard(userCardNumber)) {
                        client = dataClientCreation.getClient(userCardNumber);

                        if (client != null && client.getPassword().equals(password)) {

                            while (true) {
                                displayInfo.displayClientMenu();

                                ClientMenu accountCardMenuSelection =
                                        ClientMenu.values()[Integer.parseInt(dataValidation.getUserInputMenu())];

                                switch (accountCardMenuSelection) {
                                    case REFILL_CARD_ACCOUNT:
                                        clientCash = dataValidation.getCardRefill();
                                        if (clientCash > 10000 || clientCash < 1) {
                                            displayInfo.inputLimitError();
                                            break;
                                        }
                                        client.deposit(clientCash, LocalDateTime.now());
                                        displayInfo.successfulTransactionCardRefill(clientCash);
                                        break;

                                    case CASH_WITHDRAWAL:
                                        clientCash = dataValidation.getCashWithdrawal();
                                        if (clientCash > 2000 || clientCash < 1) {
                                            displayInfo.inputLimitError();
                                            break;
                                        }
                                        client.withdraw(clientCash, LocalDateTime.now());
                                        displayInfo.successfulCashWithdrawal(clientCash);
                                        break;

                                    case TRANSFER_FROM_CARD_TO_CARD:
                                        beneficiaryCardNumber = dataValidation.getTransferCardSelection();
                                        if (beneficiaryCardNumber.equals(DataValidation.EXIT)) {
                                            break;
                                        }

                                        long tempBeneficiaryCardNumber;
                                        long tempUserCardNumber;
                                        try {
                                            tempBeneficiaryCardNumber = Long.parseLong(beneficiaryCardNumber);
                                            tempUserCardNumber = Long.parseLong(userCardNumber);
                                        } catch (NumberFormatException e) {
                                            displayInfo.cardNumberVerificationError();
                                            break;
                                        }

                                        if (tempBeneficiaryCardNumber == tempUserCardNumber) {
                                            displayInfo.invalidCardTransfer();
                                            break;
                                        }

                                        clientCash = dataValidation.getTransferAmount();
                                        if (clientCash > 1000 || clientCash < 1) {
                                            displayInfo.inputLimitError();
                                            break;
                                        }

                                        if (dataClientCreation.containsCard(beneficiaryCardNumber)) {
                                            Client beneficiaryOfPayment =
                                                    dataClientCreation.getClient(beneficiaryCardNumber);

                                            if (beneficiaryOfPayment == null) {
                                                displayInfo.cardNumberError();
                                                break;
                                            }

                                            beneficiaryOfPayment.deposit(clientCash, LocalDateTime.now());
                                            client.withdraw(clientCash, LocalDateTime.now());
                                        } else {
                                            displayInfo.cardNumberError();
                                            break;
                                        }

                                        displayInfo.successfulTransferAmount(beneficiaryCardNumber, clientCash);
                                        break;

                                    case BALANCE_CHECK:
                                        balance = client.getBalance();
                                        displayInfo.getCardBalance(balance);
                                        break;

                                    case MINI_STATEMENT:
                                        List<ClientTransaction> tx = client.getTransactions();
                                        if (tx == null || tx.isEmpty()) {
                                            displayInfo.failureToTransact();
                                        } else {
                                            tx.forEach(System.out::println);
                                        }
                                        break;

                                    case CLIENT_INFORMATION:
                                        displayInfo.holderInformation(client);
                                        break;

                                    case ACCOUNT_CREATED_AT:
                                        displayInfo.showAccountCreatedAt(client.getCreatedAt());
                                        break;

                                    case RETURN_CARD:
                                        displayInfo.getTheCard();
                                        continue refundPosition;

                                    default:
                                        displayInfo.selectionError();
                                }
                            }
                        } else {
                            displayInfo.errorWarning();
                        }
                    } else {
                        displayInfo.errorWarning();
                    }
                    break;

                case COMPLETE_ALL_OPERATIONS:
                    FileTransfer.saveClients(new HashMap<>(dataClientCreation.getDataClient()));

                    displayInfo.displayExit();
                    System.exit(0);
                    break;

                default:
                    displayInfo.selectionError();
            }
        }
    }
}
