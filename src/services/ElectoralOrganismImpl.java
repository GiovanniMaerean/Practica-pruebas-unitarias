package services;

import data.Nif;
import data.Password;
import exception.ConnectException;
import exception.NifFormatException;
import exception.NotEnabledException;

import java.util.HashMap;
import java.util.HashSet;

public class ElectoralOrganismImpl implements ElectoralOrganism{

    private final HashMap<Nif, Boolean> electoralCollege = new HashMap<>(){
        {
            try {
                put(new Nif("11111111A"), false);

                put(new Nif("22222222B"), false);

                put(new Nif("33333333C"), true);

                put(new Nif("44444444D"), false);

                put(new Nif("55555555C"), true);
            } catch (NifFormatException e) {
                e.printStackTrace();
            }

        }
    };
    private boolean isConnection = true;


    public void canVote(Nif nif) throws NotEnabledException, ConnectException {
        
        if (isConnection) {
            if (electoralCollege.containsKey(nif)) {
                if (!electoralCollege.get(nif)) {
                    throw new NotEnabledException("Voter has already voted");
                }
            } else {
                throw new NotEnabledException("Nif is not on the electoral college");
            }
        }else {
            throw new ConnectException("There is no connection");
        }
    }

    public void disableVoter(Nif nif) throws ConnectException{
        if (isConnection) {
            electoralCollege.put(nif, false);
        } else {
            throw new ConnectException("There is no connection");
        }
    }
    
}
