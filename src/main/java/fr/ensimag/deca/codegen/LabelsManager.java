package fr.ensimag.deca.codegen;

public class LabelsManager {

	public LabelsManager() {
		whileCounter = 0;
		ifCounter = 0;
		andCounter = 0;
		orCounter = 0;
	}
	private int whileCounter;
	private int ifCounter;
	private int andCounter;
	private int orCounter;
	 
	/**
	  * increment and return WhileCounter
	  * @return whileCounter
	  */
    public int incrementWhileCounter() {
   	 whileCounter++;
   	 return whileCounter;
    }

    /**
     * increment and return ifCounter
     * @return ifCounter
     */
    public int incrementIfCounter() {
   	 ifCounter++;
   	 return ifCounter;
    }
    
    /**
     * Increment and return andCounter
     *
     * @return andCounter
     */
    public int incrementAndCounter() {
   	 andCounter++;
   	 return andCounter;
    }
    
    /**
     * Increments and return orCounter
     * @return orCounter
     */
    public int incrementOrCounter() {
   	 orCounter++;
   	 return orCounter;
    }
}
