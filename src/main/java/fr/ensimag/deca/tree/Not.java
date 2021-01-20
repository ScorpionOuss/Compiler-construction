package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.SEQ;
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
    	this.setType(type);
    	return type; 
    }


    @Override
    protected String getOperatorName() {
        return "!";
    }

	@Override
	public
	void codeExp(DecacCompiler compiler, int registerPointer) {
		getOperand().codeExp(compiler, registerPointer);
		compiler.addInstruction(new CMP(new ImmediateInteger(0), 
				Register.getR(registerPointer)));
		compiler.addInstruction(new SEQ(Register.getR(registerPointer)));
	}
	
	@Override
	public void codeCond(DecacCompiler compiler, boolean bool, Label etiquette) {
		getOperand().codeCond(compiler, !bool, etiquette);
	}
	
}
