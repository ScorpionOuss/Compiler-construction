package fr.ensimag.deca.context;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tree.Location;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.LabelOperand;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;

import org.apache.commons.lang.Validate;

/**
 * Definition of a method
 *
 * @author gl16
 * @date 01/01/2021
 */
public class MethodDefinition extends ExpDefinition {

    @Override
    public boolean isMethod() {
        return true;
    }

    @Override
    public Label getLabel() {
        Validate.isTrue(label != null,
                "setLabel() should have been called before");
        return label;
    }
    
    @Override
    public void setLabel(Label label) {
        this.label = label;
    }

    public int getIndex() {
        return index;
    }
    

    public void setIndex(int index) {
    	this.index = index;
    }
    private int index;

    @Override
    public MethodDefinition asMethodDefinition(String errorMessage, Location l)
            throws ContextualError {
        return this;
    }

    private final Signature signature;
    private Label label;
    
    /**
     * 
     * @param type Return type of the method
     * @param location Location of the declaration of the method
     * @param signature List of arguments of the method
     * @param index Index of the method in the class. Starts from 0.
     */
    public MethodDefinition(Type type, Location location, Signature signature, int index) {
        super(type, location);
        this.signature = signature;
        this.index = index;
    }

    public Signature getSignature() {
        return signature;
    }

    @Override
    public String getNature() {
        return "method";
    }

    @Override
    public boolean isExpression() {
        return false;
    }

	public void codeTable(DecacCompiler compiler, int offset) {
		// TODO Auto-generated method stub
		compiler.addInstruction(new LOAD(new LabelOperand(getLabel()),
		Register.R0));

		compiler.addInstruction(new STORE(Register.R0, 
		new RegisterOffset(offset + getIndex(),
				Register.GB)));
	}
    
}
