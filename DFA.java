import java.util.HashMap;

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
	
	//The starting/accepting states of the DFA
	private DFAState startState;
	private DFAState[] acceptStates;
	
	//The input string for the DFA
	private char[] inputString;
	
	//The current state the DFA is on
	private DFAState currentState;
	
	//The transitions for the DFA. This array is a parallel array to
	//the alphabet array, where the index of a character in the alphabet
	//array is the index of the HashMap for it's respective transition table.
	private HashMap<DFAState, DFAState>[] transitions;
	
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
	public DFA(DFAState[] initStates, char[] initAlphabet, DFAState initStartState,
			DFAState[] initAccept, HashMap<DFAState, DFAState> transitions){
		this.states = initStates;
		this.alphabet = initAlphabet;
		this.startState = initStartState;
		this.acceptStates = initAccept;
		
		
		
	}
	
	public DFA(int initNumStates, char[] initAlphabet){
		
	}

}
