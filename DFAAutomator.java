/**
 * Runs a DFA through all of its transitions and then repaints
 * the canvas that it is drawn on. 
 * @author Morgan Webber
 *
 */
public class DFAAutomator implements Runnable{

	//The DFA we want to automate
	private DFA activeDFA;
	
	//The canvas the DFA is drawn onto
	private DFACanvas canvas;
	
	/**
	 * Default Ctor
	 * 
	 * @param initActiveDFA
	 * 		The DFA this automator is in charge of
	 * @param initCanvas
	 * 		The DFACanvas that the DFA is drawn onto
	 */
	public DFAAutomator(DFA initActiveDFA, DFACanvas initCanvas){
		this.activeDFA = initActiveDFA;
		this.canvas = initCanvas;
	}
	
	
	@Override
	public void run() {
		//Run the user through the DFA
		while (this.activeDFA.getCurrentIndex() < this.activeDFA.getInputString().length()-1){
			this.activeDFA.makeTransition();
		}
		
		//Redraw the canvas
		this.canvas.repaint();
		
	}

}
