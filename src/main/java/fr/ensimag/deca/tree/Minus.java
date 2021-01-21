package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Register;
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

    /**
     * 
     * @param compiler
     */
    private void adressableCase(DecacCompiler compiler) {
		compiler.addInstruction(new SUB(getRightOperand().getAdresse(compiler),
				Register.getR(getRP(compiler))));
    }
    
    /**
     * 
     * @param compiler
     */
    private void nonDepassementCase(DecacCompiler compiler) {
    	compiler.registersManag.incrementRegisterPointer();

    	getRightOperand().codeGenInst(compiler);;
		
    	compiler.addInstruction(new SUB(Register.getR(getRP(compiler)), 
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
		compiler.addInstruction(new SUB(Register.R1, Register.getR(getRP(compiler))));
    }
    
	@Override
	public void codeGenInst(DecacCompiler compiler) {
		int registerPointer = getRP(compiler);
		
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
		addArithFloatInstruction(compiler);
		
		assert registerPointer == getRP(compiler);
	}
        

}
