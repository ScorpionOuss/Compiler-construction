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
    public boolean adressable() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DVal getAdresse() {
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
