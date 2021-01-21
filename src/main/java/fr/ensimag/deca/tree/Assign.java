package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
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
		DAddr selectAddr = getLeftOperand().getAdresse(compiler);
		//On suppose qu'il reste des registres
		//compiler.registersManag.incrementRegisterPointer();
    	getRightOperand().codeGenInst(compiler);
    	compiler.addInstruction(new STORE(Register.getR(getRP(compiler)),
    			selectAddr));
        	//compiler.registersManag.decrementRegisterPointer();
//    	else {
//    	//Executing right operand
//    	assert getRP(compiler) == 2;
//    	//On peut aussi faire codeGenInst
//    	getRightOperand().codeGenInst(compiler);;
//    	//Affectation
//    	compiler.addInstruction(new STORE(Register.getR(2),
//    			getLeftOperand().getAdresse(compiler)));
//    	}
    }




	public void codeCond(DecacCompiler compiler, boolean bool, Label endAnd) {
        throw new UnsupportedOperationException("not yet implemented");
	}
}
