package data;

import exception.*;

final public class Nif {
    String voterNif;
    //provisional
    private boolean alreadyVoted = false;
    //
    public Nif(String nif) throws NullPointerException, NifFormatException {
        if (nif == null) {
            throw new NullPointerException("NIF cannot be null");
        }
        if (!validNifFormat(nif)){
            throw new NifFormatException("The NIF format it's not correct");
        }
        this.voterNif = nif;
    }

    public String getVoterNif() {
        return voterNif;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nif nif = (Nif) o;
        return voterNif.equals(nif.voterNif);
    }


    private boolean validNifFormat(String nif) {
        return nif.matches("\\d{8}[A-Za-z]");
    }

    public void setAlreadyVoted(boolean alreadyVoted){
        this.alreadyVoted = alreadyVoted;
    }
    public boolean getAlreadyVoted(){
        return this.alreadyVoted;
    }
}