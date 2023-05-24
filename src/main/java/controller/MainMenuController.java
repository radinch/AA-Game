package controller;

import model.DataBank;

import java.util.ArrayList;

public class MainMenuController {
    public static void prepareGame() {
        DataBank.initializeGameDegrees();
        DataBank.getCurrentMap().setBalls(DataBank.getNumberOfBalls());
    }

    public static void prepareTwoPlayerGame() {
        DataBank.initializeGameDegrees();
    }
}
