/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import fr.ensimag.ima.pseudocode.instructions.LOAD;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

/**
 *
 * @author ensimag
 */
public class InstanceOf extends AbstractExpr{
	
    private AbstractExpr expr;
    private AbstractIdentifier type;

    public InstanceOf(AbstractExpr expr, AbstractIdentifier type){
    	Validate.notNull(expr);
    	Validate.notNull(type);
        this.expr = expr;
        this.type = type;
     }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
    	Type exprType = expr.verifyExpr(compiler, localEnv, currentClass);
    	Definition otherDef = compiler.getEnvironment().get(type.getName());
    	if (otherDef == null) throw new ContextualError("the type " + type.getName() + " does not exist",
    			type.getLocation());
    	Type otherType = otherDef.getType();
    	if (!exprType.typeInstanceofOp(otherType)) {
    		throw new ContextualError("incompatible types with instanceof operation", this.getLocation());
    	}
    	type.setType(otherType);
    	type.setDefinition(otherDef);
    	return exprType;
    }

    

    @Override
    public boolean adressable() {
    	return false;
    }

    @Override
    public void codeCond(DecacCompiler compiler, boolean bool, Label etiquette) {
		assert type.getType().isClass();
		assert expr.getType().isClass();
		ClassType typeClass = (ClassType) type.getType();
		ClassType exprClass = (ClassType) expr.getType();
		boolean iOf = exprClass.getDefinition().instanceOf(typeClass.getDefinition());
		if (bool == iOf) {
			compiler.addInstruction(new BRA(etiquette));
		}
    }

    @Override
    public void decompile(IndentPrintStream s) {
         s.print("(");
         expr.decompile(s);
         type.decompile(s);
         s.println(")");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        expr.prettyPrint(s, prefix, false);
        type.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
    	expr.iter(f);
    	type.iter(f);
    }
    
	@Override
	public DVal getAdresse(DecacCompiler compiler) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected void codeGenInst(DecacCompiler compiler) {
		assert type.getType().isClass();
		assert expr.getType().isClass();
		ClassType typeClass = (ClassType) type.getType();
		ClassType exprClass = (ClassType) expr.getType();
		boolean iOf = exprClass.getDefinition().instanceOf(typeClass.getDefinition());
		if (iOf) {
			compiler.addInstruction(new LOAD(1, Register.getR(getRP(compiler))));
		}
		else {
			compiler.addInstruction(new LOAD(0, Register.getR(getRP(compiler))));
		}
	}
}
