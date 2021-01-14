package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
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
    	if ((leftType.isInt() || leftType.isFloat()) && (rightType.isInt() || rightType.isFloat())) {
			return compiler.getEnvironment().get(symbolTable.create("boolean")).getType();
    	}
    	if (leftType.isClassOrNull() && rightType.isClassOrNull()) {
			return compiler.getEnvironment().get(symbolTable.create("boolean")).getType();
    	}
    	if (leftType.isBoolean() && rightType.isBoolean()) {
			return compiler.getEnvironment().get(symbolTable.create("boolean")).getType();
    	}
    	throw new ContextualError("The binary operation used is not defined for the operands types", this.getLocation());
    }


}
