package evoting;

import evoting.biometricdataperipheral.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import exception.*;
import data.*;
import services.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;


public class VotingKioskTest {
    private VotingKiosk votingKiosk;

    public static class InvalidLocalService extends LocalServiceImpl {
        @Override
        public void verifyAccount (String login, Password pssw) throws InvalidAccountException{
            throw new InvalidAccountException("Invalid Account");
        }
    }

    public static class NotEnabledElectoralOrganism extends ElectoralOrganismImpl {
        @Override
        public void canVote(Nif nif)  throws NotEnabledException, ConnectException {
            throw new NotEnabledException("Voter not enabled");
        }
    }

    public static class NoConnectionElectoralOrganism extends ElectoralOrganismImpl{
        @Override
        public void canVote(Nif nif) throws NotEnabledException, ConnectException {
            throw new ConnectException("There is no connection");
        }

        @Override
        public void disableVoter(Nif nif) throws ConnectException {
            throw new ConnectException("There is no connection");
        }
    }
    public static class InvalidPassportBiometricReader extends PassportBiometricReaderImpl {

        @Override
        public void validatePassport () throws NotValidPassportException{
            throw new NotValidPassportException("Not valid passport");
        }
    }
    public static class ErrorGetPassportBiometricReader extends PassportBiometricReaderImpl {
        @Override
        public BiometricData getPassportBiometricData () throws PassportBiometricReadingException {
            throw new PassportBiometricReadingException("Something went wrong while reading passport's biometric data");
        }
    }
    public static class NotHumanBiometricScanner extends HumanBiometricScannerImpl {
        @Override
        public SingleBiometricData scanFaceBiometrics () throws HumanBiometricScanningException{
            throw new HumanBiometricScanningException("Something went wrong while scanning face biometrics");
        }

        @Override
        public SingleBiometricData scanFingerprintBiometrics () throws HumanBiometricScanningException{
            throw new HumanBiometricScanningException("Something went wrong while scanning finger biometrics");
        }
    }

