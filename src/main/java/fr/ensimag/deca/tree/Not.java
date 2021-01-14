package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.Label;
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
public class Not extends AbstractUnaryExpr {

    public Not(AbstractExpr operand) {
        super(operand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
    	Type type = this.getOperand().verifyExpr(compiler, localEnv, currentClass);
    	if (!type.isBoolean()) {
    		throw new ContextualError("Not operator incompatible whith this expression", this.getLocation());
    	} 
    	SymbolTable symbolTable = new SymbolTable();
    	return compiler.getEnvironment().get(symbolTable.create("boolean")).getType();
    }


    @Override
    protected String getOperatorName() {
        return "!";
    }

	@Override
	public
	void codeExp(DecacCompiler compiler, int registerPointer) {
        throw new UnsupportedOperationException("not yet implemented");
		
	}
	
	@Override
	public void codeCond(DecacCompiler compiler, boolean bool, Label etiquette) {
		getOperand().codeCond(compiler, !bool, etiquette);
	}
	
}
