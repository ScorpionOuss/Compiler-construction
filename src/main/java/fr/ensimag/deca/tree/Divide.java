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

    /**
     * 
     * @param compiler
     */
    private void adressableCase(DecacCompiler compiler) {
    	if (getType().isInt()) {
			compiler.addInstruction(new QUO(getRightOperand().getAdresse(compiler),
					Register.getR(getRP(compiler))));
			}
			else {
				assert(getType().isFloat());
				compiler.addInstruction(new DIV(getRightOperand().getAdresse(compiler),
						Register.getR(getRP(compiler))));
				addArithFloatInstruction(compiler);
			}
    }
    
    /**
     * 
     * 
     * @param compiler
     */
    private void nonDepassementCase(DecacCompiler compiler) {
    	
    	compiler.registersManag.incrementRegisterPointer();
    	getRightOperand().codeGenInst(compiler);
		if (getType().isInt()) {
			compiler.addInstruction(new QUO(Register.getR(getRP(compiler)),
					Register.getR(getRP(compiler) - 1)));
			}
		else {
			assert(getType().isFloat());
			compiler.addInstruction(new DIV(Register.getR(getRP(compiler)),
					Register.getR(getRP(compiler) - 1)));
			addArithFloatInstruction(compiler);
		}
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
		if (getType().isInt()) {
			compiler.addInstruction(new QUO(Register.R1,
					Register.getR(getRP(compiler))));
			}
			else {
				assert(getType().isFloat());
				compiler.addInstruction(new DIV(Register.R1,
						Register.getR(getRP(compiler))));
				addArithFloatInstruction(compiler);
			}
    }
    
	@Override
	public void codeGenInst(DecacCompiler compiler) {		
		//remove
		addZeroDivisionInstruction(compiler);
		
		getLeftOperand().codeGenInst(compiler);;

		if (getRightOperand().adressable()) {
			adressableCase(compiler);
		}
		
		else {
	    	assert(getRightOperand().adressable() == false);
		
			getLeftOperand().codeGenInst(compiler);
			if (getRP(compiler) < getMP(compiler)) {
				nonDepassementCase(compiler);
			}
			else {
				depassementCase(compiler);
				}
			}
	}
}