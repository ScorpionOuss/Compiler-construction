package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.deca.tools.SymbolTable;

import java.io.PrintStream;

/**
 *
 * @author gl16
 * @date 01/01/2021
 */
public class BooleanLiteral extends AbstractExpr {

    private boolean value;

    public BooleanLiteral(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
    	SymbolTable symbolTable = new SymbolTable();
    	Type type = compiler.getEnvironment().get(symbolTable.create("boolean")).getType();
    	this.setType(type);
    	return type; 
    }


    @Override
    public void decompile(IndentPrintStream s) {
        s.print(Boolean.toString(value));
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }

    @Override
    String prettyPrintNode() {
        return "BooleanLiteral (" + value + ")";
    }

	@Override
	public
	void codeGenInst(DecacCompiler compiler) {
		if (value) {
			compiler.addInstruction(new LOAD(new ImmediateInteger(1),
					Register.getR(getRP(compiler))));

		}
		else {
			compiler.addInstruction(new LOAD(new ImmediateInteger(0),
					Register.getR(getRP(compiler))));

		}
	}

	@Override
	public boolean adressable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DVal getAdresse(DecacCompiler compiler) {
		// TODO Auto-generated method stub
		return null;
	}

	public void codeCond(DecacCompiler compiler, boolean bool, Label etiquette) {
		if (bool == value) {
			compiler.addInstruction(new BRA(etiquette));
		}
	}
}
