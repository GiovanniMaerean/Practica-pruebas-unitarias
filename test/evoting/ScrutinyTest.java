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

    @Test
    public void getVotesForSpecificPartyTest(){
        assertEquals(4, scrutiniy.getVotesFor(new VotingOption("PartyA")));
        assertEquals(5, scrutiniy.getVotesFor(new VotingOption("PartyB")));
        assertEquals(2, scrutiniy.getVotesFor(new VotingOption("PartyC")));
        assertEquals(8, scrutiniy.getVotesFor(new VotingOption("PartyD")));
        assertEquals(6, scrutiniy.getVotesFor(new VotingOption("PartyE")));

    }

    @Test
    public void getResultTest(){
        scrutiniy.getScrutinyResults();
    }

    @Test
    public void scrutinizeBlankVoteTest(){
        scrutiniy.scrutinize(new VotingOption(""));
        assertEquals(4, scrutiniy.getBlanks());
        assertEquals(25, scrutiniy.getTotal());

    }

    @Test
    public void scrutinizeNullVoteTest(){
        scrutiniy.scrutinize(null);
        assertEquals(2, scrutiniy.getNulls());
        assertEquals(25, scrutiniy.getTotal());

    }

    @Test
    public void scrutinizeRegularVoteTest(){
        VotingOption vOpt = new VotingOption("PartyA");
        scrutiniy.scrutinize(vOpt);
        assertEquals(5, scrutiniy.getVotesFor(vOpt));
        assertEquals(26, scrutiniy.getTotal());
    }

}
