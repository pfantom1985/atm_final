package finalproject;

import finalproject.core.Controller;

public class StartMenu {

    public static void main(String[] args) {
        try {
            new Controller().start();
        } catch (Exception e) {
            System.out.println("Критическая ошибка: " + e.getMessage()); // можно заменить на DisplayInfo позже
            e.printStackTrace();
        }
    }
}
