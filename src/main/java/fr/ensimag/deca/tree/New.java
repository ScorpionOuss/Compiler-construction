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
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.BSR;
import fr.ensimag.ima.pseudocode.instructions.LEA;
import fr.ensimag.ima.pseudocode.instructions.NEW;
import fr.ensimag.ima.pseudocode.instructions.POP;
import fr.ensimag.ima.pseudocode.instructions.PUSH;
import fr.ensimag.ima.pseudocode.instructions.STORE;

import java.io.PrintStream;

/**
 *
 * @author gl16
 */
public class New extends AbstractExpr {

    AbstractIdentifier ident;
    
    public New(AbstractIdentifier ident){
        this.ident = ident;
    }
    
    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
    	Type type;
    	try {type = ident.verifyType(compiler);} 
    	catch (ContextualError c) {
    		throw new ContextualError("undefined class", getLocation());
    	}
    	if (!type.isClass()) {
    		throw new ContextualError("undefined class", getLocation());
    	}
    	this.setType(type);
    	return type;
    }


    @Override
    protected void codeGenInst(DecacCompiler compiler) {
    	int registerPointer = getRP(compiler);
    	/*On met l'adresse de l'objet créé dans le registre, il 
    	 * faut la conserver jusqu'au push et après si on veut 
    	 * réupérer l'adresse*/
    	//New #d , R
    	compiler.addInstruction(new NEW(new ImmediateInteger(ident.getClassDefinition().getNumberOfFields() + 1),
    			Register.getR(getRP(compiler))));
    	//BOV tas_plein
    	compiler.addInstruction(new BOV(new Label("tas_plein")));
    	
    	//LEA adA , R0
    	DAddr tableAddr = ident.getClassDefinition().getOperand();
    	assert(tableAddr != null);
    	compiler.addInstruction(new LEA(tableAddr, Register.R0));
    	
    	//STORE R0, 0(R)
    	compiler.addInstruction(new STORE(Register.R0,
    			new RegisterOffset(0, Register.getR(getRP(compiler)))));
    	
    	// PUSH R
    	compiler.addInstruction(new PUSH(Register.getR(getRP(compiler))));
    	
    	// BSR init.A
    	compiler.addInstruction(new BSR(new Label("init." + ident.getName().getName())));
    	
    	// POP R
    	compiler.addInstruction(new POP(Register.getR(getRP(compiler))));
    
    	assert registerPointer == getRP(compiler);
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
        s.print("new");
        ident.decompile(s);
        s.print("()");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        ident.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
    	ident.iter(f);
    }
    
}
