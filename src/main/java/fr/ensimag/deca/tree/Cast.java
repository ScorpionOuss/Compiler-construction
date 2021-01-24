/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.Label;
import java.io.PrintStream;

import org.apache.commons.lang.Validate;

/**
 *
 * @author gl16
 */
public class Cast extends AbstractExpr{
	
    private AbstractIdentifier type;
    private AbstractExpr expr;

    public Cast(AbstractIdentifier type, AbstractExpr expr ){
    	Validate.notNull(type);
    	Validate.notNull(expr);
        this.type = type;
        this.expr = expr;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
    	Type castType = type.verifyType(compiler);
    	Type assignType = expr.verifyExpr(compiler, localEnv, currentClass);
    	if (!(assignType.castCompatible(castType))) {
    		throw new ContextualError("cast impossible", this.getLocation());
    	}
    	this.setType(castType);
    	return castType;
    }


    @Override
    public boolean adressable() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DVal getAdresse(DecacCompiler compiler) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void codeCond(DecacCompiler compiler, boolean bool, Label endAnd) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void decompile(IndentPrintStream s) {
       s.print("(");
       type.decompile(s);
       s.print(") (");
       expr.decompile(s);
       s.println(")");
       
       
       
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
    	type.prettyPrint(s,prefix,false);
        expr.prettyPrint(s,prefix,true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
    	type.iter(f);
    	expr.iter(f);
    }

	@Override
	protected void codeGenInst(DecacCompiler compiler) {
		// TODO Auto-generated method stub
		
	}


}
