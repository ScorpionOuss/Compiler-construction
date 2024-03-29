package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.instructions.ADDSP;

import org.apache.log4j.Logger;

/**
 *
 * @author gl16
 * @date 01/01/2021
 */
public class ListDeclClass extends TreeList<AbstractDeclClass> {
    private static final Logger LOG = Logger.getLogger(ListDeclClass.class);
    
    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractDeclClass c : getList()) {
            c.decompile(s);
            s.println();
        }
    }

    /**
     * Pass 1 of [SyntaxeContextuelle]
     */
    void verifyListClass(DecacCompiler compiler) throws ContextualError {
        //LOG.debug("verify listClass: start");
        for (AbstractDeclClass declClass: this.getList()) {
        	declClass.verifyClass(compiler);
        }
        //LOG.debug("verify listClass: end");
    }

    /**
     * Pass 2 of [SyntaxeContextuelle]
     */
    public void verifyListClassMembers(DecacCompiler compiler) throws ContextualError {
    	//LOG.debug("verify listClassMembers: start");
        for (AbstractDeclClass declClass: this.getList()) {
        	declClass.verifyClassMembers(compiler);
        }
        //LOG.debug("verify listClassMembers: end");
    }
    
    /**
     * Pass 3 of [SyntaxeContextuelle]
     */
    public void verifyListClassBody(DecacCompiler compiler) throws ContextualError {
    	//LOG.debug("verify listClassBody: start");
        for (AbstractDeclClass declClass: this.getList()) {
        	declClass.verifyClassBody(compiler);
        }
        //LOG.debug("verify listClassBody: end");
    }

    /**
     * First Pass of [GenCode] 
     * @param compiler
     */
    protected void buildClassesTable(DecacCompiler compiler) {
    	for (AbstractDeclClass classe : getList()) {
    		classe.buildTable(compiler);
    	}
    	compiler.stackManager.incrementStackCounterMax(compiler.stackManager.getMethodStackCounter());
		compiler.addInstruction(new ADDSP(new ImmediateInteger(compiler.stackManager.getMethodStackCounter())), 
				1);
    }    
    
    /**
     * Second Pass of [GenCode]
     * @param compiler
     */
    protected void methodsGeneration(DecacCompiler compiler) {
    	for (AbstractDeclClass classe : getList()) {
    		//Fields initialization methods generation.
    		classe.fieldsInitMethodsGen(compiler);
    		//Class methods generation
    		classe.classMethodsGen(compiler);
    	}
    }



}
