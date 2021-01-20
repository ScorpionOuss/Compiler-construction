package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.ImmediateString;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.WSTR;
import org.apache.commons.lang.Validate;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.NullType;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable;

import java.io.PrintStream;

/**
 * Null
 *
 * @author gl16
 * @date 12/01/2021
 */
public class Null extends AbstractExpr {

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass)  throws ContextualError {
        return new NullType(new SymbolTable().create("null"));
    }

    @Override
    protected void codeGenPrint(DecacCompiler compiler) {
    }


    @Override
    String prettyPrintNode() {
        return "Null";
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("null");
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
	public boolean adressable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DVal getAdresse() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void codeCond(DecacCompiler compiler, boolean bool, Label endAnd) {
		// TODO Auto-generated method stub
		
	}


}
