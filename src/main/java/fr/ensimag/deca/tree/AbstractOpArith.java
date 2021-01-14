package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.deca.tools.SymbolTable;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * Arithmetic binary operations (+, -, /, ...)
 * 
 * @author gl16
 * @date 01/01/2021
 */
public abstract class AbstractOpArith extends AbstractBinaryExpr {

    public AbstractOpArith(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
    	Type leftType = this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
    	Type rightType = this.getRightOperand().verifyExpr(compiler, localEnv, currentClass);
    	SymbolTable symbolTable = new SymbolTable();
    	if (leftType.isInt()) {
    		if (rightType.isInt()) {
    			return compiler.getEnvironment().get(symbolTable.create("int")).getType();
    		}
    		if (rightType.isFloat()) {
    			return compiler.getEnvironment().get(symbolTable.create("float")).getType();
    		}
    	}
    	if (rightType.isFloat()) {
    		if (leftType.isInt()) {
    			return compiler.getEnvironment().get(symbolTable.create("float")).getType();
    		}
    		if (leftType.isFloat()) {
    			return compiler.getEnvironment().get(symbolTable.create("float")).getType();
    		}
    	}
    	throw new ContextualError("Arithmetic operation not defined for the used types", this.getLocation());
    }
    

	
	public void codeCond(DecacCompiler compiler, boolean bool, Label endAnd) {
        throw new UnsupportedOperationException("not yet implemented");
	}
}
