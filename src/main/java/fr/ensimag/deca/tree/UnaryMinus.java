package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * @author gl16
 * @date 01/01/2021
 */
public class UnaryMinus extends AbstractUnaryExpr {

    public UnaryMinus(AbstractExpr operand) {
        super(operand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
    	Type type = this.getOperand().verifyExpr(compiler, localEnv, currentClass);
    	if (!(type.isInt() || type.isFloat())) {
    		throw new ContextualError("Unary operator - incompatible whith this expression", this.getLocation());
    	} 
    	SymbolTable symbolTable = new SymbolTable();
    	return compiler.getEnvironment().get(symbolTable.create(type.toString())).getType();
    }


    @Override
    protected String getOperatorName() {
        return "-";
    }

}
