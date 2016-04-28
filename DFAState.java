import java.awt.Point;
import java.util.HashMap;

@SuppressWarnings("unused")
public class DFAState {
	
	//----------------
	//Variables
	//----------------
	
	//The transitions for the state
	private HashMap<String, DFAState> transitions = new HashMap<>();
	
	//Whether or not this state is an accept state
	private boolean isAcceptState;
	
	//Whether or not this state is a starting state
	private boolean isStartState;
	
	//The position of the state on the canvas
	private int x;
	private int y;
	
	//The name for the state
	private String stateID;
	
	
	//----------------
	//Methods
	//----------------
	
	/**
	 * Constructor for the DFAState. Makes a state with the given transitions,
	 * name, and accept state status.
	 * 
	 * @param transitions
	 * 		The transitions for the DFAState
	 * @param initID
	 * 		The ID of the DFAState
	 * @param initIsAccept
	 * 		Whether or not this state is an accept state
	 * 		
	 */
	public DFAState(HashMap<String, DFAState> transitions, String initID, boolean initIsAccept){
		
	}
	
	/**
	 * Alternate Constructor, creates a blank state
	 */
	public DFAState(){
		this.x = 0;
		this.y = 0;
	}
	
	
	/**
	 * Returns the state that this state is pointing to on the given
	 * input
	 * 
	 * @param nextChar
	 * 		The next character to be read in the input string
	 * @return
	 * 		The state that the DFA would be in if this character
	 * 		was read.
	 */
	public DFAState readChar(char nextChar){
		return transitions.get(String.valueOf(nextChar));
	}
	
	/**
	 * Adds a new transition to this state's transition map
	 * 
	 * @param alphaChar
	 * 		The character the transition will be for
	 * @param dest
	 * 		The state this transition points to
	 */
	public void addTransition(String alphaChar, DFAState dest){
		this.transitions.put(alphaChar, dest);
	}
	
	
	//-----GETTERS-----//
	
	public HashMap<String, DFAState> getTransitions(){ return this.transitions; }
	public String getID(){ return this.stateID; }
	public boolean isAcceptState() {return this.isAcceptState; }
	public boolean isStartState() {return this.isStartState; }
	public String getStateID() { return stateID; }
	public int getX(){ return this.x; }
	public int getY(){ return this.y; }
	
	//-----SETTERS-----//
	
	public void setIsAccept(boolean initIsAccept){
		this.isAcceptState = initIsAccept;
	}
	
	public void setIsStart(boolean initIsStart){
		this.isStartState = initIsStart;
	}
	
	public void setStateID(String stateID) {
		this.stateID = stateID;
	}
	
	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
	}
}
