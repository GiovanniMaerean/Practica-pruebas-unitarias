package evoting;

import org.junit.jupiter.api.Test;
import exception.*;
import data.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class VotingKioskTest {
    @Test
    public void proceduralExceptionTest () {
        VotingKiosk vk = new VotingKiosk();
        vk.setManualStepCounter(-1);

        ProceduralException exception = assertThrows(ProceduralException.class, vk::initVoting);
        assertEquals("InitVoting doesn't belong to the actual step", exception.getMessage());

        exception = assertThrows(ProceduralException.class, () -> {
            vk.setDocument('N');
        });
        assertEquals("SetDocument doesn't belong to the actual step", exception.getMessage());


        exception = assertThrows(ProceduralException.class, () -> {
            vk.enterAccount("Fernando", new Password("1234"));
        });
        assertEquals("EnterAccount doesn't belong to the actual step", exception.getMessage());


        exception = assertThrows(ProceduralException.class, () -> {
            vk.confirmIdentif('Y');
        });
        assertEquals("ConfirmIdentif doesn't belong to the actual step", exception.getMessage());


        exception = assertThrows(ProceduralException.class, () -> {
            vk.enterNif(new Nif("12345678B"));
        });
        assertEquals("EnterNif doesn't belong to the actual step", exception.getMessage());


        exception = assertThrows(ProceduralException.class, vk::initOptionsNavigation);
        assertEquals("InitOptionsNavigation doesn't belong to the actual step", exception.getMessage());


        exception = assertThrows(ProceduralException.class, () -> {
            vk.consultVotingOption(new VotingOption(""));
        });
        assertEquals("ConsultVotingOption doesn't belong to the actual step", exception.getMessage());


        exception = assertThrows(ProceduralException.class, vk::vote);
        assertEquals("Vote doesn't belong to the actual step", exception.getMessage());

        exception = assertThrows(ProceduralException.class, () -> {
            vk.confirmVotingOption('Y');
        });
        assertEquals("ConfirmVotingOption doesn't belong to the actual step", exception.getMessage());
    }

}
