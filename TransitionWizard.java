import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.Component;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


@SuppressWarnings("serial")
public class TransitionWizard extends JFrame implements ActionListener {

	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	//Constants that define the radio button what was selected
	private final String stateOne = "0";
	private final String stateTwo = "1";
	private final String stateThree = "2";
	private final String stateFour = "3";
	private final String stateFive = "4";
	
	//The radio buttons that resemble the states in the DFA
	private JRadioButton radStateOne, radStateTwo, radStateThree, radStateFour, radStateFive;
	
	//The next button, initiates the next transition
	private JButton btnNext;
	
	private JLabel lblState, lblAlphabetChar;
	
	//These are the responses to each question
	private ArrayList<Transition> answers = new ArrayList<>();
	
	//The DFA we are generating the Transitions for
	private DFA currentDFA;
	
	//The current state we are building a transition for
	private String begState;
	
	//The current alphabet character we are asking about
	private String alphabetChar;
	
	//The question we are on, used to determine which state/char is next
	private int transitionCount;
	
	//Whether or not the transition wizard is done
	private boolean isFinished;
	
	//The parent component that started this frame, will be disabled until
	//the transition wizard finishes
	private Component parentComponent;
	

	/**
	 * Create the frame.
	 */
	public TransitionWizard(DFA theDFA, Component parent) {
		setTitle("Transitions");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 250, 287);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSelectTransition = new JLabel("Select transition from state:");
		lblSelectTransition.setBounds(10, 11, 158, 14);
		contentPane.add(lblSelectTransition);
		
		lblState = new JLabel("1");
		lblState.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblState.setBounds(178, 11, 46, 14);
		contentPane.add(lblState);
		
		JLabel lblOnAlphabetCharacter = new JLabel("On alphabet character:");
		lblOnAlphabetCharacter.setBounds(10, 36, 158, 14);
		contentPane.add(lblOnAlphabetCharacter);
		
		lblAlphabetChar = new JLabel("x");
		lblAlphabetChar.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblAlphabetChar.setBounds(178, 36, 46, 14);
		contentPane.add(lblAlphabetChar);
		
		radStateOne = new JRadioButton("1");
		buttonGroup.add(radStateOne);
		radStateOne.setBounds(20, 82, 158, 23);
		radStateOne.setSelected(true);
		contentPane.add(radStateOne);
		
		radStateTwo = new JRadioButton("2");
		buttonGroup.add(radStateTwo);
		radStateTwo.setBounds(20, 108, 158, 23);
		contentPane.add(radStateTwo);
		
		radStateThree = new JRadioButton("3");
		buttonGroup.add(radStateThree);
		radStateThree.setBounds(20, 134, 158, 23);
		contentPane.add(radStateThree);
		
		radStateFour = new JRadioButton("4");
		buttonGroup.add(radStateFour);
		radStateFour.setBounds(20, 160, 158, 23);
		contentPane.add(radStateFour);
		
		radStateFive = new JRadioButton("5");
		buttonGroup.add(radStateFive);
		radStateFive.setBounds(20, 185, 158, 23);
		contentPane.add(radStateFive);
		
		JLabel lblToState = new JLabel("To state:");
		lblToState.setBounds(10, 61, 158, 14);
		contentPane.add(lblToState);
		
		btnNext = new JButton("Next");
		btnNext.addActionListener(this);
		btnNext.setBounds(135, 214, 89, 23);
		contentPane.add(btnNext);
		
		//Initialize our DFA and our question variables
		this.currentDFA = theDFA;
		transitionCount = 0;
		this.begState = this.findNextState();
		this.alphabetChar = this.findNextAlphabetChar();
		this.updateTextFields();
		
		//Disable the radio buttons that cannot be used
		switch (theDFA.getNumStates()){
		case 1:
			radStateTwo.setEnabled(false);
			radStateThree.setEnabled(false);
			radStateFour.setEnabled(false);
			radStateFive.setEnabled(false);
			break;
		case 2:
			radStateThree.setEnabled(false);
			radStateFour.setEnabled(false);
			radStateFive.setEnabled(false);
			break;
		case 3:
			radStateFour.setEnabled(false);
			radStateFive.setEnabled(false);
			break;
		case 4:
			radStateFive.setEnabled(false);
			break;
		}
		
