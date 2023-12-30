package evoting;

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
    @BeforeEach
    public void crearVotingKiosk(){
        votingKiosk = new VotingKiosk();
    }
    @Test
    public void proceduralExceptionTest () {

        votingKiosk.setManualStepCounter(-1);

        ProceduralException exception = assertThrows(ProceduralException.class, votingKiosk::initVoting);
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
    public void checkCorrectOptionForDocumentDNI(){

        votingKiosk.setManualStepCounter(1);
        try {
            votingKiosk.setDocument('D');
        } catch (ProceduralException e) {
            throw new RuntimeException(e);
        }
        assertEquals(2, votingKiosk.getManualStepCounter());
    }
    @Test
    public void checkCorrectOptionForDocumentNIF(){

        votingKiosk.setManualStepCounter(1);
        try {
            votingKiosk.setDocument('N');
        } catch (ProceduralException e) {
            throw new RuntimeException(e);
        }
        assertEquals(2, votingKiosk.getManualStepCounter());
    }
    @Test
    public void checkCorrectOptionForDocumentPassport(){

        votingKiosk.setManualStepCounter(1);
        try {
            votingKiosk.setDocument('P');
        } catch (ProceduralException e) {
            throw new RuntimeException(e);
        }
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
    public void enterValidAccountTest() {
        votingKiosk.setManualStepCounter(2);
        LocalServiceImpl localService = new LocalServiceImpl();
        votingKiosk.setLocalService(localService);

        try {
            votingKiosk.enterAccount("francis", new Password("tobby22"));
        } catch (ProceduralException e) {
            throw new RuntimeException(e);
        } catch (InvalidAccountException e) {
            throw new RuntimeException(e);
        }
        assertEquals(3, votingKiosk.getManualStepCounter());
    }
}
