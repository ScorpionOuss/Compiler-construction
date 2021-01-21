package fr.ensimag.deca.context;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tree.Location;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.RegisterOffset;

/**
 * Definition of a Deca type (builtin or class).
 *
 * @author gl16
 * @date 01/01/2021
 */
public class TypeDefinition extends Definition {

    public TypeDefinition(Type type, Location location) {
        super(type, location);
    }
    
    private DAddr operand;

    @Override
    public String getNature() {
        return "type";
    }

    @Override
    public boolean isExpression() {
        return false;
    }

	@Override
	public void setOperand(DAddr operand) {
		this.operand = operand;
	}

	@Override
	public DAddr getOperand() {
		// TODO Auto-generated method stub
		//il faut faire une exception.
		return operand;
	}
}
