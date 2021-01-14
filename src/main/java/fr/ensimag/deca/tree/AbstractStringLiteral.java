package fr.ensimag.deca.tree;

import fr.ensimag.ima.pseudocode.DVal;

/**
 *
 * @author gl16
 * @date 01/01/2021
 */
public abstract class AbstractStringLiteral extends AbstractExpr {

    public abstract String getValue();
    
	@Override
	public DVal getAdresse() {
		// TODO Auto-generated method stub
		return null;
	}

}
