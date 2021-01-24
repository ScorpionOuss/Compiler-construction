package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.ImmediateFloat;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.NullOperand;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.LOAD;

import java.io.PrintStream;

/**
 * Absence of initialization (e.g. "int x;" as opposed to "int x =
 * 42;").
 *
 * @author gl16
 * @date 01/01/2021
 */
public class NoInitialization extends AbstractInitialization {

	private Type type;
	
	public Type getType() {
		return type;
	}
	
    @Override
    protected void verifyInitialization(DecacCompiler compiler, Type t,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
    	type = t;
    }


    /**
     * Node contains no real information, nothing to check.
     */
    @Override
    protected void checkLocation() {
        // nothing
    }

    @Override
    public void decompile(IndentPrintStream s) {
        // nothing
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
	protected void codeGenInitialization(DecacCompiler compiler) {
		if (type.getName().getName() == "int") {
			compiler.addInstruction(new LOAD(0, Register.getR(
					compiler.registersManag.getRegisterPointer())));
		}
		
		else if(type.getName().getName() == "float") {
			compiler.addInstruction(new LOAD(new ImmediateFloat(0),
					Register.getR(compiler.registersManag.getRegisterPointer())));
		}
		
		else if (type.getName().getName() == "boolean") {
			compiler.addInstruction(new LOAD(new ImmediateInteger(0),
					Register.getR(compiler.registersManag.getRegisterPointer())));
		}
		
		else {
			compiler.addInstruction(new LOAD(new NullOperand(), 
					Register.getR(compiler.registersManag.getRegisterPointer())));
		}
	}
}
