package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.MUL;

/**
 * @author gl16
 * @date 01/01/2021
 */
public class Multiply extends AbstractOpArith {
    public Multiply(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "*";
    }


    /**
     * 
     * @param compiler
     */
    private void adressableCase(DecacCompiler compiler) {
		compiler.addInstruction(new MUL(getRightOperand().getAdresse(),
				Register.getR(getRP(compiler))));
    }
    
    /**
     * 
     * @param compiler
     */
    private void nonDepassementCase(DecacCompiler compiler) {
    	compiler.registersManag.incrementRegisterPointer();

    	getRightOperand().codeGenInst(compiler);;
		
    	compiler.addInstruction(new MUL(Register.getR(getRP(compiler)), 
				Register.getR(getRP(compiler)- 1)));
		
		compiler.registersManag.decrementRegisterPointer();

    }
    
    /**
     * 
     * @param compiler
     */
    private void depassementCase(DecacCompiler compiler) {
    	assert(getRP(compiler) == getMP(compiler));
		/*Manage capacity overrun*/
		depassementCapacite(compiler);

		//Minus
		compiler.addInstruction(new MUL(Register.R1, Register.getR(getRP(compiler))));
    }
    
	@Override
	public void codeGenInst(DecacCompiler compiler) {
		assert(getRP(compiler) <= getMP(compiler));
		getLeftOperand().codeGenInst(compiler);
		
		if (getRightOperand().adressable()) {
			adressableCase(compiler);
		}
		
		else {
			assert(getRightOperand().adressable() == false);
			if (getRP(compiler) < getMP(compiler)) {
				nonDepassementCase(compiler);
			}
			else {
				depassementCase(compiler);
			}
		}
		//addArithFloatInstruction(compiler);
	}

}
