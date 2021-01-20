package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.DIV;
import fr.ensimag.ima.pseudocode.instructions.QUO;
import fr.ensimag.ima.pseudocode.instructions.SUB;

/**
 *
 * @author gl16
 * @date 01/01/2021
 */
public class Divide extends AbstractOpArith {
    public Divide(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "/";
    }


	@Override
	public
	void codeExp(DecacCompiler compiler, int registerPointer) {
		/*Il faut absolument factoriser*/
		assert(registerPointer <= compiler.numberOfRegister);
		
		addZeroDivisionInstruction(compiler, registerPointer);
		if (getRightOperand().adressable()) {
			getLeftOperand().codeExp(compiler, registerPointer);
			if (getType().isInt()) {
			compiler.addInstruction(new QUO(getRightOperand().getAdresse(),
					Register.getR(registerPointer)));
			}
			else {
				assert(getType().isFloat());
				compiler.addInstruction(new DIV(getRightOperand().getAdresse(),
						Register.getR(registerPointer)));
			}
		}
		
		else {
			assert(getRightOperand().adressable() == false);
			getLeftOperand().codeExp(compiler, registerPointer);
			if (registerPointer < compiler.numberOfRegister) {
				getRightOperand().codeExp(compiler, registerPointer + 1);
				if (getType().isInt()) {
					compiler.addInstruction(new QUO(Register.getR(registerPointer + 1),
							Register.getR(registerPointer)));
					}
				else {
					assert(getType().isFloat());
					compiler.addInstruction(new DIV(Register.getR(registerPointer + 1),
							Register.getR(registerPointer)));
				}
			}
			else {
				assert(registerPointer == compiler.numberOfRegister);
				/*Manage capacity overrun*/
				depassementCapacite(compiler);

				//Minus
				if (getType().isInt()) {
					compiler.addInstruction(new QUO(Register.R1,
							Register.getR(registerPointer)));
					}
					else {
						assert(getType().isFloat());
						compiler.addInstruction(new DIV(Register.R1,
								Register.getR(registerPointer)));
					}
			}
		}
		addArithFloatInstruction(compiler);
	}

}
