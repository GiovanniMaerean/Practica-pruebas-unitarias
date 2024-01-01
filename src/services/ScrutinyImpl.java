package services;

import data.VotingOption;
import java.util.HashMap;
import java.util.List;

public class ScrutinyImpl implements Scrutiny{
    private HashMap<VotingOption, Integer> parties = new HashMap() {
        {
            put(new VotingOption("PartyA"), 0);
            put(new VotingOption("PartyB"), 0);
            put(new VotingOption("PartyC"), 0);
            put(new VotingOption("PartyD"), 0);
            put(new VotingOption("PartyE"), 0);
        }

    };
    private int totalVotes = 0;
    private int nullVotes = 0;
    private int blankVotes = 0;

    public void initVoteCount (List<VotingOption> validParties){

        parties = new HashMap<VotingOption, Integer>();
        for (VotingOption vOpt : validParties){
            parties.put(vOpt, 0);
        }

    }
    public void scrutinize (VotingOption vopt){
        if(vopt == null){
            nullVotes++;
        } else if (!parties.containsKey(vopt)) {
            blankVotes++;
        }
        else{
            totalVotes++;
            parties.replace(vopt, getVotesFor(vopt)+1);
        }
    }
    public int getVotesFor (VotingOption vopt){
        return parties.get(vopt);
    }
    public int getTotal (){
        return this.totalVotes;
    }
    public int getNulls (){
        return this.nullVotes;
    }
    public int getBlanks (){
        return this.blankVotes;
    }
    public void getScrutinyResults (){
        for (VotingOption vOpt : parties.keySet()) {
            System.out.println("Votes for "+ vOpt.getParty() + ": " + getVotesFor(vOpt));
        }
        System.out.println("Null votes: "+ getNulls());
        System.out.println("Blank votes "+ getBlanks());
        System.out.println("Total votes "+ getTotal());
    }
}
