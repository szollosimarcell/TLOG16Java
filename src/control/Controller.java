package control;

import basicclasses.TimeLogger;
import java.util.Scanner;

/**
 *
 * @author MARCI
 */
public class Controller {

    private final TimeLogger workLog = new TimeLogger();
    private final TimeLoggerUI ui = new TimeLoggerUI(workLog);
    private final Scanner scanner = new Scanner(System.in);

    public Controller() {
        start();
    }

    private void start() {
        System.out.println("Welcome to TimeLogger!\n\n");
        ui.showMenu();

        int input = checkRange(0, 10, false);
        while (ui.menu(input)) {
            ui.showMenu();
            input = checkRange(0, 10, false);
        }
    }

    public int checkRange(int lowerLimit, int upperLimit, boolean isIndex) {
        int input = checkIfNumeric();
        while (input < lowerLimit || upperLimit < input) {
            System.out.print("Wrong number! ");
            input = checkIfNumeric();
        }
        return input;
    }

    public int checkIfNumeric() {
        String input = scanner.nextLine();
        if (!input.matches("[0-9]+")) {
            System.out.print("Wrong value! Please type only numberic characters! ");
            input = scanner.nextLine();
        }
        return Integer.parseInt(input);
    }
}
