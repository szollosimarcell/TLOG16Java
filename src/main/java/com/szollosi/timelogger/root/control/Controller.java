/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.szollosi.timelogger.root.control;

import com.szollosi.timelogger.root.objects.TimeLogger;
import java.util.Scanner;

/**
 *
 * @author mszollosi
 */
public class Controller {

    private final TimeLogger workLog = new TimeLogger();
    private final TimeLoggerUI ui = new TimeLoggerUI(workLog);
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Constructor of the controller class.
     *
     */
    public Controller() {
        start();
    }

    /**
     * This method starts the cycle of the program until the user decides to exit.
     *
     */
    private void start() {
        System.out.println("Welcome to TimeLogger!\n\n");
        ui.showMenu();

        int input = checkInterval(0, 10);
        while (ui.menu(input)) {
            ui.showMenu();
            input = checkInterval(0, 10);
        }
    }

    /**
     * Calls the inputIfNumeric method.
     * Checks the numeric input whether it fits in the range of the options the program offers.
     *
     * @param lowerLimit - the lower limit of the interval
     * @param upperLimit - the upper limit of the interval
     * @return - the input
     */
    public int checkInterval(int lowerLimit, int upperLimit) {
        int input = inputIfNumeric();
        while (input < lowerLimit || upperLimit < input) {
            System.out.print("Wrong number! ");
            input = inputIfNumeric();
        }
        return input;
    }

    /**
     * Requests a numeric input from the user with restrictions. The input must be a numeric character.
     *
     * @return - the input
     */
    public int inputIfNumeric() {
        String input = scanner.nextLine();
        while (!input.matches("[0-9]+")) {
            System.out.print("Wrong value! Please type only numeric characters! ");
            input = scanner.nextLine();
        }
        return Integer.parseInt(input);
    }
}