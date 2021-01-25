package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.OPP;
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
    	this.setType(type);
    	return type; 
    }


    @Override
    protected String getOperatorName() {
        return "-";
    }

	@Override
	public void codeGenInst(DecacCompiler compiler) {
		if (getOperand().adressable()) {
			compiler.addInstruction(new OPP(getOperand().getAdresse(compiler),
					Register.getR(getRP(compiler))));
		}
		else {
			getOperand().codeGenInst(compiler);
			compiler.addInstruction(new OPP(Register.getR(getRP(compiler)),
					Register.getR(getRP(compiler))));
		}
		addArithFloatInstruction(compiler);
	}
}
