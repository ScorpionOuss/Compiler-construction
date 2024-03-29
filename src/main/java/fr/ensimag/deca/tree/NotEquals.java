package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.SGT;
import fr.ensimag.ima.pseudocode.instructions.SNE;

/**
 *
 * @author gl16
 * @date 01/01/2021
 */
public class NotEquals extends AbstractOpExactCmp {

    public NotEquals(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "!=";
    }


	@Override
	public
	void codeGenInst(DecacCompiler compiler) {
		int registerPointer = getRP(compiler);
		
		//CMP operands
		codeCMP(compiler);
		//Scc instruction
		compiler.addInstruction(new SNE(Register.getR(getRP(compiler))));
		
		assert registerPointer == getRP(compiler);
	}

	public void codeCond(DecacCompiler compiler, boolean bool, Label etiquette) {
		//We use the Equals method
		Equals equal = new Equals(getLeftOperand(), getRightOperand());
		equal.codeCond(compiler, !bool, etiquette);
	}
}
