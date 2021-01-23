package fr.ensimag.deca.tree;

import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.deca.DecacCompiler;

/**
 * Left-hand side value of an assignment.
 * 
 * @author gl16
 * @date 01/01/2021
 */
public abstract class AbstractLValue extends AbstractExpr {
	
//	public void codeCond(DecacCompiler compiler, boolean bool, Label endAnd) {
//        throw new UnsupportedOperationException("not yet implemented");
//	}
	
	@Override
	public abstract DAddr getAdresse(DecacCompiler compiler);

}
