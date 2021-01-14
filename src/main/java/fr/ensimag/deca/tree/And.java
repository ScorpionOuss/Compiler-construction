package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.Label;

/**
 *
 * @author gl16
 * @date 01/01/2021
 */
public class And extends AbstractOpBool {

    public And(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return "&&";
    }

	@Override
	public
	void codeExp(DecacCompiler compiler, int registerPointer) {
		// TODO Auto-generated method stub
        throw new UnsupportedOperationException("not yet implemented");
	}


	public void codeCond(DecacCompiler compiler, boolean bool, Label etiquette) {
		
		//〈Code(C,faux,E)〉
		if (!bool) {
			getLeftOperand().codeCond(compiler, bool, etiquette);
			getRightOperand().codeCond(compiler, bool, etiquette);
		}
		//〈Code(C,vrai,E)〉
		else {
			Label endAnd = new Label("endAnd");
			getLeftOperand().codeCond(compiler, !bool, endAnd);
			getRightOperand().codeCond(compiler, bool, etiquette);
			compiler.addLabel(endAnd);
		}
	}
}
