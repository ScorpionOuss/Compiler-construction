/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Signature;
import fr.ensimag.deca.tools.IndentPrintStream;

/**
 * List of method parameters.
 * 
 * @author gl16
 * @date 14/01/2021
 */
public class ListDeclParam extends TreeList<AbstractDeclParam> {

    @Override
    public void decompile(IndentPrintStream s) {
       for (AbstractDeclParam c : getList()) {
            c.decompile(s);
            s.println();
        }
    }
    
    void verifyListDeclParam(DecacCompiler compiler,
            Signature signature) throws ContextualError {
    	for (AbstractDeclParam declParam: this.getList()) {
    		declParam.verifyDeclParam(compiler, signature);
    	}
    }
    
}
