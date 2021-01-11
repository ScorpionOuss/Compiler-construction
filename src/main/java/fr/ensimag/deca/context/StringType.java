package fr.ensimag.deca.context;

import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.ima.pseudocode.DVal;

/**
 *
 * @author Ensimag
 * @date 01/01/2021
 */
public class StringType extends Type {

    public StringType(SymbolTable.Symbol name) {
        super(name);
    }

    @Override
    public boolean isString() {
        return true;
    }

    @Override
    public boolean sameType(Type otherType) {
    	// implemented
    	return otherType.isString();
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
