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
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.NullOperand;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.ADD;
import fr.ensimag.ima.pseudocode.instructions.ADDSP;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.BNE;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import fr.ensimag.ima.pseudocode.instructions.BSR;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.ERROR;
import fr.ensimag.ima.pseudocode.instructions.LEA;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.PUSH;
import fr.ensimag.ima.pseudocode.instructions.RTS;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import fr.ensimag.ima.pseudocode.instructions.SUB;
import fr.ensimag.ima.pseudocode.instructions.SUBSP;

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
    	Type booleanType = compiler.getEnvironment().get(new SymbolTable().create("boolean")).getType();
    	this.setType(booleanType);
    	return booleanType;
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
		codeGenInst(compiler);
		if (bool) {
			compiler.addInstruction(new CMP(new ImmediateInteger(0),
					Register.getR(getRP(compiler))));
			compiler.addInstruction(new BNE(etiquette));
		}
		else {
			compiler.addInstruction(new CMP(new ImmediateInteger(0),
					Register.getR(getRP(compiler))));
			compiler.addInstruction(new BEQ(etiquette));
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
			compiler.addInstruction(new ADDSP(new ImmediateInteger(2)));
			//class -3(LB)
			compiler.addInstruction(new LEA(type.getClassDefinition().getOperand(), 
					Register.R0));
			compiler.addInstruction(new STORE(Register.R0, 
					new RegisterOffset(-1, Register.SP)));
			//expr -2(LB)
			compiler.addInstruction(new LOAD(expr.getAdresse(compiler), 
					Register.R0));
			compiler.addInstruction(new STORE(Register.R0,
					new RegisterOffset(0, Register.SP)));
			compiler.addInstruction(new BSR(new Label("debut.io")));
			compiler.addInstruction(new SUBSP(new ImmediateInteger(2)));
	}
}