		//Tell everyone we're busy
		this.isFinished = false;
		
		//Initialize and stop our parent component
		this.parentComponent = parent;
		this.parentComponent.setEnabled(false);
	}
	
	/**
	 * Determines which radio button is pressed right now
	 * 
	 * @return
	 * 		A constant value of the TransitionWizard class that represents
	 * 		the radio button that was selected
	 */
	public String selectedRadioButton(){
		
		if (this.radStateOne.isSelected()){
			return this.stateOne;
		}
		else if (this.radStateTwo.isSelected()){
			return this.stateTwo;
		}
		else if (this.radStateThree.isSelected()){
			return this.stateThree;
		}
		else if (this.radStateFour.isSelected()){
			return this.stateFour;
		}
		else{
			return this.stateFive;
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		//Only do something if it was the next button that was pressed
		if (e.getSource() == this.btnNext){
			//Store the transition in our answer list
			Transition transition = new Transition(Integer.parseInt(this.begState), this.alphabetChar, 
					Integer.parseInt(this.selectedRadioButton()));
			this.answers.add(transition);
			
			//Give the state the new transition
			this.currentDFA.addTransition(transition);
			
			//Update to a new selection
			transitionCount++;
			this.begState = this.findNextState();
			this.alphabetChar = this.findNextAlphabetChar();
			
			//Continue updating until we've asked all the questions
			if (this.begState != null && this.alphabetChar != null){
				this.updateTextFields();	
			}
			//At this point we're done asking questions, so we can stop the wizard
			else{
				this.parentComponent.setEnabled(true);
				this.setVisible(false);
			}
			
		}
		
	}
	
	/**
	 * Determines which state we are currently on
	 * 
	 * @return
	 * 		A string value ("0" through "5") representing which state we are currently on
	 */
	private String findNextState(){
		
		//Keep track of the total and which state we are on
		int count = 0;
		int stateCount = 0;
		for (DFAState state : this.currentDFA.getStates()){
			
			for (char character : this.currentDFA.getAlphabet()){
				//If we have found which number we're on, finish
				if (count == this.transitionCount){
					return String.valueOf(stateCount);
				}
				else{
					count++;
				}
			}
		
			stateCount++;
		
		}
		
		//If we get to this point then we have reached the end of the states
		return null;
		
	}
	
	/**
	 * Determines which alphabet character we are currently on
	 * 
	 * @return
	 * 		A string containing the current character we are asking about
	 */
	private String findNextAlphabetChar(){
		
		//Keep track of the total and which state we are on
		int count = 0;
		for (DFAState state : this.currentDFA.getStates()){
			
			for (char character : this.currentDFA.getAlphabet()){
				//If we have found which number we're on, finish
				if (count == this.transitionCount){
					return String.valueOf(character);
				}
				else{
					count++;
				}
			}
			
		}
		
		//If we get to this point then we have reached the end of the states
		return null;
	}
	
	/**
	 * Builds the transitions
	 */
	private void buildTransitions(){
		
	}
	
	/**
	 * Updates the text fields to advance the questions onto the next state
	 */
	private void updateTextFields(){
		//Set the text variables
		this.lblState.setText(String.valueOf(Integer.parseInt(this.begState)+1));
		this.lblAlphabetChar.setText(this.alphabetChar);
		
		//Reset the radio buttons
		this.radStateOne.setSelected(true);
	
	}
	
	//-----GETTERS-----//

	public String getStateOne() { return stateOne; }
	public String getStateTwo() { return stateTwo; }
	public String getStateThree() { return stateThree; }
	public String getStateFour() { return stateFour; }
	public String getStateFive() { return stateFive; }
	public ArrayList<Transition> getTransitions() { return this.answers; }
	public boolean isFinished() {return this.isFinished; }
}
