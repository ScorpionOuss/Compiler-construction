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

/**
 *
 * @author gl16
 */
public abstract class AbstractMethodBody extends Tree{

	protected abstract void verifyMethodBody(DecacCompiler compiler, EnvironmentExp localEnv,
			ClassDefinition classDefinition, Type returnType)
	                   throws ContextualError;
		
}
