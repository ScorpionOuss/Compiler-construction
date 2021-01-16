package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.ADD;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.POP;
import fr.ensimag.ima.pseudocode.instructions.PUSH;

/**
 * @author gl16
 * @date 01/01/2021
 */
public class Plus extends AbstractOpArith {
    public Plus(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }
 

    @Override
    protected String getOperatorName() {
        return "+";
    }


	@Override
	public void codeExp(DecacCompiler compiler, int registerPointer) {
		assert(registerPointer <= compiler.numberOfRegister);
		
		if (getRightOperand().adressable()) {
			getLeftOperand().codeExp(compiler, registerPointer);
			compiler.addInstruction(new ADD(getRightOperand().getAdresse(),
					Register.getR(registerPointer)));
		}
		
		else {
			assert(getRightOperand().adressable() == false);
			getLeftOperand().codeExp(compiler, registerPointer);
			if (registerPointer < compiler.numberOfRegister) {
				getRightOperand().codeExp(compiler, registerPointer + 1);
				compiler.addInstruction(new ADD(Register.getR(registerPointer + 1), 
						Register.getR(registerPointer)));
			}
			else {
				assert(registerPointer == compiler.numberOfRegister);
				/*Manage capacity overrun*/
				depassementCapacite(compiler);
				

				//Plus
				compiler.addInstruction(new ADD(Register.R1, Register.getR(compiler.numberOfRegister)));
			}
		}
		addArithFloatInstruction(compiler);
	}

}
