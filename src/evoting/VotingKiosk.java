package evoting;
import data.*;
import exception.*;
import services.*;

/**
 * Internal classes involved in the exercise of the vote
 */
public class VotingKiosk {

    private int manualStepCounter = 0;
    LocalService localService;
    ElectoralOrganism electoralOrganism;
    Scrutiny scrutiny;
    private char document;
    private String user;
    private Password password;
    private Nif nif;
    private VotingOption vopt;

    // The class members
    // The constructor/s
    // Input events
    public VotingKiosk(){}
    public void initVoting () throws ProceduralException {
        if (manualStepCounter == 0) {
            System.out.println("E-Voting functionality was selected");
            System.out.println("Select a type of Document for identification");
            manualStepCounter++;
        } else {
            throw new ProceduralException("InitVoting doesn't belong to the actual step");
        }
    }
    public void setDocument (char opt) throws ProceduralException {
        if (manualStepCounter == 1) {
            this.document = opt;
            if (opt == 'N' || opt == 'D') {
                //manual
                System.out.println("Wait for support staff");
                manualStepCounter++;
            } else if (opt == 'P') {
                //biometrico

            }
        } else {
            throw new ProceduralException("SetDocument doesn't belong to the actual step");
        }
    }
    public void enterAccount (String login, Password pssw) throws ProceduralException, InvalidAccountException, NullPointerException {
        if (manualStepCounter == 2) {
            if (login == null || pssw == null) {
                throw new NullPointerException("User name and password cannot be null");
            }
            localService.verifyAccount(login, pssw);
            System.out.println("Authentication ok");
            manualStepCounter++;
        } else {
            throw new ProceduralException("EnterAccount doesn't belong to the actual step");
        }
    }
    public void confirmIdentif (char conf) throws ProceduralException, InvalidDNIDocumException {
        if (manualStepCounter == 3) {

            if (conf == 'V') {
                System.out.println("Document is valid");
                System.out.println("Introduce NIF");
                manualStepCounter++;
            } else if (conf == 'I') {
                throw new InvalidDNIDocumException("Document is not valid");
            }
        } else {
            throw new ProceduralException("ConfirmIdentif doesn't belong to the actual step");
        }
    }
    public void enterNif (Nif nif) throws ProceduralException, NotEnabledException, NullPointerException, ConnectException {
        if (manualStepCounter == 4) {
            if (nif == null) {
                throw new NullPointerException("Entered nif cannot be null");
            }
            System.out.println("Nif ok. Starting voting rights verification");
            electoralOrganism.canVote(nif);
            System.out.println("Voting rights ok");
            this.nif = nif;
            manualStepCounter++;
        } else {
            throw new ProceduralException("EnterNif doesn't belong to the actual step");
        }
    }
    public void initOptionsNavigation () throws ProceduralException {
        if (manualStepCounter == 5) {
            System.out.println("Showing menus and voting options");
            manualStepCounter++;
        } else {
            throw new ProceduralException("InitOptionsNavigation doesn't belong to the actual step");
        }
    }
    public void consultVotingOption (VotingOption vopt) throws NullPointerException, ProceduralException{
        if (manualStepCounter == 6) {
            if (vopt ==  null){
                throw new NullPointerException("Consulted voting option cannot be null");
            }
            this.vopt = vopt;
            System.out.println("Showing vote info");
            manualStepCounter++;
        } else {
            throw new ProceduralException("ConsultVotingOption doesn't belong to the actual step");
        }
    }
    public void vote () throws ProceduralException{
        if (manualStepCounter == 7) {
            System.out.println("Select one of the parties to vote");
            System.out.println("Confirm your vote");
            manualStepCounter++;
        } else {
            throw new ProceduralException("Vote doesn't belong to the actual step");
        }
    }

    public void confirmVotingOption (char conf) throws ProceduralException ,ConnectException {
        if (manualStepCounter == 8) {
            if(conf == 'Y'){
                System.out.println("Voting option: "+ this.vopt + " .Confirmed");
                scrutiny.scrutinize(vopt);
                electoralOrganism.disableVoter(nif);
                manualStepCounter++;
                finalizeSession();
            }
            else if(conf =='N'){
                manualStepCounter -=2;
            }
        } else {
            throw new ProceduralException("ConfirmVotingOption doesn't belong to the actual step");
        }
    }
    // Internal operation, not required
    private void finalizeSession () {
        System.out.println("Finalizing session");
    }
    // Setter methods for injecting dependences and additional methods

    public void setManualStepCounter(int num){this.manualStepCounter = num;}
    public int getManualStepCounter(){
        return manualStepCounter;
    }
    public void setLocalService(LocalService localService) {this.localService = localService;}

    public void setElectoralOrganism(ElectoralOrganism electoralOrganism) {this.electoralOrganism = electoralOrganism;}
}
