package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.MUL;

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
		assert(registerPointer < compiler.numberOfRegister);
		
		getLeftOperand().codeExp(compiler, registerPointer);
		if (registerPointer < compiler.numberOfRegister) {
			getRightOperand().codeExp(compiler, registerPointer + 1);
			compiler.addInstruction(new MUL(Register.getR(registerPointer + 1), 
					Register.getR(registerPointer)));
		}
		else {
			assert(registerPointer == compiler.numberOfRegister);
			/*Manage capacity overrun*/
			depassementCapacite(compiler);
			//Plus
			compiler.addInstruction(new MUL(Register.R1,
					Register.getR(compiler.numberOfRegister)));
		}
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
