package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.ImmediateString;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import fr.ensimag.ima.pseudocode.instructions.ERROR;
import fr.ensimag.ima.pseudocode.instructions.HALT;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.WNL;
import fr.ensimag.ima.pseudocode.instructions.WSTR;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;


public class Return extends AbstractInst {

    private AbstractExpr exp;

    public AbstractExpr getExp() {
        return exp;
    }
    
    public Return(AbstractExpr exp) {
        Validate.notNull(exp);
        this.exp = exp;
    }


    
    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType)
            throws ContextualError {
    	if (currentClass == null) {
    		throw new ContextualError("Main should not have a return instruction", getLocation());
    	}
		exp = exp.verifyRValue(compiler, localEnv, currentClass, returnType);
    	if (exp.getType().isVoid()) {
    		throw new ContextualError("unexpected return for this method", getLocation());
    	}
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("return ");
        getExp().decompile(s);
        s.println(";");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        exp.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        exp.prettyPrint(s, prefix, false);
    }
    
    @Override
	public void codeGenInst(DecacCompiler compiler, String name) {
    	exp.codeGenInst(compiler);
    	compiler.addInstruction(new LOAD(Register.getR(getRP(compiler)),
    			Register.R0));
    	compiler.addInstruction(new BRA(new Label(name)));
	}
    
}

