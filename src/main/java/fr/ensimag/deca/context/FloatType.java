package fr.ensimag.deca.context;

import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.ImmediateFloat;

/**
 *
 * @author Ensimag
 * @date 01/01/2021
 */
public class FloatType extends Type {

    public FloatType(SymbolTable.Symbol name) {
        super(name);
    }

    @Override
    public boolean isFloat() {
        return true;
    }

    @Override
    public boolean sameType(Type otherType) {
    	// implemented
    	return otherType.isFloat();
    }
    
    @Override
    public boolean assignCompatible(Type otherType) {
    	return otherType.isInt();
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
