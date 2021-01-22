package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;

/**
 *
 * @author gl16
 * @date 01/01/2021
 */
public class Or extends AbstractOpBool {

    public Or(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return "||";
    }

	@Override
	public void codeGenInst(DecacCompiler compiler){
		int registerPointer = getRP(compiler);
		assert(getRP(compiler) <= getMP(compiler));
		AbstractExpr newNot = new Not(new And(new Not(getLeftOperand()),
				new Not(getRightOperand())));
		newNot.codeGenInst(compiler);
		
		assert registerPointer == getRP(compiler);
	}
	
	public void codeCond(DecacCompiler compiler, boolean bool, Label etiquette) {
		
		//〈Code(C, vrai, E)〉
		if (bool) {
			getLeftOperand().codeCond(compiler, bool, etiquette);
			getRightOperand().codeCond(compiler, bool, etiquette);
		}
		//〈Code(C, faux, E)〉
		else {
			Label endOr = new Label("endOr" + 
					String.valueOf(compiler.labelsManager.incrementOrCounter()));
			getLeftOperand().codeCond(compiler, !bool, endOr);
			getRightOperand().codeCond(compiler, bool, etiquette);
			compiler.addLabel(endOr);
		}
	}
}
