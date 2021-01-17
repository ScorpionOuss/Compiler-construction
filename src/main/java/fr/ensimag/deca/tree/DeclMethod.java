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
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;

import org.mockito.internal.matchers.InstanceOf;

/**
 *
 * @author ensimag
 */
public class DeclMethod extends AbstractDeclMethod {
    private AbstractIdentifier type;
    private AbstractIdentifier ident;
    private ListDeclParam listDeclParam;
    private AbstractMethodBody  methodBody;
    
    public DeclMethod(AbstractIdentifier type,AbstractIdentifier ident, 
           ListDeclParam listDeclParam, AbstractMethodBody methodBody  ){
        this.type = type;
        this.ident = ident;
        this.listDeclParam = listDeclParam;
        this.methodBody = methodBody;
    }
    @Override
    public void decompile(IndentPrintStream s) {
        type.decompile(s);
        s.print(" ");
        ident.decompile(s);
        s.print("(");
        listDeclParam.decompile(s);
        s.print(")");
        methodBody.decompile(s);
        
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
      type.prettyPrint(s, prefix, false);
      ident.prettyPrint(s, prefix, false);
      listDeclParam.prettyPrint(s, prefix, false);
      methodBody.prettyPrint(s, prefix, false);
      
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void verifyDeclMethod(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
	@Override
	protected void buidTable(DecacCompiler compiler) {
		// TODO Auto-generated method stub
		assert(ident.getDefinition() instanceof MethodDefinition);//defensive programming

	}
    
}
