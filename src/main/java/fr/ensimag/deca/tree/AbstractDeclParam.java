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

/**
 * @author gl16
 * @date 01/01/2021
 */
public abstract class AbstractDeclParam extends Tree {
    
	/**
     * Implements non-terminal "decl_param" of [SyntaxeContextuelle] in pass 2
     * @param compiler contains "env_types" attribute
	 * @param signature 
     */
    protected abstract void verifyDeclParam(DecacCompiler compiler, Signature signature)
            throws ContextualError;
}
