package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;

/**
 * @author gl16
 * @date 14/01/2021
*/
public class DeclField extends AbstractDeclField {
    
    final private AbstractIdentifier ident;
    
    final private AbstractInitialization initialization;

    public DeclField( AbstractIdentifier ident, AbstractInitialization initialization) {
    	
        Validate.notNull(ident);
        Validate.notNull(initialization);
       
        this.ident = ident;
        this.initialization = initialization;
    }

    @Override
    protected void verifyDeclField(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");
    }


    @Override
    public void decompile(IndentPrintStream s) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected
    void iterChildren(TreeFunction f) {
    	// to be verified
        ident.iter(f);
        initialization.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
    	// to be verified
        ident.prettyPrint(s, prefix, false);
        initialization.prettyPrint(s, prefix, false);
    }

}

