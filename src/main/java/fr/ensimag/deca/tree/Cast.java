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
import fr.ensimag.ima.pseudocode.ImmediateString;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.ERROR;
import fr.ensimag.ima.pseudocode.instructions.FLOAT;
import fr.ensimag.ima.pseudocode.instructions.INT;
import fr.ensimag.ima.pseudocode.instructions.WNL;
import fr.ensimag.ima.pseudocode.instructions.WSTR;

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
    	return true;
    }

    @Override
    public DVal getAdresse(DecacCompiler compiler) {
    	return expr.getAdresse(compiler);
    }

    @Override
    public void codeCond(DecacCompiler compiler, boolean bool, Label endAnd) {
        throw new UnsupportedOperationException("Not supported"); //To change body of generated methods, choose Tools | Templates.
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
		int registerPointer = getRP(compiler);
		expr.codeGenInst(compiler);


		if (type.getType().getName() == expr.getType().getName()) {
//			System.out.println(type.getType().getName().getName());
		}		
		
		else if (type.getType().isInt()) {
			compiler.addInstruction(new INT(Register.getR(getRP(compiler)),
					Register.getR(getRP(compiler))));
		}
		
		else if(type.getType().isFloat()) {
			compiler.addInstruction(new FLOAT(Register.getR(getRP(compiler)),
					Register.getR(getRP(compiler))));
		}
		else {
			InstanceOf iof = new InstanceOf(expr, type);
			int castCounter = compiler.labelsManager.incrementCastCounter();
			Label endSucces = new Label("end.Succes" + castCounter);
			iof.codeCond(compiler, true, endSucces);
			compiler.addInstruction(new WSTR(new ImmediateString(
					"Erreur; le cast n'est pas possible")));
			compiler.addInstruction(new WNL());
			compiler.addInstruction(new ERROR());
			compiler.addLabel(endSucces);
			expr.codeGenInst(compiler);
		}
		assert registerPointer == getRP(compiler);
	}
}
