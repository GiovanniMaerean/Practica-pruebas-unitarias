package evoting;
import data.VotingOption;

import services.ScrutinyImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScrutinyTest {
    private ScrutinyImpl scrutiny;
    private VotingKiosk votingKiosk;
    @BeforeEach
    public void createScrutiny(){
        scrutiny = new ScrutinyImpl();
        votingKiosk = new VotingKiosk();
        votingKiosk.setScrutiny(scrutiny);
    }
    @Test
    public void initVoteCountTest(){
        ArrayList<VotingOption> votingOptions = new ArrayList<>();
        votingOptions.add(new VotingOption("PartyA"));
        votingOptions.add(new VotingOption("PartyB"));
        votingKiosk.initVoteCount(votingOptions);

        assertEquals(0, votingKiosk.getBlanks());
        assertEquals(0, votingKiosk.getNulls());
        assertEquals(0, votingKiosk.getTotal());
        assertEquals(0, votingKiosk.getVotesFor(votingOptions.get(0)));
        assertEquals(0, votingKiosk.getVotesFor(votingOptions.get(1)));

    }

    @Test
    public void getNullVotesTest(){
        assertEquals(1, votingKiosk.getNulls());

    }

    @Test
    public void getBlankVotesTest(){
        assertEquals(3, votingKiosk.getBlanks());
    }

    @Test
    public void getTotalVotesTest(){
        assertEquals(25, votingKiosk.getTotal());
    }

    @Test
    public void getVotesForSpecificPartyTest(){
        assertEquals(4, votingKiosk.getVotesFor(new VotingOption("PartyA")));
        assertEquals(5, votingKiosk.getVotesFor(new VotingOption("PartyB")));
        assertEquals(2, votingKiosk.getVotesFor(new VotingOption("PartyC")));
        assertEquals(8, votingKiosk.getVotesFor(new VotingOption("PartyD")));
        assertEquals(6, votingKiosk.getVotesFor(new VotingOption("PartyE")));

    }

    @Test
    public void getResultTest(){
        votingKiosk.getScrutinyResults();
    }

    @Test
    public void scrutinizeBlankVoteTest(){
        votingKiosk.scrutinize(new VotingOption(""));
        assertEquals(4, votingKiosk.getBlanks());
        assertEquals(25, votingKiosk.getTotal());

    }

    @Test
    public void scrutinizeNullVoteTest(){
        votingKiosk.scrutinize(null);
        assertEquals(2, votingKiosk.getNulls());
        assertEquals(25, votingKiosk.getTotal());

    }

    @Test
    public void scrutinizeRegularVoteTest(){
        VotingOption vOpt = new VotingOption("PartyA");
        votingKiosk.scrutinize(vOpt);
        assertEquals(5,votingKiosk.getVotesFor(vOpt));
        assertEquals(26, votingKiosk.getTotal());
    }

}
