package services;

import data.Password;
import exception.InvalidAccountException;

import java.util.HashMap;

public class LocalServiceImpl implements LocalService{
    private final HashMap<String, Password> supportAccounts = new HashMap() {
        {
            put("jorge", new Password("123"));
            put("javier", new Password("javier23"));
            put("francis",new Password("tobby22"));
            put("juanen",new Password("naruto777"));
            put("patricia",new Password("20032004"));
        }
    };
    @Override
    public void verifyAccount (String login, Password pssw) throws InvalidAccountException{
        if(supportAccounts.containsKey(login)){
            Password password = supportAccounts.get(login);
            if(!password.equals(pssw)){
                throw new InvalidAccountException("Invalid Account");
            }
        }
        else {
            throw new InvalidAccountException("Invalid Account");
        }
    }
}