    @BeforeEach
    public void crearVotingKiosk(){
        votingKiosk = new VotingKiosk();
    }
    @Test
    public void proceduralExceptionTest () {

        votingKiosk.setManualStepCounter(-1);
        votingKiosk.setBiometricStepCounter(-1);

        ProceduralException exception = assertThrows(ProceduralException.class, () -> {
            votingKiosk.initVoting();
        });
        assertEquals("InitVoting doesn't belong to the actual step", exception.getMessage());

        exception = assertThrows(ProceduralException.class, () -> {
            votingKiosk.setDocument('N');
        });
        assertEquals("SetDocument doesn't belong to the actual step", exception.getMessage());


        exception = assertThrows(ProceduralException.class, () -> {
            votingKiosk.enterAccount("Fernando", new Password("1234"));
        });
        assertEquals("EnterAccount doesn't belong to the actual step", exception.getMessage());


        exception = assertThrows(ProceduralException.class, () -> {
            votingKiosk.confirmIdentif('Y');
        });
        assertEquals("ConfirmIdentif doesn't belong to the actual step", exception.getMessage());


        exception = assertThrows(ProceduralException.class, () -> {
            votingKiosk.enterNif(new Nif("12345678B"));
        });
        assertEquals("EnterNif doesn't belong to the actual step", exception.getMessage());


        exception = assertThrows(ProceduralException.class, votingKiosk::initOptionsNavigation);
        assertEquals("InitOptionsNavigation doesn't belong to the actual step", exception.getMessage());


        exception = assertThrows(ProceduralException.class, () -> {
            votingKiosk.consultVotingOption(new VotingOption(""));
        });
        assertEquals("ConsultVotingOption doesn't belong to the actual step", exception.getMessage());


        exception = assertThrows(ProceduralException.class, votingKiosk::vote);
        assertEquals("Vote doesn't belong to the actual step", exception.getMessage());

        exception = assertThrows(ProceduralException.class, () -> {
            votingKiosk.confirmVotingOption('Y');
        });
        assertEquals("ConfirmVotingOption doesn't belong to the actual step", exception.getMessage());
    }
    @Test
    public void checkCorrectOptionForDocumentDNI() throws ProceduralException{

        votingKiosk.setManualStepCounter(1);

        votingKiosk.setDocument('D');

        assertEquals(2, votingKiosk.getManualStepCounter());
    }
    @Test
    public void checkCorrectOptionForDocumentNIF() throws ProceduralException{

        votingKiosk.setManualStepCounter(1);

        votingKiosk.setDocument('N');

        assertEquals(2, votingKiosk.getManualStepCounter());
    }
    @Test
    public void checkCorrectOptionForDocumentPassport() throws ProceduralException{

        votingKiosk.setManualStepCounter(1);

        votingKiosk.setDocument('P');

        assertEquals(1, votingKiosk.getManualStepCounter());
    }
    @Test
    public void nullAccountAttributesTest() {
        votingKiosk.setManualStepCounter(2);

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            votingKiosk.enterAccount(null, null);
        });


        assertEquals("User name and password cannot be null", exception.getMessage());

        exception = assertThrows(NullPointerException.class, () -> {
            votingKiosk.enterAccount("", null);
        });


        assertEquals("User name and password cannot be null", exception.getMessage());

        exception = assertThrows(NullPointerException.class, () -> {
            votingKiosk.enterAccount(null, null);
        });

        assertEquals("User name and password cannot be null", exception.getMessage());
    }

    @Test
    public void enterAccountThrowsExceptionTest() {
        votingKiosk.setManualStepCounter(2);
        InvalidLocalService invalidLocalService = new InvalidLocalService();
        votingKiosk.setLocalService(invalidLocalService);

        InvalidAccountException exception = assertThrows(InvalidAccountException.class,() -> {
            votingKiosk.enterAccount("", new Password(""));
        });
        assertEquals("Invalid Account", exception.getMessage());
    }

    @Test
    public void enterAccountInvalidLoginTest() {
        votingKiosk.setManualStepCounter(2);
        LocalServiceImpl localService = new LocalServiceImpl();
        votingKiosk.setLocalService(localService);

        InvalidAccountException exception = assertThrows(InvalidAccountException.class,() -> {
            votingKiosk.enterAccount("miguel", new Password("123"));
        });
        assertEquals("Invalid Account", exception.getMessage());
    }

    @Test
    public void enterAccountInvalidPasswordTest() {
        votingKiosk.setManualStepCounter(2);
        LocalServiceImpl localService = new LocalServiceImpl();
        votingKiosk.setLocalService(localService);

        InvalidAccountException exception = assertThrows(InvalidAccountException.class,() -> {
            votingKiosk.enterAccount("francis", new Password("juanjo88"));
        });
        assertEquals("Invalid Account", exception.getMessage());
    }

    @Test
    public void enterValidAccountTest() throws ProceduralException, InvalidAccountException{
        votingKiosk.setManualStepCounter(2);
        LocalServiceImpl localService = new LocalServiceImpl();
        votingKiosk.setLocalService(localService);

        votingKiosk.enterAccount("francis", new Password("tobby22"));

        assertEquals(3, votingKiosk.getManualStepCounter());
    }

    @Test
    public void confirmInvalidIdentifTest() {
        votingKiosk.setManualStepCounter(3);

        InvalidDNIDocumException exception = assertThrows(InvalidDNIDocumException.class, () -> {
            votingKiosk.confirmIdentif('I');
        });

        assertEquals("Document is not valid", exception.getMessage());

    }


    @Test
    public void confirmValidIdentifTest() throws ProceduralException, InvalidDNIDocumException {
        votingKiosk.setManualStepCounter(3);

        votingKiosk.confirmIdentif('V');

        assertEquals(4, votingKiosk.getManualStepCounter());
    }


    @Test
    public void enterNullNifTest() {
        votingKiosk.setManualStepCounter(4);

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            votingKiosk.enterNif(null);
        });

        assertEquals("Entered nif cannot be null", exception.getMessage());
    }

    @Test
    public void notEnabledExceptionTest() {
        votingKiosk.setManualStepCounter(4);
        NotEnabledElectoralOrganism notEnabledElecOrg = new NotEnabledElectoralOrganism();
        votingKiosk.setElectoralOrganism(notEnabledElecOrg);

        NotEnabledException exception = assertThrows(NotEnabledException.class, () -> {
            votingKiosk.enterNif(new Nif("11111111A"));
        });

        assertEquals("Voter not enabled", exception.getMessage());
    }

    @Test
    public void enterNifConnectExceptionTest() {
        votingKiosk.setManualStepCounter(4);
        NoConnectionElectoralOrganism noConnectElectOrg = new NoConnectionElectoralOrganism();
        votingKiosk.setElectoralOrganism(noConnectElectOrg);

        ConnectException exception = assertThrows(ConnectException.class, () -> {
           votingKiosk.enterNif(new Nif("11111111A"));
        });

        assertEquals("There is no connection", exception.getMessage());
    }

    @Test
    public void nifNotInElecCollegeTest() {
        votingKiosk.setManualStepCounter(4);
        ElectoralOrganismImpl electoralOrganism = new ElectoralOrganismImpl();
        votingKiosk.setElectoralOrganism(electoralOrganism);

        NotEnabledException exception = assertThrows(NotEnabledException.class, () -> {
            votingKiosk.enterNif(new Nif("11111111Z"));
        });

        assertEquals("Nif is not on the electoral college", exception.getMessage());

    }

    @Test
    public void voterAlreadyVotedTest() {
        votingKiosk.setManualStepCounter(4);
        ElectoralOrganismImpl electoralOrganism = new ElectoralOrganismImpl();
        votingKiosk.setElectoralOrganism(electoralOrganism);

        NotEnabledException exception = assertThrows(NotEnabledException.class, () -> {
            votingKiosk.enterNif(new Nif("11111111A"));
        });

        assertEquals("Voter has already voted", exception.getMessage());
    }

    @Test
    public void voterCanVote() throws NifFormatException, ProceduralException, NotEnabledException, ConnectException {
        votingKiosk.setManualStepCounter(4);
        ElectoralOrganismImpl electoralOrganism = new ElectoralOrganismImpl();
        votingKiosk.setElectoralOrganism(electoralOrganism);

        votingKiosk.enterNif(new Nif("55555555C"));

        assertEquals(5, votingKiosk.getManualStepCounter());

    }

    @Test
    public void nullConsultVotingOption() {
        votingKiosk.setManualStepCounter(6);
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            votingKiosk.consultVotingOption(null);
        });

        assertEquals("Consulted voting option cannot be null", exception.getMessage());
    }

    @Test
    public void confirmVOptConnectExceptionTest() {
        Scrutiny scrutiny = new ScrutinyImpl();
        votingKiosk.setScrutiny(scrutiny);
        votingKiosk.setManualStepCounter(8);
        NoConnectionElectoralOrganism noConnectElectOrg = new NoConnectionElectoralOrganism();
        votingKiosk.setElectoralOrganism(noConnectElectOrg);

        ConnectException exception = assertThrows(ConnectException.class, () -> {
            votingKiosk.confirmVotingOption('Y');
        });

        assertEquals("There is no connection", exception.getMessage());
    }


    @Test
    public void confirmedVotingOptionTest() throws ProceduralException, ConnectException {
        Scrutiny scrutiny = new ScrutinyImpl();
        votingKiosk.setScrutiny(scrutiny);
        votingKiosk.setManualStepCounter(8);

        ElectoralOrganismImpl electoralOrganism = new ElectoralOrganismImpl();
        votingKiosk.setElectoralOrganism(electoralOrganism);

        votingKiosk.confirmVotingOption('Y');

        assertEquals(9, votingKiosk.getManualStepCounter());
    }

    @Test
    public void notConfirmedVotingOptionTest() throws ProceduralException, ConnectException {
        votingKiosk.setManualStepCounter(8);

        ElectoralOrganismImpl electoralOrganism = new ElectoralOrganismImpl();
        votingKiosk.setElectoralOrganism(electoralOrganism);

        votingKiosk.confirmVotingOption('N');

        assertEquals(6, votingKiosk.getManualStepCounter());
    }

    @Test
    public void proceduralExceptionBiometricTest () {

        votingKiosk.setManualStepCounter(-1);
        votingKiosk.setBiometricStepCounter(-1);

        ProceduralException exception = assertThrows(ProceduralException.class, votingKiosk::initVoting);
        assertEquals("InitVoting doesn't belong to the actual step", exception.getMessage());

        exception = assertThrows(ProceduralException.class, () -> {
            votingKiosk.setDocument('P');
        });
        assertEquals("SetDocument doesn't belong to the actual step", exception.getMessage());

        exception = assertThrows(ProceduralException.class, () -> {
            votingKiosk.grantExplicitConsent('Y');
        });
        assertEquals("GrantExplicitConsent doesn't belong to the actual step", exception.getMessage());

        exception = assertThrows(ProceduralException.class, () -> {
            votingKiosk.readPassport();
        });
        assertEquals("ReadPassport doesn't belong to the actual step", exception.getMessage());

        exception = assertThrows(ProceduralException.class, () -> {
            votingKiosk.readFaceBiometrics();
        });
        assertEquals("ReadFaceBiometrics doesn't belong to the actual step", exception.getMessage());

        exception = assertThrows(ProceduralException.class, () -> {
            votingKiosk.readFingerPrintBiometrics();
        });
        assertEquals("ReadFingerPrintBiometrics doesn't belong to the actual step", exception.getMessage());

        byte[] facialData = {5,15,25,35};
        byte[] fingerData = {10,20,30,40};
        BiometricData passportData = new BiometricData(new SingleBiometricData(facialData), new SingleBiometricData(fingerData));
        BiometricData humanData = new BiometricData(new SingleBiometricData(facialData), new SingleBiometricData(fingerData));
        exception = assertThrows(ProceduralException.class, () -> {
            votingKiosk.verifiyBiometricData(passportData, humanData);
        });
        assertEquals("VerifiyBiometricData doesn't belong to the actual step", exception.getMessage());


        exception = assertThrows(ProceduralException.class, () -> {
            votingKiosk.removeBiometricData();
        });
        assertEquals("RemoveBiometricData doesn't belong to the actual step", exception.getMessage());

        exception = assertThrows(ProceduralException.class, votingKiosk::initOptionsNavigation);
        assertEquals("InitOptionsNavigation doesn't belong to the actual step", exception.getMessage());


        exception = assertThrows(ProceduralException.class, () -> {
            votingKiosk.consultVotingOption(new VotingOption(""));
        });
        assertEquals("ConsultVotingOption doesn't belong to the actual step", exception.getMessage());


        exception = assertThrows(ProceduralException.class, votingKiosk::vote);
        assertEquals("Vote doesn't belong to the actual step", exception.getMessage());

        exception = assertThrows(ProceduralException.class, () -> {
            votingKiosk.confirmVotingOption('Y');
        });
        assertEquals("ConfirmVotingOption doesn't belong to the actual step", exception.getMessage());
    }
    @Test
    public void verifiyBiometricDataFiledTest(){
        byte[] facialData = {5,15,25,35};
        byte[] fingerData = {10,20,30,40};
        byte[] facialData2 = {45,35,29,100};
        BiometricData passportData = new BiometricData(new SingleBiometricData(facialData), new SingleBiometricData(fingerData));
        BiometricData humanData = new BiometricData(new SingleBiometricData(facialData2), new SingleBiometricData(fingerData));
        votingKiosk.setBiometricStepCounter(6);
        Exception exception = assertThrows(BiometricVerificationFailedException.class, () -> {
            votingKiosk.verifiyBiometricData(passportData, humanData);
        });
        assertEquals("Human biometric data doesn't match with passport biometric data", exception.getMessage());
    }
    @Test
    public void verifiyBiometricDataCorrectTest() throws ProceduralException, BiometricVerificationFailedException{
        byte[] facialData = {5,15,25,35};
        byte[] fingerData = {10,20,30,40};
        BiometricData passportData = new BiometricData(new SingleBiometricData(facialData), new SingleBiometricData(fingerData));
        BiometricData humanData = new BiometricData(new SingleBiometricData(facialData), new SingleBiometricData(fingerData));
        votingKiosk.setBiometricStepCounter(6);
        votingKiosk.verifiyBiometricData(passportData, humanData);
        assertEquals(7, votingKiosk.getBiometricStepCounter());
    }
    @Test
    public void grantExplicitConsentAcceptedTest() throws ProceduralException{
        votingKiosk.setBiometricStepCounter(2);
        votingKiosk.grantExplicitConsent('Y');
        assertEquals(3, votingKiosk.getBiometricStepCounter());
    }
    @Test
    public void grantExplicitConsentDenniedTest() throws ProceduralException{
        votingKiosk.setBiometricStepCounter(2);
        votingKiosk.grantExplicitConsent('N');
        assertEquals(2, votingKiosk.getBiometricStepCounter());
    }
    @Test
    public void readPassportValidatePassportFailsTest(){
        votingKiosk.setBiometricStepCounter(3);
        InvalidPassportBiometricReader invalidPassportBiometricReader = new InvalidPassportBiometricReader();
        votingKiosk.setPassportBiometricReader(invalidPassportBiometricReader);
        Exception exception = assertThrows(NotValidPassportException.class, () -> {
            votingKiosk.readPassport();
        });
        assertEquals("Not valid passport", exception.getMessage());

    }
    @Test
    public void readPassportGetPassportBiomDataCorrectTest(){
        votingKiosk.setBiometricStepCounter(3);
        ErrorGetPassportBiometricReader errorGetPassportBiometricReader = new ErrorGetPassportBiometricReader();
        votingKiosk.setPassportBiometricReader(errorGetPassportBiometricReader);
        Exception exception = assertThrows(PassportBiometricReadingException.class, () -> {
            votingKiosk.readPassport();
        });
        assertEquals("Something went wrong while reading passport's biometric data", exception.getMessage());

    }
    @Test
    public void readFaceBiometricsFailsTest(){
        votingKiosk.setBiometricStepCounter(4);
        NotHumanBiometricScanner notHumanBiometricScanner = new NotHumanBiometricScanner();
        votingKiosk.setHumanBiometricScanner(notHumanBiometricScanner);
        Exception exception = assertThrows(HumanBiometricScanningException.class, () -> {
            votingKiosk.readFaceBiometrics();
        });
        assertEquals("Something went wrong while scanning face biometrics", exception.getMessage());

    }
    @Test
    public void readFingerBiometricsFailsTest(){
        votingKiosk.setBiometricStepCounter(5);
        NotHumanBiometricScanner notHumanBiometricScanner = new NotHumanBiometricScanner();
        votingKiosk.setHumanBiometricScanner(notHumanBiometricScanner);
        Exception exception = assertThrows(HumanBiometricScanningException.class, () -> {
            votingKiosk.readFingerPrintBiometrics();
        });
        assertEquals("Something went wrong while scanning finger biometrics", exception.getMessage());

    }
}
