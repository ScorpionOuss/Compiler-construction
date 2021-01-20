package fr.ensimag.deca.codegen;

import java.util.LinkedList;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.POP;
import fr.ensimag.ima.pseudocode.instructions.PUSH;

public class RegistersManager {
	
	private LinkedList<Integer> listSavedPointers; 
	private int registerPointer;
	private int numberOfRegister = 15;

	public RegistersManager() {
		listSavedPointers = new LinkedList<Integer>();
		registerPointer = 2;
	}
	
	 /*
     * Increments the register Pointer
     */
    public void incrementRegisterPointer() {
   	 registerPointer++;
    }
    
    /*
     * Decrements Register Pointer
     */
    public void decrementRegisterPointer() {
   	 registerPointer--;
    }
    
    /*
     * Getter of registerPointer
     */
    public int getRegisterPointer() {
   	 return registerPointer;
    }
    
    /**
     * Getter of numberOfRegister
     */
    public int getNumberOfRegister() {
      	 return numberOfRegister;
       }
    
    /**
     * Setter of numberOfRegister
     */
    public void setNumberOfRegister(int nb) {
    	numberOfRegister = nb;
    }
    
    /**
     * Save registers
     * @param compiler
     */
    public void saveRegisters(DecacCompiler compiler) {
    	for (int i = 2; i <= registerPointer; i++) {
    		compiler.addInstruction(new PUSH(Register.getR(i)));
    	}
    	listSavedPointers.add(registerPointer);
    	registerPointer = 2;
    }

    /**
     * Restore registers
     * @param compiler
     */
	public void restoreRegisters(DecacCompiler compiler) {
		registerPointer = listSavedPointers.removeLast();
		
		for(int i = registerPointer; i >= 2; i--) {
			compiler.addInstruction(new POP(Register.getR(i)));
		}
	}
}
