package tracks.singlePlayer.simple.doNothing;

import core.game.StateObservation;
import core.player.AbstractPlayer;
import ontology.Types;
import ontology.Types.ACTIONS;
import tools.ElapsedCpuTimer;

import java.util.ArrayList;
import java.util.List; 
import java.util.stream.IntStream; 
import java.util.stream.Collectors; 
import java.util.stream.Stream; 

public class Agent extends AbstractPlayer{


	/**
	 * initialize all variables for the agent
	 * @param stateObs Observation of the current state.
     * @param elapsedTimer Timer when the action returned is due.
	 */
	public Agent(StateObservation stateObs, ElapsedCpuTimer elapsedTimer){
	}
	
	/**
	 * return ACTION_NIL on every call to simulate doNothing player
	 * @param stateObs Observation of the current state.
     * @param elapsedTimer Timer when the action returned is due.
	 * @return 	ACTION_NIL all the time
	 */
	@Override
	public ACTIONS act(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
		
		// List<Integer> range = IntStream.rangeClosed(1, 1000000).boxed().collect(Collectors.toList());
		// System.out.print(range);


		// ArrayList<String> myList = new ArrayList<>();
		// // ArrayList<String> list = new ArrayList<>();
		// myList.add("Item1");
		// myList.add("Item2");




		// range.parallelStream().forEach((o) -> {
		// 	System.out.print(o + " ");
		// });
		
		// // System.out.println("hello");
		// System.exit(0);
		
		
		return Types.ACTIONS.ACTION_NIL;
	}
}
