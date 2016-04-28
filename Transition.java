
/**
 * Defines a raw transition as it is taken from the transition wizard
 * @author Morgan
 *
 */
public class Transition {

	private int beginningState;
	private String transitionCharacter;
	private int endingState;
	
	public Transition(){
		//Do nothing
	}
	
	public Transition(int initBegState, String initTransChar, int initEndState){
		this.beginningState = initBegState;
		this.transitionCharacter = initTransChar;
		this.endingState = initEndState;
	}

	//-----GETTERS-----//
	public int getBeginningState() { return beginningState; }
	public String getTransitionCharacter() { return transitionCharacter; }
	public int getEndingState() { return endingState; }

	//-----SETTERS-----//
	public void setBeginningState(int beginningState) { this.beginningState = beginningState; }
	public void setTransitionCharacter(String transitionCharacter) { this.transitionCharacter = transitionCharacter; }
	public void setEndingState(int endingState) { this.endingState = endingState; }
	
	
	
}
