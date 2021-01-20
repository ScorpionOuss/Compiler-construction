package fr.ensimag.deca.codegen;

import java.util.LinkedList;

public class StackManager {
	
	public StackManager() {
		listStackCounters = new LinkedList<Integer>();
		methodStackCounter = 2;//Concernant le super type Object
		stackCounterMax = 0;
		numberCurrentVariables = 0;
	}
	
    private int methodStackCounter;
    private int stackCounterMax;
    private int numberCurrentVariables;
    
    private LinkedList<Integer> listCurrentVariable;
    private LinkedList<Integer> listStackCounters;

    /**
     * 
     * @param i
     */
    public void incrementMethodStackCounter(int i) {
    	methodStackCounter += i;
    }
    
    /**
     * 
     * @return methodStackCounter
     */
    public int getMethodStackCounter() {
    	return methodStackCounter;
    }
    /**
     * 
     * @param i
     */
    public void incrementStackCounterMax(int i) {
    	stackCounterMax += i;
    }

    /**
     * 
     * @return stackCounterMax
     */
    public int getStackCounterMax() {
    	return stackCounterMax;
    }
    
    /**
     * 
     * @return numberCurrentVariables
     */
    public int recoverAndIncrement() {
   	 numberCurrentVariables++;
   	 return numberCurrentVariables;
    }
    
    /**
     * save stackCounter
     */
    public void saveStackPointers() {
    	listStackCounters.add(stackCounterMax);
    	stackCounterMax = 0;
    	
    	listCurrentVariable.add(numberCurrentVariables);
    	numberCurrentVariables = 0;
    }
    
    /**
     * restore stackCounter
     */
    public void restoreStackPointers() {
    	numberCurrentVariables = listCurrentVariable.removeLast();
    	stackCounterMax = listStackCounters.removeLast();
    }
}
