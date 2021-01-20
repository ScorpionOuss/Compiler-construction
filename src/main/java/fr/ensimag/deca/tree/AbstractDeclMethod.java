package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.DAddr;

/**
 * Method declaration
 *
 * @author gl16
 * @date 14/01/2021
 */
public abstract class AbstractDeclMethod extends Tree {

    /**
     * Implements non-terminal "decl_method" of [SyntaxeContextuelle] in pass 2
     * @param compiler contains "env_types" attribute
     * @param currentClass
     *          corresponds to the "class" attribute (null in the main bloc).
     */
    protected abstract void verifyDeclMethod(DecacCompiler compiler,
            ClassDefinition currentClass)
            throws ContextualError;

	protected abstract void buidTable(DecacCompiler compiler, String className, int offset);
	protected abstract void verifyMethodBody(DecacCompiler compiler, ClassDefinition classDefinition) throws ContextualError;


	protected abstract void GenMethodeCode(DecacCompiler compiler);

}
