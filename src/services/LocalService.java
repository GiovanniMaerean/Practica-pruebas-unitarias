package services;

import data.Password;
import exception.InvalidAccountException;

public interface LocalService { // Solves the login step for the support staff
    void verifyAccount (String login, Password pssw)
            throws InvalidAccountException;
}
