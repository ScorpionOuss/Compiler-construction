package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.REM;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 *
 * @author gl16
 * @date 01/01/2021
 */
public class Modulo extends AbstractOpArith {

    public Modulo(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");
    }


    @Override
    protected String getOperatorName() {
        return "%";
    }


	@Override
	public void codeExp(DecacCompiler compiler, int registerPointer) {
		assert(registerPointer < compiler.numberOfRegister);
		
		if (getRightOperand().adressable()) {
			getLeftOperand().codeExp(compiler, registerPointer);
			compiler.addInstruction(new REM(getRightOperand().getAdresse(),
					Register.getR(registerPointer)));
		}
		
		else {
			assert(getRightOperand().adressable() == false);
			getLeftOperand().codeExp(compiler, registerPointer);
			if (registerPointer < compiler.numberOfRegister) {
				getRightOperand().codeExp(compiler, registerPointer + 1);
				compiler.addInstruction(new REM(Register.getR(registerPointer + 1), 
						Register.getR(registerPointer)));
			}
			else {
				assert(registerPointer == compiler.numberOfRegister);
				/*Manage capacity overrun*/
				depassementCapacite(compiler);
				

				//Plus
				compiler.addInstruction(new REM(Register.R1, Register.getR(compiler.numberOfRegister)));
			}
		}
	}
}
