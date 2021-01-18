/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.context.Signature;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;

import org.apache.commons.lang.Validate;

/**
 *
 * @author ensimag
 */
public class DeclMethod extends AbstractDeclMethod {
    private AbstractIdentifier returnType;
    private AbstractIdentifier name;
    private ListDeclParam listDeclParam;
    private AbstractMethodBody  methodBody;
    
    public DeclMethod(AbstractIdentifier type, AbstractIdentifier name, 
           ListDeclParam listDeclParam, AbstractMethodBody methodBody  ){
    	Validate.notNull(type);
    	Validate.notNull(name);
    	Validate.notNull(listDeclParam);
    	Validate.notNull(methodBody);
        this.returnType = type;
        this.name = name;
        this.listDeclParam = listDeclParam;
        this.methodBody = methodBody;
    }
    @Override
    public void decompile(IndentPrintStream s) {
        returnType.decompile(s);
        s.print(" ");
        name.decompile(s);
        s.print("(");
        listDeclParam.decompile(s);
        s.print(")");
        methodBody.decompile(s);
        
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
      returnType.prettyPrint(s, prefix, false);
      name.prettyPrint(s, prefix, false);
      listDeclParam.prettyPrint(s, prefix, false);
      methodBody.prettyPrint(s, prefix, true);
    }
    

    @Override
    protected void iterChildren(TreeFunction f) {
    	returnType.iter(f);
    	name.iter(f);
    	listDeclParam.iter(f);
    	methodBody.iter(f);
    }

    @Override
    protected void verifyDeclMethod(DecacCompiler compiler, ClassDefinition currentClass) throws ContextualError {
    	Type type = returnType.verifyType(compiler);
    	Signature signature = new Signature();
    	listDeclParam.verifyListDeclParam(compiler, signature);
    	MethodDefinition methodDefinition = new MethodDefinition(type, name.getLocation(),
    			signature, currentClass.incNumberOfMethods());
    	try {
			currentClass.getMembers().declare(name.getName(), methodDefinition);
			name.setType(type);
			name.setDefinition(methodDefinition);
		} catch (DoubleDefException e) {
			e.printStackTrace();
			throw new ContextualError("method name already exists", name.getLocation());
		}
    }
    
	@Override
	protected void buidTable(DecacCompiler compiler) {
		// TODO Auto-generated method stub
		assert(name.getDefinition() instanceof MethodDefinition);//defensive programming

	}
	@Override
	protected void verifyMethodBody(DecacCompiler compiler) {
        throw new UnsupportedOperationException("Not supported yet."); 
	}
    
}
