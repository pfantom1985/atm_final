package finalproject.ui;

import finalproject.dto.Client;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DisplayInfo {

    private static final boolean COLORS_ENABLED = resolveColorsEnabled();

    public static final String RED = color("\033[0;31m");
    public static final String GREEN = color("\033[0;32m");
    public static final String YELLOW = color("\033[0;33m");
    public static final String BLUE = color("\033[0;34m");
    public static final String VIOLET = color("\033[0;35m");
    public static final String TURQUOISE = color("\033[0;36m");
    public static final String RESET = color("\033[0m");

    private static final DateTimeFormatter CREATED_AT_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static boolean resolveColorsEnabled() {
        String noColorEnv = System.getenv("NO_COLOR");
        if (noColorEnv != null && !noColorEnv.isBlank()) {
            return false;
        }

        String noColorProp = System.getProperty("no.color");
        if (noColorProp != null) {
            String v = noColorProp.trim().toLowerCase();
            if (v.equals("true") || v.equals("1") || v.equals("yes") || v.equals("on")) {
                return false;
            }
        }

        return true;
    }

    private static String color(String ansi) {
        return COLORS_ENABLED ? ansi : "";
    }

    public void atmMainMenu() {
        System.out.print(BLUE + """
                *****   ******************************************************************   *****
                *****   ********   Г Л А В Н О Е   М Е Н Ю   Б А Н К О М А Т А    ********   *****
                *****   ******************************************************************   *****
                *****   ***   1.  Зарегистрировать нового клиента банка.          ********   *****
                *****   ***   2.  Вставить банковскую карточку в банкомат.        ********   *****
                *****   ***   3.  Завершить работу и забрать карту (EXIT).        ********   *****
                *****   ******************************************************************   *****
                """ + RESET);
    }

    public void displayClientMenu() {
        System.out.print(BLUE + """
                *****   ******************************************************************   *****
                *****   ******************    М Е Н Ю   К Л И Е Н Т А    *****************   *****
                *****   ******************       К А Р Т - С Ч Е Т       *****************   *****
                *****   ******************************************************************   *****
                *****   ***   1.  Пополнить карт-счет (до 10000 BYN).              *******   *****
                *****   ***   2.  Выдать денежные средства (до 2000 BYN).          *******   *****
                *****   ***   3.  Перевести с карты на карту (до 1000 BYN).        *******   *****
                *****   ***   4.  Просмотреть баланс карт-счета карточки.          *******   *****
                *****   ***   5.  Мини-выписка по счету (5 последних операций).    *******   *****
                *****   ***   6.  Сведения о владельце банковского карт-счета.     *******   *****
                *****   ***   7.  Дата и время создания карт-счета.                *******   *****
                *****   ***   8.  Вернуть карту и выйти в ГЛАВНОЕ МЕНЮ БАНКОМАТА.  *******   *****
                *****   ******************************************************************   *****
                """ + RESET);
    }

    public void successfulClientRegistration() {
        System.out.print(GREEN + """
                *****   ******************************************************************   *****
                *****   ********        Регистрация клиента прошла успешно!       ********   *****
                *****   ******************************************************************   *****
                *****   ********              Производится возврат в              ********   *****
                *****   ********   Г Л А В Н О Е   М Е Н Ю   Б А Н К О М А Т А    ********   *****
                *****   ******************************************************************   *****
                """ + RESET);
    }

    public void holderInformation(Client client) {
        System.out.println(TURQUOISE + "*****   ************   С В Е Д Е Н И Я   О   К Л И Е Н Т Е    ************   *****"
                           + "\n*****   ******************************************************************   *****"
                           + "\n*****   ********   Имя владельца: \t\t" + client.getFirstName()
                           + "\n*****   ********   Фамилия владельца: \t" + client.getLastName()
                           + "\n*****   ********   Город проживания: \t" + client.getCityOfResidence()
                           + "\n*****   ********   Номер телефона: \t\t" + client.getPhoneNumber()
                           + "\n*****   ********   Номер карточки: \t\t" + client.getUserCardNumber()
                           + "\n*****   ******************************************************************   *****" + RESET);
    }

    public void showAccountCreatedAt(LocalDateTime createdAt) {
        if (createdAt == null) {
            System.out.println(YELLOW + "*****   Дата создания счета отсутствует (нет данных).   ********   *****" + RESET);
            return;
        }
        System.out.println(TURQUOISE
                           + "*****   Дата и время создания карт-счета: "
                           + createdAt.format(CREATED_AT_FORMAT)
                           + "   **********   *****"
                           + RESET);
    }

    public void inputError() {
        System.out.print(YELLOW + """
                *****   ******************************************************************   *****
                *****   ********        Ошибка! Допустимые условия ввода:         ********   *****
                *****   ******************************************************************   *****
                *****   ********            1) ввод до 20 символов;               ********   *****
                *****   ********            2) буквы - латинские;                 ********   *****
                *****   ********            3) первая - заглавная;                ********   *****
                *****   ********            4) без дефисов и пробелов.            ********   *****
                *****   ******************************************************************   *****
                *****   Введите правильно Ваши данные:                            ********   *****
                """ + RESET);
    }

    public void cardBlockInformation() {
        System.out.print(YELLOW + """
                *****   ******************************************************************   *****
                *****   **********            В Н И М А Н И Е  !!!              **********   *****
                *****   **********        Ваша карточка заблокирована.          **********   *****
                *****   **********       Превышен лимит ввода пин-кода!         **********   *****
                *****   **********        Окончание блокировки через:           **********   *****
                *****   ******************************************************************   *****
                """ + RESET);
    }

    public void displayExit() {
        System.out.print(TURQUOISE + """
                *****   Все данные из базы сохранены в файл "data.json". Окончание работы.   *****
                *****   ******************************************************************   *****
                *****   **********            Д О   С В И Д А Н И Я             **********   *****
                *****   **********   (для работы запустите приложение заново)   **********   *****
                *****   ******************************************************************   *****
                """ + RESET);
    }

    public void dataSavedToFile() {
        System.out.println(TURQUOISE + "*****   Данные из файла \"data.json\" сохранены в базу. Приступаем к работе.   *****" + RESET);
    }

    public void detailsFromJson() {
        System.out.println(TURQUOISE + "*****   Из файла \"data.json\" в базу ничего не загружено.           *******   ***** " + RESET);
    }

    public void emptyDatabase() {
        System.out.println(TURQUOISE + "*****   ********************    База данных пуста.   *********************   *****" + RESET);
    }

    public void failureToTransact() {
        System.out.println(YELLOW + "*****   Операций по карт-счету за данную сессию не совершалось.   ********   *****" + RESET);
    }

    public void getTheCard() {
        System.out.println(TURQUOISE + "*****   **********  Все операции завершены. Заберите карточку!  **********   *****" + RESET);
    }

    public void selectMenuOperation() {
        System.out.print(BLUE + "*****   Выберите операцию и нажмите ВВОД:\t" + RESET);
    }

    public void enterFirstNameAndStart() {
        System.out.print(VIOLET + """
                *****   ******************************************************************   *****
                *****   ********       Р Е Г И С Т Р А Ц И Я   К Л И Е Н Т А      ********   *****
                *****   ******************************************************************   *****
                *****   ********     укажите Ваши данные, либо для возврата в     ********   *****
                *****   ********   Г Л А В Н О Е   М Е Н Ю   Б А Н К О М А Т А    ********   *****
                *****   ********            наберите слово:   " Exit "            ********   *****
                *****   ******************************************************************   *****
                *****   Ваше имя:\t""" + RESET);
    }

    public void enterFirstName() {
        System.out.print(VIOLET + "*****   Ваше имя:\t" + RESET);
    }

    public void enterLastName() {
        System.out.print(VIOLET + "*****   Фамилия:\t" + RESET);
    }

    public void enterCity() {
        System.out.print(VIOLET + "*****   Ваш город:\t" + RESET);
    }

    public void enterPhoneNumber() {
        System.out.print(VIOLET + "*****   Номер телефона (7 цифр):\t" + RESET);
    }

    public void enterCardNumber() {
        System.out.print(VIOLET + "*****   Номер карточки (8 цифр):\t" + RESET);
    }

    public void enterPinNumber() {
        System.out.print(VIOLET + "*****   Пин-код карты (4 цифры):\t" + RESET);
    }

    public void cardNumberForTransfer() {
        System.out.print(VIOLET + "*****   Укажите номер карты для перевода (8 цифр):\t" + RESET);
    }

    public void cardRefill() {
        System.out.print(VIOLET + "*****   Укажите вносимую сумму (до 10000 BYN):\t" + RESET);
    }

    public void cashWithdrawal() {
        System.out.print(VIOLET + "*****   Укажите снимаемую сумму (до 2000 BYN):\t" + RESET);
    }

    public void transferAmount() {
        System.out.print(VIOLET + "*****   Укажите переводимую сумму (до 1000 BYN):\t" + RESET);
    }

    public void getCardBalance(double balance) {
        System.out.println(TURQUOISE + "*****   Успешная операция! Баланс карт-счета ( " + balance + " ) BYN." + RESET);
    }

    public void successfulTransactionCardRefill(double clientCash) {
        System.out.println(GREEN + "*****   Успешная операция! На карт-счет внесено ( " + clientCash + " ) BYN." + RESET);
    }

    public void successfulCashWithdrawal(double clientCash) {
        System.out.println(GREEN + "*****   Успешная операция! С карт-счета списано ( " + clientCash + " ) BYN." + RESET);
    }

    public void successfulTransferAmount(String beneficiaryCardNumber, double clientCash) {
        System.out.printf(GREEN + "*****   Успешная операция! На карту < %s > переведено ( %s ) BYN %n",
                beneficiaryCardNumber, clientCash + RESET);
    }

    public void wrongPinEnter(int tryCount) {
        System.out.print(YELLOW + "*****   Ошибка ввода пин-кода! Осталось ( " + tryCount + " ) попыток.\nУкажите правильно пин-код Вашей карты:\t" + RESET);
    }

    public void invalidCardTransfer() {
        System.out.println(YELLOW + "*****   Перевод со счета на счет одной карты недопустим! Возврат в меню карт-счета клиента." + RESET);
    }

    public void isNotNumber() {
        System.out.print(YELLOW + "*****   Ошибка выбора операции! Вы ввели символ вместо цифры!     ********   *****\n" + RESET);
    }

    public void detailsToJson() {
        System.out.print(RED + "*****   Внимание!!! Возникла непредвиденная ошибка записи в файл \"data.json\". " + RESET);
    }

    public void inputLimitError() {
        System.out.println(RED + "*****   Ошибка ввода или превышен лимит! Возврат в меню карт-счета клиента." + RESET);
    }

    public void cardNumberVerificationError() {
        System.out.println(RED + "*****   Ошибка при сверке номеров карточек! Возврат в меню карт-счета клиента." + RESET);
    }

    public void selectionError() {
        System.out.print(RED + "*****   Ошибка выбора операции!" + RESET);
    }

    public void errorWarning() {
        System.out.println(RED + "*****   Внимание!!! Ошибка входа или ввода данных! Возврат в ГЛАВНОЕ МЕНЮ БАНКОМАТА." + RESET);
    }

    public void wrongNumber() {
        System.out.print(RED + "*****   Ошибка ввода или номер существует! Укажите заново номер.  ********   *****\n" + RESET);
    }

    public void wrongUserCardNumber() {
        System.out.print(RED + "*****   Ошибка ввода! Укажите правильно номер банковской карты.   ********   *****\n" + RESET);
    }

    public void wrongPin() {
        System.out.print(RED + "*****   Ошибка ввода! Укажите правильно пин-код банковской карты.  *******   *****\n" + RESET);
    }

    public void wrongSum() {
        System.out.print(RED + "*****   Ошибка ввода! Укажите правильно сумму:\t" + RESET);
    }

    public void cardNumberError() {
        System.out.println(RED + "*****   Ошибка ввода! Карточка с таким номером не существует.      *******   *****" + RESET);
    }
}
