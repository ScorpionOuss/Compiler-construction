package fr.ensimag.deca.tree;

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
            ClassDefinition currentClass) throws ContextualError {
    	SymbolTable symbolTable = new SymbolTable();
    	return new NullType(symbolTable.create("null"));
    }


    @Override
    String prettyPrintNode() {
        return "Null";
    }

    @Override
    public void decompile(IndentPrintStream s) {
    	throw new UnsupportedOperationException("not yet imlemented");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }

}