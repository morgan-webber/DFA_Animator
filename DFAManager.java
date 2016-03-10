import java.awt.*;
import java.util.HashMap;

import javax.swing.*;

@SuppressWarnings("unused")
public class DFAManager {
	
	//----------------
	//Constants
	//----------------

	//The colors of each state/transition based on 
	//whether or not the state has been touched
	private final Color DFA_STATE_UNTOUCHED = Color.WHITE;
	private final Color DFA_STATE_CURRENT = Color.ORANGE;
	private final Color DFA_STATE_TOUCHED = Color.GREEN;
	private final Color DFA_TRANS_UNTOUCHED = Color.BLACK;
	private final Color DFA_TRANS_TOUCHED = Color.GRAY;
	
	//----------------
	//Variables
	//----------------
	
	// This is the canvas that we will draw the DFA on
	private Canvas DFACanvas = new Canvas();
	
	//This is the current DFA that we are manipulating
	private DFA activeDFA;
	
	
	//----------------
	//Methods
	//----------------
	
	
	
	
	/**
	 * Resets the DFA to it's original state by removing it's input
	 * string and resetting all of the colors for the states/transitions 
	 */
	public void resetDFA(DFA initDFA){
		
	}
	
	/**
	 * Generates a random DFA with a given number of states using
	 * a given alphabet. The transitions are randomly generated.
	 * 
	 * @return
	 * 		A new, randomly generated DFA of the given size and alphabet
	 */
	public DFA generateRandomDFA(int numStates, char[] alphabet){
		
		return null;
	}
	
	/**
	 * Creates a new DFA using the information stored in the sliders
	 * and text boxes.
	 * 
	 * @return
	 * 		A new, complete DFA
	 */
	public DFA createDFA(){
		
		return null;
	}
	
	/**
	 * This method will draw connections between each state on the
	 * canvas according to the transitions of the state
	 * 
	 */
	public void connectActiveDFA(){
		
	}
	
	/**
	 * "Rewinds" the DFA one character
	 */
	public void stepBack(){
		
	}
	
	/**
	 * Reads one character off the activeDFA's input string
	 * and advances it accordingly
	 */
	public void stepForward(){
		
	}
	
	/**
	 * Begins a wizard that will create a transition HashMap
	 * for the active DFA and will set the DFA's transition to
	 * that HashMap
	 */
	public void runTransitionWizard(){
		
	}
	
	/**
	 * This is the main method, program starts here
	 */
	public static void main(){
		
			
	}

	/**
	 * Sets the active DFA to a new DFA
	 * 
	 * @param newActiveDFA
	 * 		The new active DFA
	 */
	private void setActiveDFA(DFA newActiveDFA){ this.activeDFA = newActiveDFA; }
	
	
}
