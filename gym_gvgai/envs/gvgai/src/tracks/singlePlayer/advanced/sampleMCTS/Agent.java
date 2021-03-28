package tracks.singlePlayer.advanced.sampleMCTS;

import java.util.ArrayList;
import java.util.Random;

import core.game.StateObservation;
import core.player.AbstractPlayer;
import ontology.Types;
import tools.ElapsedCpuTimer;

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
        // Random temp = new Random();
        
        
        // System.out.println(temp.nextInt());
        
        // System.out.println("Calling ACT");
        mctsPlayer = getPlayer(so, elapsedTimer);
        // mctsPlayer2 = getPlayer(so, elapsedTimer);

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

        //Set the state observation object as the new root of the tree.
        // System.out.println("You called ACT");

        // Here we create a tree

        mctsPlayer.init(stateObs);

        //Determine the action using MCTS...
        int action = mctsPlayer.run(elapsedTimer);
        // System.out.println("Act");

        //... and return it.

        // System.out.println(actions[action]);


        return actions[action];
    }

}

// import java.util.stream.Stream; 


		// ArrayList<String> myList = new ArrayList<>();
		// myList.add("Item1");
		// myList.add("Item2");

		// range.parallelStream().forEach((o) -> {
		// 	System.out.print(o + " ");
		// });