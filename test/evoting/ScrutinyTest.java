package evoting;
import data.VotingOption;

import services.ScrutinyImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScrutinyTest {
    private ScrutinyImpl scrutiniy;
    @BeforeEach
    public void createScrutiny(){
        scrutiniy = new ScrutinyImpl();
    }
    @Test
    public void initVoteCountTest(){
        ArrayList<VotingOption> votingOptions = new ArrayList<>();
        votingOptions.add(new VotingOption("PartyA"));
        votingOptions.add(new VotingOption("PartyB"));
        scrutiniy.initVoteCount(votingOptions);

        assertEquals(0, scrutiniy.getBlanks());
        assertEquals(0, scrutiniy.getNulls());
        assertEquals(0, scrutiniy.getTotal());
        assertEquals(0, scrutiniy.getVotesFor(votingOptions.get(0)));
        assertEquals(0, scrutiniy.getVotesFor(votingOptions.get(1)));

    }

    @Test
    public void getNullVotesTest(){
        assertEquals(1, scrutiniy.getNulls());

    }

    @Test
    public void getBlankVotesTest(){
        assertEquals(3, scrutiniy.getBlanks());
    }

    @Test
    public void getTotalVotesTest(){
        assertEquals(25, scrutiniy.getTotal());
    }

}
