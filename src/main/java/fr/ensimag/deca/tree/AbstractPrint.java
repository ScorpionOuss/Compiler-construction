package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.FloatType;
import fr.ensimag.deca.context.IntType;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * Print statement (print, println, ...).
 *
 * @author gl16
 * @date 01/01/2021
 */
public abstract class AbstractPrint extends AbstractInst {

    private boolean printHex;
    private ListExpr arguments = new ListExpr();
    
    abstract String getSuffix();

    public AbstractPrint(boolean printHex, ListExpr arguments) {
        Validate.notNull(arguments);
        this.arguments = arguments;
        this.printHex = printHex;
    }

    public ListExpr getArguments() {
        return arguments;
    }

    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType)
            throws ContextualError {
    	for (AbstractExpr expr: arguments.getList()) {
    		Type type = expr.verifyExpr(compiler, localEnv, currentClass);
    		if (!(type.isString() || type.isInt() || type.isFloat())) {
    			throw new ContextualError("this expression can't be printed", this.getLocation());
    		}
    	}
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler, String name) {
    	if (!getPrintHex()) {
	    	for (AbstractExpr a : getArguments().getList()) {
	            a.codeGenPrint(compiler);
	        }
    	}
    	else {
    		assert(getPrintHex());
    		for (AbstractExpr a : getArguments().getList()) {
    			a.codeGenPrintHex(compiler);
	        }
    	}
    }

    public boolean getPrintHex() {
        return printHex;
    }

    @Override
    public void decompile(IndentPrintStream s) {
       s.print("print");
       s.print(getSuffix()+"(");
       arguments.decompilePrint(s);
       s.println(");");

    }

    @Override
    protected void iterChildren(TreeFunction f) {
        arguments.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        arguments.prettyPrint(s, prefix, true);
    }

}
