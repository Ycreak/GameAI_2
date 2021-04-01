package tracks.singlePlayer.advanced.student;

import java.util.ArrayList;
import java.util.Random;

import core.game.StateObservation;
import core.player.AbstractPlayer;
import ontology.Types;
import tools.ElapsedCpuTimer;

// For multicore
import java.util.stream.Stream; 
import java.util.stream.Collectors;
import java.util.List;
import java.util.Arrays;

import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
/**
 * Created with IntelliJ IDEA.
 * User: ssamot
 * Date: 14/11/13
 * Time: 21:45
 * This is an implementation of MCTS UCT
 */
public class Agent extends AbstractPlayer {

    public int num_actions;
    public Types.ACTIONS[] actions;

    // Init a playerlist
    protected ArrayList<SingleMCTSPlayer> playerList = new ArrayList<>();
    // Change your parameters here
    private boolean debugging = false;
    private boolean majority_voting = false;
    private int num_threads = 4;

    /**
     * Public constructor with state observation and time due.
     * @param so state observation of the current game.
     * @param elapsedTimer Timer for the controller creation.
     */
    public Agent(StateObservation so, ElapsedCpuTimer elapsedTimer)
    {
        //Get the actions in a static array.
        ArrayList<Types.ACTIONS> act = so.getAvailableActions();
        actions = new Types.ACTIONS[act.size()];
        for(int i = 0; i < actions.length; ++i)
        {
            actions[i] = act.get(i);
        }
        
        num_actions = actions.length;
        
        // Create several players to run simultaniously and add them to a list
        for(int i = 0; i < num_threads; i++){
            SingleMCTSPlayer newPlayer = getPlayer(so, elapsedTimer);
            playerList.add(newPlayer);
        }
        
        if(debugging) System.out.println(playerList);

    }

    public SingleMCTSPlayer getPlayer(StateObservation so, ElapsedCpuTimer elapsedTimer) {
        return new SingleMCTSPlayer(new Random(), num_actions, actions);
    }


    /**
     * Picks an action. This function is called every game step to request an
     * action from the player.
     * @param stateObs Observation of the current state.
     * @param elapsedTimer Timer when the action returned is due.
     * @return An action for the current state
     */
    public Types.ACTIONS act(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {

        for(SingleMCTSPlayer player : playerList ){
            //Set the state observation object as the new root of the tree.
            player.init(stateObs);
        }
    
        // Run the players in parallel using Java's Stream function. Collect the results in a list
        List<List<Double>> ensembleResult = playerList.parallelStream().map(o -> o.run(elapsedTimer)).collect(Collectors.toCollection(ArrayList::new));
        
        if(debugging) System.out.println(ensembleResult);

        // If majority voting is selected, create a list of all verdicts and find the most common action   
        if (majority_voting){
            List<Integer> majorityList = new ArrayList<>();

            for(List<Double> treeResult : ensembleResult){
                majorityList.add(treeResult.get(0).intValue());
            }
            
            if(debugging) System.out.println(majorityList);
            int action = findMostCommonElement(majorityList);
            if(debugging) System.out.println(action);
            return actions[action];
        }

        // No majority voting? We pick the highest value over all trees
        else {
            double highest = 0.0;
            double move = 0.0;

            for(List<Double> treeResult : ensembleResult){
                if (treeResult.get(1) > highest){
                    highest = treeResult.get(1);
                    move = treeResult.get(0);
                }
            }

            int move2 = (int)move;
            if(debugging) System.out.println(move2);

            // if(debugging) System.exit(0);
            return actions[move2];

        }
    }

    /**
     * Finds the most common element in the given integer list.
     * @param list of integers
     * @return int: most common element
     */
    public static int findMostCommonElement(List<Integer> list) {
        Collections.sort(list);

        int mostCommon = 0;
        int last = 0;
        int mostCount = 0;
        int lastCount = 0;
        for (int x : list) {
            if (x == last) {
                lastCount++;
            } 
            if (lastCount > mostCount) {
                mostCount = lastCount;
                mostCommon = last;
            }
            last = x;
        }
        return mostCommon;
    }
}