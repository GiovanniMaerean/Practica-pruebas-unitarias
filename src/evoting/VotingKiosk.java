package evoting;
import data.*;
import evoting.biometricdataperipheral.HumanBiometricScanner;
import evoting.biometricdataperipheral.PassportBiometricReader;
import exception.*;
import services.*;

import java.util.List;

/**
 * Internal classes involved in the exercise of the vote
 */
public class VotingKiosk {

    private int manualStepCounter = 0;
    private int biometricStepCounter = 0;
    LocalService localService;
    ElectoralOrganism electoralOrganism;
    Scrutiny scrutiny;
    private char document;
    private String user;
    private Password password;
    private Nif nif;
    private VotingOption vopt;
    private char explicitConsent;

    private BiometricData passportData;
    private HumanBiometricScanner humanBiometricScanner;
    private PassportBiometricReader passportBiometricReader;
    private SingleBiometricData faceData;

    private SingleBiometricData fingerprintData;

    // The class members
    // The constructor/s
    // Input events
    public VotingKiosk(){}
    public void initVoting () throws ProceduralException {
        if (manualStepCounter == 0  || biometricStepCounter == 0) {
            System.out.println("E-Voting functionality was selected");
            System.out.println("Select a type of Document for identification");
            manualStepCounter++;
            biometricStepCounter++;
        } else {
            throw new ProceduralException("InitVoting doesn't belong to the actual step");
        }
    }
    public void setDocument (char opt) throws ProceduralException {
        if (manualStepCounter == 1  || biometricStepCounter == 1) {
            this.document = opt;
            if (opt == 'N' || opt == 'D') {
                //manual
                System.out.println("Wait for support staff");
                manualStepCounter++;
            } else if (opt == 'P') {
                //biometrico
                biometricStepCounter++;
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
        if (manualStepCounter == 5 || biometricStepCounter == 8) {
            System.out.println("Showing menus and voting options");
            manualStepCounter++;
            biometricStepCounter++;
        } else {
            throw new ProceduralException("InitOptionsNavigation doesn't belong to the actual step");
        }
    }
    public void consultVotingOption (VotingOption vopt) throws NullPointerException, ProceduralException{
        if (manualStepCounter == 6 || biometricStepCounter == 9) {
            if (vopt ==  null){
                throw new NullPointerException("Consulted voting option cannot be null");
            }
            this.vopt = vopt;
            System.out.println("Showing vote info");
            manualStepCounter++;
            biometricStepCounter++;
        } else {
            throw new ProceduralException("ConsultVotingOption doesn't belong to the actual step");
        }
    }
    public void vote () throws ProceduralException{
        if (manualStepCounter == 7 || biometricStepCounter == 10) {
            System.out.println("Select one of the parties to vote");
            System.out.println("Confirm your vote");
            manualStepCounter++;
            biometricStepCounter++;
        } else {
            throw new ProceduralException("Vote doesn't belong to the actual step");
        }
    }

    public void confirmVotingOption (char conf) throws ProceduralException ,ConnectException {
        if (manualStepCounter == 8 || biometricStepCounter == 11) {
            if(conf == 'Y'){
                System.out.println("Voting option: "+ this.vopt + " .Confirmed");
                scrutinize(vopt);
                electoralOrganism.disableVoter(nif);
                manualStepCounter++;
                biometricStepCounter++;
                finalizeSession();
            }
            else if(conf =='N'){
                manualStepCounter -=2;
                biometricStepCounter -=2;
            }
        } else {
            throw new ProceduralException("ConfirmVotingOption doesn't belong to the actual step");
        }

    }
    // Internal operation, not required
    private void finalizeSession () {
        System.out.println("Finalizing session");
    }


    public void verifiyBiometricData(BiometricData humanBioD, BiometricData passpBioD) throws BiometricVerificationFailedException, ProceduralException {
        if (biometricStepCounter == 6) {
            boolean verificationSucceeded = humanBioD.equals(passpBioD);

            if (!verificationSucceeded) {
                throw new BiometricVerificationFailedException("Human biometric data doesn't match with passport biometric data");
            } else {
                System.out.println("Biometric data verification succeeded");
                biometricStepCounter++;
            }
        } else {
            throw new ProceduralException("VerifiyBiometricData doesn't belong to the actual step");
        }

    }

    public void removeBiometricData () throws ProceduralException{
        if (biometricStepCounter == 7) {
            passportData = null;
            biometricStepCounter++;
        } else {
            throw new ProceduralException("RemoveBiometricData doesn't belong to the actual step");
        }
    }

    public void grantExplicitConsent (char cons) throws ProceduralException{
        explicitConsent = cons;
        if (biometricStepCounter == 2) {
            if (cons == 'Y') {
                //manual
                System.out.println("Consent granted");
                biometricStepCounter++;
            } else if (cons == 'N') {
                //biometrico
                System.out.println("Consent not granted");
                //manualStepCounter++;
            }
        } else {
            throw new ProceduralException("GrantExplicitConsent doesn't belong to the actual step");
        }
    }

    public void readPassport () throws NotValidPassportException, PassportBiometricReadingException, ProceduralException {
        if (biometricStepCounter == 3) {
            passportBiometricReader.validatePassport();
            passportBiometricReader.getPassportBiometricData();
            Nif voterNif = passportBiometricReader.getNifWithOCR();

            biometricStepCounter++;
        } else {
            throw new ProceduralException("ReadPassport doesn't belong to the actual step");
        }
    }

    public void readFaceBiometrics () throws HumanBiometricScanningException, ProceduralException {
        if (biometricStepCounter == 4) {
            faceData = humanBiometricScanner.scanFaceBiometrics();
            biometricStepCounter++;
        } else {
            throw new ProceduralException("ReadFaceBiometrics doesn't belong to the actual step");
        }

    }

    public void readFingerPrintBiometrics () throws NotEnabledException, HumanBiometricScanningException, BiometricVerificationFailedException, ConnectException, ProceduralException {
        if (biometricStepCounter == 5) {
            fingerprintData = humanBiometricScanner.scanFingerprintBiometrics();
            biometricStepCounter++;
        } else {
            throw new ProceduralException("ReadFingerPrintBiometrics doesn't belong to the actual step");
        }
    }
    // Setter methods for injecting dependences and additional methods


    public void setManualStepCounter(int num){this.manualStepCounter = num;}
    public void setBiometricStepCounter(int num){this.biometricStepCounter = num;}

    public int getManualStepCounter(){
        return manualStepCounter;
    }
    public void setLocalService(LocalService localService) {this.localService = localService;}

    public void setElectoralOrganism(ElectoralOrganism electoralOrganism) {this.electoralOrganism = electoralOrganism;}

    public void setScrutiny(Scrutiny scrutiny) {this.scrutiny = scrutiny;}

    public void setHumanBiometricScanner(HumanBiometricScanner humanBiometricScanner) {this.humanBiometricScanner = humanBiometricScanner;}

    public void setPassportBiometricReader(PassportBiometricReader passportBiometricReader) {this.passportBiometricReader = passportBiometricReader;}
    public int getBiometricStepCounter(){
        return biometricStepCounter;
    }


    void initVoteCount (List<VotingOption> validParties){scrutiny.initVoteCount(validParties);}

    void scrutinize(VotingOption vopt) {scrutiny.scrutinize(vopt);}
    public int getVotesFor(VotingOption vopt) {return scrutiny.getVotesFor(vopt);}

    int getTotal (){return scrutiny.getTotal();}

    int getNulls (){return scrutiny.getNulls();}

    int getBlanks (){return scrutiny.getBlanks();}

    void getScrutinyResults (){scrutiny.getScrutinyResults();}
}
