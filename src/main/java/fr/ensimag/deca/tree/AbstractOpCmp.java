package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.CMP;
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
public abstract class AbstractOpCmp extends AbstractBinaryExpr {

    public AbstractOpCmp(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
    	Type leftType = this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
    	Type rightType = this.getRightOperand().verifyExpr(compiler, localEnv, currentClass);
    	SymbolTable symbolTable = new SymbolTable();
    	Type type;
    	if ((leftType.isInt() || leftType.isFloat()) && (rightType.isInt() || rightType.isFloat())) {
    		type = compiler.getEnvironment().get(symbolTable.create("boolean")).getType();
    		this.setType(type);
			return type; 
    	}
    	if (leftType.isClassOrNull() && rightType.isClassOrNull()) {
    		type = compiler.getEnvironment().get(symbolTable.create("boolean")).getType();
    		this.setType(type);
			return type; 
    	}
    	if (leftType.isBoolean() && rightType.isBoolean()) {
    		type = compiler.getEnvironment().get(symbolTable.create("boolean")).getType();
    		this.setType(type);
			return type; 
    	}
    	throw new ContextualError("The binary operation used is not defined for the operands types", this.getLocation());
    }
    
    /**
     * 
     * @param compiler
     */
    private void adressableCase(DecacCompiler compiler) {
    	compiler.addInstruction(new CMP(getRightOperand().getAdresse(compiler),
				Register.getR(getRP(compiler))));
    }

    /**
     * 
     * @param compiler
     */
    private void nonDepassementCase(DecacCompiler compiler) {
    	compiler.registersManag.incrementRegisterPointer();
    	getRightOperand().codeGenInst(compiler);
    	compiler.addInstruction(new CMP(Register.getR(getRP(compiler)), 
				Register.getR(getRP(compiler) - 1)));
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
		
		compiler.addInstruction(new CMP(Register.R1, 
				Register.getR(getRP(compiler))));
    }
    
    /**
     * 
     * @param compiler
     */
    protected void codeCMP(DecacCompiler compiler) {
    	int registerPointer = getRP(compiler);
    	
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
