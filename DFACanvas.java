import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.math.*;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DFACanvas extends JPanel{

	//The DFA we want to draw
	private DFA currentDFA = null;
	
	//The colors of each state/transition based on 
	//whether or not the state has been touched
	private final Color DFA_STATE_UNTOUCHED = Color.BLACK;
	private final Color DFA_STATE_CURRENT = Color.GREEN.darker();
	private final Color DFA_STATE_TOUCHED = Color.ORANGE;
	private final Color DFA_STATE_START = Color.BLUE.darker();
	private final Color DFA_TRANS_UNTOUCHED = Color.BLACK;
	private final Color DFA_TRANS_TOUCHED = Color.GRAY;

	//Pixels for drawing the DFAs
	private final int DFA_DIAM = 60;
	private final int DFA_INNER_RAD = 45;

	/**
	 * Draws the DFA's states on the canvas in different
	 * patters depending on the number of states
	 * 
	 * @param canvas
	 * 		The canvas to draw the DFA on
	 */
	public void paint(Graphics canvas){
		
		super.paint(canvas);
		
		if (currentDFA != null){
			DFAState[] states = this.currentDFA.getStates();

			//Draw the input string at the top so the user knows where they are
			this.drawInputString(canvas);
			
			//Draw the transitions for each state first so we can draw the states over them
			canvas.setColor(Color.BLACK);
			for (DFAState state : this.currentDFA.getStates()){
				//Draw a line from state 1 to state 2 for each transition
				for (char transChar : this.currentDFA.getAlphabet()){
					DFAState destState = state.readChar(transChar);
					this.drawTransition(canvas, state, transChar, destState);
				}
				
			}
			
			//Draw the states
			for (DFAState state : states){
				if (this.currentDFA.getCurrentState() == state){
					canvas.setColor(DFA_STATE_CURRENT);
				}
				else if(this.currentDFA.getStartState() == state){
					canvas.setColor(DFA_STATE_START);
				}
				else{
					canvas.setColor(DFA_STATE_UNTOUCHED);
				}
				canvas.fillOval(state.getX(), state.getY(), DFA_DIAM, DFA_DIAM);
				if (state.isAcceptState()){
					canvas.setColor(Color.WHITE);
					canvas.fillOval(state.getX()+(DFA_DIAM - DFA_INNER_RAD)/2, state.getY()+(DFA_DIAM - DFA_INNER_RAD)/2,
							DFA_INNER_RAD, DFA_INNER_RAD);
				}
				
			}
			
		}
	}
	
	/**
	 * This method draws a transition between two states
	 * 
	 * There are many cases:
	 * 	1) The state points to itself
	 * 		a) Draw a loop (semi-circle) from begState to begState
	 * 	2) endState is in Q1 with respect to begState
	 * 	3) endState is in Q2 with respect to begState
	 * 	4) endState is in Q3 with respect to begState
	 * 	5) endState is in Q4 with respect to begState
	 * 
	 * 	For 2-5 above, we need to draw the tips of the arrow (represents
	 * 	direction) accordingly
	 * 
	 * 	**This method is really gross to look at and needs lots of cleaning
	 * 
	 * @param begState
	 * 		The beginning state
	 * @param transChar
	 * 		The character the transition is going on
	 * @param endState
	 * 		The state the beginning state points to given transChar as input
	 */
	private void drawTransition(Graphics canvas, DFAState begState, char transChar, DFAState endState){
		
		//if the state is going to itself
		if (begState == endState){
			//We will draw a circle that is always going outwards from the state relative to
			//the center Y axis
			int centerX= this.getWidth()/2;
			int centerY = this.getHeight()/2;
			
			//Get the center of the state
			int stateCenterX = begState.getX() + DFA_DIAM/2;
			int stateCenterY = begState.getY() + DFA_DIAM/2;
			
			//Determine which quadrant we are in
			int deltaX = centerX - stateCenterX;
			int deltaY = centerY - stateCenterY;
			
			int ovalX = 0;
			int ovalY = 0;
			//Depending on where we are, draw the circle facing different directions
			if (deltaX != 0){
				if (deltaX > 0){
					ovalX = stateCenterX-DFA_DIAM;
					ovalY = stateCenterY-DFA_DIAM/4;
				}
				else if (deltaX < 0){
					ovalX = stateCenterX;
					ovalY = stateCenterY-DFA_DIAM/4;
				}
				canvas.drawOval(ovalX, ovalY, DFA_DIAM, DFA_DIAM/2);
			}
			//if we are on the center, look to the deltaY
			else{
				if (deltaY > 0){
					ovalX = stateCenterX-DFA_DIAM/4;
					ovalY = stateCenterY-DFA_DIAM;
				}
				else{
					ovalX = stateCenterX-DFA_DIAM/4;
					ovalY = stateCenterY;
				}
				canvas.drawOval(ovalX, ovalY, DFA_DIAM/2, DFA_DIAM);
			}
		}
		else{
			//Grab our coordinates
			int begStateCenterX = begState.getX() + DFA_DIAM/2;
			int begStateCenterY = begState.getY() + DFA_DIAM/2;
			int endStateCenterX = endState.getX() + DFA_DIAM/2;
			int endStateCenterY = endState.getY() + DFA_DIAM/2;
			
			//We can draw the line without regard to position
			canvas.drawLine(begStateCenterX, begStateCenterY, endStateCenterX, endStateCenterY);
			
			//Draw the tips of the arrows with respect to position
			
			int deltaX = endStateCenterX - begStateCenterX;
			int deltaY = endStateCenterY - begStateCenterY;
			
			//The points for each tip location
			Point tipPoint = new Point();
			Point tipTop = new Point();
			Point tipBottom = new Point();
			
			/* BEGIN TRIG MAGIC */
			
			//Find the tip of the arrow
			double tipX;
			double tipY;
			//If states are on same vertical plane
			if (deltaX == 0){
				tipX = 0;
				tipY = DFA_DIAM/2;
				
				if (deltaY < 0){
					tipTop.setLocation(endStateCenterX + .25*DFA_DIAM/2, endStateCenterY + 1.75*DFA_DIAM/2);
					tipBottom.setLocation(endStateCenterX - .25*DFA_DIAM/2, endStateCenterY + 1.75*DFA_DIAM/2);
				}
				else{
					tipTop.setLocation(endStateCenterX + .25*DFA_DIAM/2, endStateCenterY - 1.75*DFA_DIAM/2);
					tipBottom.setLocation(endStateCenterX - .25*DFA_DIAM/2, endStateCenterY - 1.75*DFA_DIAM/2);
				}
			}
			//If states are on same horizontal plane
			else if (deltaY == 0){
				tipX = DFA_DIAM/2;
				tipY = 0;
				
				if (deltaX < 0){
					tipTop.setLocation(endStateCenterX + 1.75*DFA_DIAM/2, endStateCenterY - .25*DFA_DIAM/2);
					tipBottom.setLocation(endStateCenterX + 1.75*DFA_DIAM/2, endStateCenterY + .25*DFA_DIAM/2);
				}
				else{
					tipTop.setLocation(endStateCenterX - 1.75*DFA_DIAM/2, endStateCenterY - .25*DFA_DIAM/2);
					tipBottom.setLocation(endStateCenterX - 1.75*DFA_DIAM/2, endStateCenterY + .25*DFA_DIAM/2);
				}
			}
			else{
				double theta = Math.atan(((double)deltaY)/((double)deltaX));
				tipX = DFA_DIAM/2 * Math.cos(theta);
				tipY = DFA_DIAM/2 * Math.sin(theta);
				
				//Calculate the points for each "branch" of the tip
				double thetaPrime = Math.atan(.25/1.75); //angle between tip start and end
				double hyp = Math.hypot(1.75*DFA_DIAM/2, .25*DFA_DIAM/2);
				
				
				if (deltaX > 0){
					tipTop.setLocation(endStateCenterX - hyp*Math.cos(theta-thetaPrime), 
							endStateCenterY - hyp*Math.sin(theta-thetaPrime));
					tipBottom.setLocation(endStateCenterX - hyp*Math.cos(theta+thetaPrime), 
							endStateCenterY - hyp*Math.sin(theta+thetaPrime));
				}
				else{
					tipTop.setLocation(endStateCenterX + hyp*Math.cos(theta-thetaPrime), 
							endStateCenterY + hyp*Math.sin(theta-thetaPrime));
					tipBottom.setLocation(endStateCenterX + hyp*Math.cos(theta+thetaPrime), 
							endStateCenterY + hyp*Math.sin(theta+thetaPrime));
				}
			}
			
			//Correction calculation to orient the tip of the arrow to the right location
			
			if (deltaX > 0){
				tipX = (-1) * tipX;
				tipY = (-1) * tipY;		
			}
			
			/*	
				if (deltaX == 0 && deltaY > 0){
					tipY = (-1) * tipY;
				}
			*/
			
			//Set the tip point
			tipPoint.setLocation(endStateCenterX + tipX, endStateCenterY + tipY);
			
			//Draw the whole arrow
			canvas.drawLine(begStateCenterX, begStateCenterY, endStateCenterX, endStateCenterY);
			canvas.drawLine(tipPoint.x, tipPoint.y, tipTop.x, tipTop.y);
			canvas.drawLine(tipPoint.x, tipPoint.y, tipBottom.x, tipBottom.y);
			
			/* END TRIG MAGIC*/
			
		}
	}
	
	private void drawInputString(Graphics canvas){
		char[] inputString = this.currentDFA.getInputString().toCharArray();
		int posX = this.getWidth()/4;
		int posY = this.getHeight()/10;
		
		for (int charNum = 0; charNum < inputString.length; charNum++){
			canvas.drawString(String.valueOf(inputString[charNum]), posX, posY);
			
			//Let the user know which letter they are currently on
			if (charNum == this.currentDFA.getCurrentIndex()){
				Color oldColor = canvas.getColor();
				canvas.setColor(Color.GREEN.darker());
				canvas.fillOval(posX, posY + 5, 10, 10);
				canvas.setColor(oldColor);
			}
			
			posX += 20;
		}
		
	}
	
	public void setDFA(DFA newDFA){ this.currentDFA = newDFA; }
	public int getDFADiam(){return this.DFA_DIAM;}

}
