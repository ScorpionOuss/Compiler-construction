package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.*;
import fr.ensimag.ima.pseudocode.instructions.ADDSP;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.BSR;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import fr.ensimag.ima.pseudocode.instructions.SUBSP;

import org.apache.commons.lang.Validate;
import java.io.PrintStream;
public class MethodCall extends AbstractExpr{

    AbstractExpr obj;
    AbstractIdentifier method;
    ListExpr params;

    public MethodCall(AbstractExpr obj, AbstractIdentifier method, ListExpr params) {
        Validate.notNull(obj);
        Validate.notNull(method);
        Validate.notNull(params);

        this.obj = obj;
        this.method = method;
        this.params = params;
    }


    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
   if(currentClass == null) {
              throw new ContextualError("Cannot use 'this' in main.", getLocation());
          }
          this.setType(currentClass.getType());
         return currentClass.getType();
    }
    
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
    	ImmediateInteger numPar = new ImmediateInteger(params.size() + 1);
    	//ADDSP
    	compiler.addInstruction(new ADDSP(numPar));
    	
    	//Empilement des paramètres
    	
    	////Empiler Objet
    	
    	/***TODO***/
    	
    	////Empiler les paramètres effectifs
    	int compteur = 1;
    	for (AbstractExpr expr : params.getList()) {
    		//Pour l'instant on considère que c'est le registre 2
    		expr.codeGenInst(compiler);
    		compiler.addInstruction(new STORE(Register.getR(2), 
    				new RegisterOffset(-compteur, Register.SP)));
    		compteur++;
    	}
    	
    	// récupérer 0(SP) et tester referNull
    	compiler.addInstruction(new LOAD(new RegisterOffset(0, Register.SP), 
    			Register.getR(2)));
    	compiler.addInstruction(new CMP(new NullOperand(), Register.getR(2)));
    	
    	compiler.addInstruction(new BEQ(new Label("dereferencement.null")));
    	//récupérer l'adresse de la table
    	
    	compiler.addInstruction(new LOAD(new RegisterOffset(0, Register.getR(2)),
    			Register.getR(2)));
    	//appel de la méthode selon l'indice dans la table
    	
    	compiler.addInstruction(new BSR(new RegisterOffset(method.getMethodDefinition().getIndex(),
    			Register.getR(2))));
    	//dépilement.
    	compiler.addInstruction(new SUBSP(numPar));
    }
    

    @Override
    protected void codeGenPrint(DecacCompiler compiler) {
    	throw new UnsupportedOperationException("opération non tolérée");
    }

    @Override
    public void decompile(IndentPrintStream s) {
        obj.decompile(s);
        s.print("(");
        method.decompile(s);
        s.print(")");
        params.decompile(s);
         s.println(")");
        
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        obj.prettyPrint(s, prefix, false);
        method.prettyPrint(s, prefix, false);
        params.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        obj.iter(f);
        method.iter(f);
        params.iter(f);
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

