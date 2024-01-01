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
        assertEquals(0, votingKiosk.getNulls());

        votingKiosk.scrutinize(null);
        assertEquals(1, votingKiosk.getNulls());

    }

    @Test
    public void getBlankVotesTest(){
        assertEquals(0, votingKiosk.getBlanks());

        votingKiosk.scrutinize(new VotingOption(""));
        votingKiosk.scrutinize(new VotingOption(""));

        assertEquals(2, votingKiosk.getBlanks());
    }

    @Test
    public void getTotalVotesTest(){
        assertEquals(0, votingKiosk.getTotal());

        votingKiosk.scrutinize(new VotingOption("PartyA"));
        votingKiosk.scrutinize(new VotingOption("PartyA"));
        votingKiosk.scrutinize(new VotingOption("PartyA"));
        votingKiosk.scrutinize(new VotingOption("PartyB"));

        assertEquals(4, votingKiosk.getTotal());
    }

    @Test
    public void getVotesForSpecificPartyTest(){
        votingKiosk.scrutinize(new VotingOption("PartyA"));
        votingKiosk.scrutinize(new VotingOption("PartyA"));

        assertEquals(2, votingKiosk.getVotesFor(new VotingOption("PartyA")));

        votingKiosk.scrutinize(new VotingOption("PartyB"));
        votingKiosk.scrutinize(new VotingOption("PartyB"));
        votingKiosk.scrutinize(new VotingOption("PartyB"));
        votingKiosk.scrutinize(new VotingOption("PartyB"));

        assertEquals(4, votingKiosk.getVotesFor(new VotingOption("PartyB")));

        assertEquals(0, votingKiosk.getVotesFor(new VotingOption("PartyC")));


    }

    @Test
    public void getResultTest(){
        votingKiosk.scrutinize(new VotingOption("PartyA"));
        votingKiosk.scrutinize(new VotingOption("PartyA"));

        votingKiosk.scrutinize(new VotingOption("PartyB"));
        votingKiosk.scrutinize(new VotingOption("PartyB"));
        votingKiosk.scrutinize(new VotingOption("PartyB"));
        votingKiosk.scrutinize(new VotingOption("PartyB"));

        votingKiosk.getScrutinyResults();
    }


}
