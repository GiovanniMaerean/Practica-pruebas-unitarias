package data;

import exception.*;

final public class Nif {
    String voterNif;
    //provisional

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

    @Override
    public int hashCode () { return voterNif.hashCode(); }


    private boolean validNifFormat(String nif) {
        return nif.matches("\\d{8}[A-Za-z]");
    }

}