package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.FLOAT;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * Conversion of an int into a float. Used for implicit conversions.
 * 
 * @author gl16
 * @date 01/01/2021
 */
public class ConvFloat extends AbstractUnaryExpr {
    public ConvFloat(AbstractExpr operand) {
        super(operand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) {
    	SymbolTable symbolTable = new SymbolTable();
    	Type type = compiler.getEnvironment().get(symbolTable.create("float")).getType();
    	this.setType(type);
    	return type; 
    }

    @Override
    protected String getOperatorName() {
        return "/* conv float */";
    }
    
	@Override
	public void codeExp(DecacCompiler compiler, int registerPointer) {
		getOperand().codeExp(compiler, registerPointer);
		compiler.addInstruction(new FLOAT(Register.getR(registerPointer),
				Register.getR(registerPointer)));
	}
}
