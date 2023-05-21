package controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.DataBank;
import model.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class LoginController {
    public boolean isPasswordStrong(String password) {
        return password.length() >= 6 && password.length() <= 32 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*[0-9].*") &&
                password.matches(".*[\\*\\.!@\\$%\\^&\\(\\)\\{\\}\\[\\]:;<>,\\?/~_\\+\\-=\\|].*");
    }

    public boolean isUsernameUsed(String username) {
        return DataBank.getUserByUsername(username) != null;
    }

    public boolean isPasswordCorrect(String password, User user) {
        return user.getPassword().equals(password);
    }

    public boolean doesUserExists(String username) {
        return DataBank.getUserByUsername(username) != null;
    }

}
