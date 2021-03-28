package tracks.singlePlayer.student;

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
// import java.util.Entry;
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

    protected SingleMCTSPlayer mctsPlayer;
    protected SingleMCTSPlayer mctsPlayer2;
    protected SingleMCTSPlayer mctsPlayer3;
    protected SingleMCTSPlayer mctsPlayer4;

    protected ArrayList<SingleMCTSPlayer> playerList;

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
        
        //Create the player.

        // ArrayList<SingleMCTSPlayer> playerList = new ArrayList<>();

        for(int i = 0; i < 4; i++){
            SingleMCTSPlayer newPlayer = getPlayer(so, elapsedTimer);
            playerList.add(newPlayer);
        }
        System.out.println(playerList);

        // System.exit(0);


        mctsPlayer4 = getPlayer(so, elapsedTimer);

        mctsPlayer = getPlayer(so, elapsedTimer);
        mctsPlayer2 = getPlayer(so, elapsedTimer);
        mctsPlayer3 = getPlayer(so, elapsedTimer);
        // mctsPlayer4 = getPlayer(so, elapsedTimer);



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

        // Add the created players to a list
		// ArrayList<SingleMCTSPlayer> myList = new ArrayList<>();
		// myList.add(mctsPlayer);
		// myList.add(mctsPlayer2);
		// myList.add(mctsPlayer3);
		// myList.add(mctsPlayer4);


        for(SingleMCTSPlayer player : playerList ){
            player.init(stateObs);
        }
        //Set the state observation object as the new root of the tree.
        // mctsPlayer.init(stateObs);
        // mctsPlayer2.init(stateObs);
        // mctsPlayer3.init(stateObs);
        // mctsPlayer4.init(stateObs);
    
        // Run the players in parallel using Java's Stream function. Collect the results in a list
        List<Integer> ensembleResult = playerList.parallelStream().map(o -> o.run(elapsedTimer)).collect(Collectors.toList());
        System.out.println(ensembleResult);
        
        // Do a majority vote for the ensemble: use that value
        int action = findMostCommonElement(ensembleResult);
        System.out.println(action);

        System.exit(0);

        return actions[action];
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

		// myList.parallelStream().forEach((o) -> {
        //     int action = o.run(elapsedTimer);
        //     System.out.println(actions[action]);
		// });