package fr.ensimag.deca.context;

import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.ImmediateInteger;

/**
 *
 * @author Ensimag
 * @date 01/01/2021
 */
public class IntType extends Type {

    public IntType(SymbolTable.Symbol name) {
        super(name);
    }

    @Override
    public boolean isInt() {
        return true;
    }

    @Override
    public boolean sameType(Type otherType) {
    	// implemented
    	return otherType.isInt();
    }

	@Override
	public boolean assignCompatible(Type otherType) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean subType(Type otherType) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean castCompatible(Type otherType) {
		// TODO Auto-generated method stub
		return false;
	}


}
