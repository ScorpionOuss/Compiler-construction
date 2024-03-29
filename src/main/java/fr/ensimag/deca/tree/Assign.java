package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.POP;
import fr.ensimag.ima.pseudocode.instructions.PUSH;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * Assignment, i.e. lvalue = expr.
 *
 * @author gl16
 * @date 01/01/2021
 */
public class Assign extends AbstractBinaryExpr {

    @Override
    public AbstractLValue getLeftOperand() {
        // The cast succeeds by construction, as the leftOperand has been set
        // as an AbstractLValue by the constructor.
        return (AbstractLValue)super.getLeftOperand();
    }

    public Assign(AbstractLValue leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
    	Type leftType = this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
    	AbstractExpr expr = this.getRightOperand().verifyRValue(compiler, localEnv, currentClass, leftType);
		this.setRightOperand(expr);
    	this.setType(leftType);
    	return leftType;
    }


    @Override
    protected String getOperatorName() {
        return "=";
    }
    
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
    	selectionGen(compiler);
    }




	private void selectionGen(DecacCompiler compiler) {
    	DAddr selectAddr = getLeftOperand().getAdresse(compiler);
    	assert(selectAddr != null);
    	//On suppose qu'il reste des registres
		if (getRP(compiler) < getMP(compiler)) {
    	compiler.registersManag.incrementRegisterPointer();
    	getRightOperand().codeGenInst(compiler);
    	compiler.addInstruction(new STORE(Register.getR(getRP(compiler)),
    			selectAddr));
		compiler.addInstruction(new LOAD(selectAddr, Register.getR(getRP(compiler) - 1)));
    	compiler.registersManag.decrementRegisterPointer();
		}
		else {
			depassementCapacite(compiler);
			compiler.addInstruction(new STORE(Register.R1,
	    			selectAddr));
			compiler.addInstruction(new LOAD(selectAddr, Register.getR(getRP(compiler) - 1)));
		}
	}
	


	public void codeCond(DecacCompiler compiler, boolean bool, Label endAnd) {
        throw new UnsupportedOperationException("not yet implemented");
	}
}
