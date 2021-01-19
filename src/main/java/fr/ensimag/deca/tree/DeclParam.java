/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.ParamDefinition;
import fr.ensimag.deca.context.Signature;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;

import org.apache.commons.lang.Validate;

/**
 *
 * @author gl16
 */
public class DeclParam extends AbstractDeclParam{
    
    private AbstractIdentifier type;
    private AbstractIdentifier name;
   
    
    public DeclParam(AbstractIdentifier type,AbstractIdentifier ident){
    	Validate.notNull(type);
    	Validate.notNull(name);
        this.type = type;
        this.name = ident;
       
    }
    
    @Override
    protected AbstractIdentifier getName() {
    	return name;
    }

    @Override
    public void decompile(IndentPrintStream s) {
       type.decompile(s);
       s.print(" ");;
       name.decompile();
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        name.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
    	type.iter(f);
    	name.iter(f);
    }

	@Override
	protected void verifyDeclParam(DecacCompiler compiler, Signature signature) throws ContextualError {
		Type parType = type.verifyType(compiler);
		name.setType(parType);
		name.setDefinition(new ParamDefinition(parType, name.getLocation()));
		signature.add(parType);
	}
    
    
}
