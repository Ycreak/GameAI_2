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
        
        System.out.println("Calling ACT");
        mctsPlayer = getPlayer(so, elapsedTimer);
        mctsPlayer2 = getPlayer(so, elapsedTimer);

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
        // System.out.println("ACT is not here right now");
        // System.out.println("Leave your message at the beep");
        // System.out.println("beep.");




        // Here we create a tree

		ArrayList<SingleMCTSPlayer> myList = new ArrayList<>();
		myList.add(mctsPlayer);
		myList.add(mctsPlayer2);

        mctsPlayer.init(stateObs);
        mctsPlayer2.init(stateObs);

        // mctsPlayer.init(stateObs);
        // mctsPlayer2.init(stateObs);
        // //Determine the action using MCTS...
        // int action = mctsPlayer.run(elapsedTimer);
        // int action2 = mctsPlayer2.run(elapsedTimer);


        // System.out.println("Act");

        //... and return it.

        // System.out.println(actions[action]);
        // System.out.println(actions[action2]);

        List<Integer> intList = new ArrayList<Integer>();        

        List<String>strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
        List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
        
        System.out.println("Filtered List: " + filtered);
        String mergedString = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.joining(", "));
        System.out.println("Merged String: " + mergedString);

        
        List<Integer> hello = myList.parallelStream().map(o -> o.run(elapsedTimer)).collect(Collectors.toList());
        System.out.println(hello);
        
        for(int integer : hello){
            System.out.println(actions[integer]);
        }
        
        System.exit(0);


		myList.parallelStream().forEach((o) -> {
            
            // o.init(stateObs);
            int action = o.run(elapsedTimer);
            System.out.println(actions[action]);
            // intList.add(action);
            
            // System.out.print(o + " ");
		});
        
        System.exit(0);
        // System.out.println("ArrayList : " + intList.toString());


        return actions[0];
    }

}

// import java.util.stream.Stream; 


		// ArrayList<String> myList = new ArrayList<>();
		// myList.add("Item1");
		// myList.add("Item2");

		// range.parallelStream().forEach((o) -> {
		// 	System.out.print(o + " ");
		// });