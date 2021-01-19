package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;

/**
 * List of class methods declarations .
 * 
 * @author gl16
 * @date 01/01/2021
 */
public class ListDeclMethod extends TreeList<AbstractDeclMethod> {

    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractDeclMethod c : getList()) {
            c.decompile(s);
            s.println();
        }
    }

    /**
     * Implements non-terminal "list_decl_method" of [SyntaxeContextuelle] in pass 2
     * @param compiler contains the "env_types" attribute
     * @param currentClass 
     *          corresponds to "class" attribute (null in the main bloc).
     */    
    void verifyListDeclMethod(DecacCompiler compiler, ClassDefinition currentClass) throws ContextualError {
    	currentClass.incNumberOfMethods();
    	for (AbstractDeclMethod declMethod: this.getList()) {
    		declMethod.verifyDeclMethod(compiler, currentClass);
    	}
    }

	public void buildTable(DecacCompiler compiler) {
		// TODO Auto-generated method stub
		compiler.incrementMethodStackCouter(size() + 1);
		for (AbstractDeclMethod method : getList()) {
			method.buidTable(compiler);
		}
	}

	public void verifyListMethodsBody(DecacCompiler compiler) {
		for (AbstractDeclMethod declMethod: this.getList()) {
    		declMethod.verifyMethodBody(compiler);
    	}
	}
    
}