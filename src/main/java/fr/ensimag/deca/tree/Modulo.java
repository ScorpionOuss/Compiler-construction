package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.REM;
import fr.ensimag.deca.tools.SymbolTable;
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
    	Type leftType = this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
    	Type rightType = this.getRightOperand().verifyExpr(compiler, localEnv, currentClass);
    	SymbolTable symbolTable = new SymbolTable();
    	if (leftType.isInt() && rightType.isInt()) {
    		Type type = compiler.getEnvironment().get(symbolTable.create("int")).getType();
    		this.setType(type);
			return type;
    	}
    	throw new ContextualError("Arithmetic operation Modulo is not defined for the used types", this.getLocation());
    }


    @Override
    protected String getOperatorName() {
        return "%";
    }

    /**
     * 
     * @param compiler
     */
    private void adressableCase(DecacCompiler compiler) {
    	compiler.addInstruction(new REM(getRightOperand().getAdresse(compiler),
				Register.getR(getRP(compiler))));
    }
    
    /**
     * 
     * @param compiler
     */
    private void nonDepassementCase(DecacCompiler compiler) {
    	compiler.registersManag.incrementRegisterPointer();

    	getRightOperand().codeGenInst(compiler);
		compiler.addInstruction(new REM(Register.getR(getRP(compiler)), 
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
		//Plus
		compiler.addInstruction(new REM(Register.R1, 
				Register.getR(getRP(compiler))));
    }
    
	@Override
	public void codeGenInst(DecacCompiler compiler) {
		int registerPointer = getRP(compiler);
		assert(getRP(compiler) <= getMP(compiler));

		addZeroDivisionInstruction(compiler);
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
		
		assert registerPointer == getRP(compiler);
	}
}
