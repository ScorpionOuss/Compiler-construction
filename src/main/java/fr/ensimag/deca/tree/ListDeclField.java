package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.RegisterOffset;

/**
 * List of class fields declarations .
 * 
 * @author gl16
 * @date 01/01/2021
 */
public class ListDeclField extends TreeList<AbstractDeclField> {

    @Override
    public void decompile(IndentPrintStream s) {
         for (AbstractDeclField c : getList()) {
            c.decompile(s);
            s.println();
        }
    }

    /**
     * Implements non-terminal "list_decl_field" of [SyntaxeContextuelle] in pass 2
     * @param compiler contains the "env_types" attribute
     * @param localEnv 
     *   its "parentEnvironment" corresponds to "env_exp_sup" attribute
     *   in precondition, its "current" dictionary corresponds to 
     *      the "env_exp" attribute
     *   in postcondition, its "current" dictionary corresponds to 
     *      the "env_exp_r" attribute
     * @param currentClass 
     *          corresponds to "class" attribute (null in the main bloc).
     */    
    void verifyListDeclField(DecacCompiler compiler,
            ClassDefinition currentClass) throws ContextualError {
    	for (AbstractDeclField declField: this.getList()) {
    		declField.verifyDeclField(compiler, currentClass);
    	}
    }

    /*
     * Initialization of fields
     */
	public void initFields(DecacCompiler compiler, RegisterOffset spot) {
		for (AbstractDeclField field : getList()) {
			field.initField(compiler, spot);
		}
	}
    
}

