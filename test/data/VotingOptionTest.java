package data;

import data.VotingOption;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class VotingOptionTest {

    @Test
    public void nullVotingOptionTest(){
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            new VotingOption(null);
        });

        assertEquals("Voting option cannot be null", exception.getMessage());
    }
}
