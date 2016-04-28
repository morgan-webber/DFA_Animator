import java.awt.Canvas;
import java.awt.Component;
import java.awt.Graphics;
import java.util.HashMap;

import javax.swing.JOptionPane;

@SuppressWarnings("unused")
public class DFA {
	
	//----------------
	//Constants
	//----------------
	
	
	//----------------
	//Variables
	//----------------
	
	//The states of the DFA
	private DFAState[] states;
	
	//The alphabet for the DFA
	private char[] alphabet;
	
	//The starting/accepting states' indexes in the states array of the DFA
	private int startState;
	private int[] acceptStates;
	
	//The input string for the DFA
	private String inputString;
	
	//The current letter we are on in the input string
	private int currentLetterIndex;
	
	//The current state the DFA is on
	private DFAState currentState;
	
	//Boolean to determine if the DFA is still processing the string
	private boolean isFinished;
	
	//The number of states in the DFA
	private int numStates;
	
	//The name of the DFA
	private String name;
	
	//----------------
	//Methods
	//----------------
	
	/**
	 * Default Constructor for the DFA. Creates a DFA object with the
	 * given parameters.
	 * 
	 * @param initStates
	 * 		The states in the DFA
	 * @param initAlphabet
	 * 		The alphabet of the DFA
	 * @param initStartState
	 * 		The starting state for the DFA
	 * @param initAccept
	 * 		The accept states of the DFA
	 * @param transitions
	 * 		The transitions for each state in the DFA
	 */
	public DFA(DFAState[] initStates, char[] initAlphabet, int initStartState, int[] initAcceptStates, String initName){
		this.states = initStates;
		this.alphabet = initAlphabet;
		this.startState = initStartState;
		this.acceptStates = initAcceptStates;
		this.numStates = initStates.length;
		this.name = initName;
		this.currentState = states[initStartState];
		this.currentLetterIndex = 0;
		this.inputString = "";
		this.isFinished = false;
	}
	
	/**
	 * Alternate Constructor for the DFA. Creates a randomized DFA with the
	 * given number of states and the given alphabet.
	 * 
	 * @param initNumStates
	 * 		The number of states in the DFA
	 * @param initAlphabet
	 * 		The alphabet for the DFA
	 */
	public DFA(int initNumStates, char[] initAlphabet){
		
	}

	/**
	 * Reads off the next character in the input string
	 * and moves to the respective state
	 * 
	 * @return
	 * 		Returns true if the transition was made
	 * 		or false if we are at the end of the 
	 * 		input string.
	 */
	public boolean makeTransition(){
			
		if (currentLetterIndex >= inputString.length() - 1){
			isFinished = true;
			return false;
		}
		else{
			this.currentState = this.currentState.readChar(this.inputString.charAt(this.currentLetterIndex));
			currentLetterIndex++;
			return true;
		}
		
	}
	
	/**
	 * Creates a state array containing new states without transitions but with
	 * each state being marked as either accept of reject
	 * 
	 * @param numStates
	 * 		The number of states in the DFA
	 * @param acceptStates
	 * 		An array containing the index of the accept states
	 * @return
	 */
	public static DFAState[] createStates(int numStates, int[] acceptStates, int startState){
		DFAState[] states = new DFAState[numStates];
		for(int i = 0; i < numStates; i++){
			//Create our new state
			states[i] = new DFAState();
			
			//Mark the state as an accept state if it is one
			for (int acceptState : acceptStates){
				if (i == acceptState){
					states[i].setIsAccept(true);
					break;
				}
			}
			
			//Mark the state as the start state if it is one
			if (i == startState){
				states[i].setIsStart(true);
			}
			
		}
		
		//Give the states their positions
		if (numStates != 4){
			states[0].setPosition(75, 75);
			if (numStates > 1){
				states[1].setPosition(275, 75);
				if (numStates > 2){
					states[2].setPosition(175, 175);
					if (numStates > 3){
						states[3].setPosition(75, 275);
						states[4].setPosition(275, 275);
					}
				}
			}
		}
		else{
			states[0].setPosition(75, 75);
			states[1].setPosition(275, 75);
			states[2].setPosition(75, 275);
			states[3].setPosition(275, 275);
		}
		
		
		
		return states;
	}
	
	/**
	 * Adds a transition to the DFA
	 * @param newTransition
	 */
	public void addTransition(Transition newTransition){
		int begState = newTransition.getBeginningState();
		String transChar = newTransition.getTransitionCharacter();
		int endState = newTransition.getEndingState();
		
		this.states[begState].addTransition(transChar, this.states[endState]);
	}
	
	
	//-----GETTERS-----//
	
	public int[] getAcceptStates(){ return this.acceptStates; }
	public DFAState getCurrentState(){ return this.currentState; }
	public char[] getAlphabet(){ return this.alphabet; }
	public boolean isFinished(){ return this.isFinished; }
	public DFAState getStartState(){ return this.states[this.startState]; }
	public DFAState[] getStates(){ return this.states; }
	public String getName() { return this.name; }
	public String getInputString() { return this.inputString; }
	public int getCurrentIndex() { return this.currentLetterIndex;}
	public int getCurrentLetter() {
		if (this.currentLetterIndex >= this.inputString.length()){
			return this.inputString.charAt(this.currentLetterIndex - 1);
		}
		else{
			return this.inputString.charAt(this.currentLetterIndex); 
		}
	}
	public int getNumStates() {
		return numStates;
	}
	
	//-----SETTERS-----//
	
	public void setInputString(String initInputString){ this.inputString = initInputString; }

	

	public void setCurrentLetterIndex(int currentLetterIndex) {
		this.currentLetterIndex = currentLetterIndex;
	}

	public void setCurrentState(DFAState currentState) {
		this.currentState = currentState;
	}

	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}


	
	

	
	
}
