package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.ADD;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.POP;
import fr.ensimag.ima.pseudocode.instructions.PUSH;
import fr.ensimag.ima.pseudocode.instructions.SUB;

/**
 * @author gl16
 * @date 01/01/2021
 */
public class Minus extends AbstractOpArith {
    public Minus(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "-";
    }


	@Override
	public
	void codeExp(DecacCompiler compiler, int registerPointer) {
		assert(registerPointer <= compiler.numberOfRegister);

		if (getRightOperand().adressable()) {
			getLeftOperand().codeExp(compiler, registerPointer);
			compiler.addInstruction(new SUB(getRightOperand().getAdresse(),
					Register.getR(registerPointer)));
		}
		
		else {
			assert(getRightOperand().adressable() == false);
			getLeftOperand().codeExp(compiler, registerPointer);
			if (registerPointer < compiler.numberOfRegister) {
				getRightOperand().codeExp(compiler, registerPointer + 1);
				compiler.addInstruction(new SUB(Register.getR(registerPointer + 1), 
						Register.getR(registerPointer)));
			}
			else {
				assert(registerPointer == compiler.numberOfRegister);
				/*Manage capacity overrun*/
				depassementCapacite(compiler);

				//Minus
				compiler.addInstruction(new SUB(Register.R1, Register.getR(registerPointer))); 

			}
		}
		addArithFloatInstruction(compiler);
	}
}
