package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import java.io.PrintStream;

public class This extends AbstractThis {

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        if(currentClass == null) {
            throw new ContextualError("Cannot use 'this' in main.", getLocation());
        }
        this.setType(currentClass.getType());
        return currentClass.getType();
    }


    public void codegenExpr(DecacCompiler compiler,GPRegister register){
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler){

    }

    @Override
    public void decompile(IndentPrintStream s) {
    s.print("this");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // Leaf node
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // Leaf node
    }


	@Override
	public void codeExp(DecacCompiler compiler, int registerPointer) {
		// TODO Auto-generated method stub
		
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

