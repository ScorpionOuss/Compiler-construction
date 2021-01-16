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
    	Type type;
    	if (leftType.isInt()) {
    		if (rightType.isInt()) {
				type = compiler.getEnvironment().get(symbolTable.create("int")).getType();
				this.setType(type);
				return type;
    		}
    		if (rightType.isFloat()) {
    			this.setLeftOperand(new ConvFloat(this.getLeftOperand()));
				type = compiler.getEnvironment().get(symbolTable.create("float")).getType();
				this.getLeftOperand().setType(type);
				this.setType(type);
				return type;
    		}
    	}
    	if (leftType.isFloat()) {
    		if (rightType.isInt()) {
    			this.setRightOperand(new ConvFloat(this.getRightOperand()));
				type = compiler.getEnvironment().get(symbolTable.create("float")).getType();
				this.getRightOperand().setType(type);
				this.setType(type);
				return type;
    		}
    		if (rightType.isFloat()) {
				type = compiler.getEnvironment().get(symbolTable.create("float")).getType();
				this.setType(type);
				return type;
    		}
    	}
    	throw new ContextualError("Arithmetic operation not defined for the used types", this.getLocation());
    }
    

	
	public void codeCond(DecacCompiler compiler, boolean bool, Label endAnd) {
        throw new UnsupportedOperationException("not yet implemented");
	}
}
