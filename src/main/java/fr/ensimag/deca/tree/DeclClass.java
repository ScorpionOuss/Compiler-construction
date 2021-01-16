package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

/**
 * Declaration of a class (<code>class name extends superClass {members}<code>).
 * 
 * @author gl16
 * @date 01/01/2021
 */
public class DeclClass extends AbstractDeclClass {

    private AbstractIdentifier name;
    private AbstractIdentifier superClass;
    private ListDeclFieldSet fields;
    private ListDeclMethod methods;
    
    public DeclClass(AbstractIdentifier name,AbstractIdentifier superClass,
            ListDeclFieldSet listDeclFieldSet, ListDeclMethod listDeclMethod){
			
            this.name = name;
            this.superClass = superClass;
            this.fields = listDeclFieldSet; 
            this.methods = listDeclMethod;
    }


    
	
    @Override
    public void decompile(IndentPrintStream s) {
        s.print("class ");
        name.decompile(s);
        s.print("extends");
        superClass.decompile(s);
        s.println("{");
        fields.decompile(s);
        methods.decompile(s);
        s.println("}");
        
    }

    @Override
    protected void verifyClass(DecacCompiler compiler) throws ContextualError {
    	SymbolTable symbolTable = new SymbolTable();
    }

    @Override
    protected void verifyClassMembers(DecacCompiler compiler)
            throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");
    }
    
    @Override
    protected void verifyClassBody(DecacCompiler compiler) throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");
    }

	

	@Override
	protected void iterChildren(TreeFunction f) {
		// TODO Auto-generated method stub
		
	}


    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        name.prettyPrint(s, prefix, false);
        superClass.prettyPrint(s, prefix, false);
        fields.prettyPrint(s, prefix, true);
        methods.prettyPrint(s, prefix, true);
        
    }
//
//    @Override
//    protected void iterChildren(TreeFunction f) {
//        throw new UnsupportedOperationException("Not yet supported");
//    }

}
