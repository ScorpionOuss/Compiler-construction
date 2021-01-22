/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.context.Signature;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;

import org.apache.commons.lang.Validate;

/**
 *
 * @author gl16
 */
public class DeclMethod extends AbstractDeclMethod {

    private AbstractIdentifier returnType;
    private AbstractIdentifier name;
    private ListDeclParam listDeclParam;
    private AbstractMethodBody methodBody;
    
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
    	
    	Type type; 
    	if (returnType.getName().toString().equals("void")) {
    		Definition voidDef = compiler.getEnvironment().get(returnType.getName());
    		type = voidDef.getType();
    		returnType.setType(type);
    		returnType.setDefinition(voidDef);
    	} else type = returnType.verifyType(compiler);
    	
    	Signature signature = new Signature();
    	listDeclParam.verifyListDeclParam(compiler, signature);
    	MethodDefinition methodDefinition = new MethodDefinition(type, name.getLocation(),
    			signature, currentClass.getNumberOfMethods() + 1);
    	
    	// the definition used for the name in this class or in a superclass (if it exists)
    	// only used if the name is not defined in this class
    	Definition superNameDefinition = currentClass.getMembers().get(name.getName());
    	
    	// verifies if the name is already defined in this class
    	try {
			currentClass.getMembers().declare(name.getName(), methodDefinition);
			name.setType(type);
			name.setDefinition(methodDefinition);
		} catch (DoubleDefException e) {
			throw new ContextualError("method name already exists", name.getLocation());
		}
    	
    	// verifies the conditions specified when the method name is defined in the superclass
    	if (superNameDefinition != null) {
    		MethodDefinition superNameMethodDef = superNameDefinition.asMethodDefinition("The name " + name.getName() + " is declare as a field in the superclass",
    				name.getLocation());
    		if (!(superNameMethodDef.getSignature().getArgs().equals(signature.getArgs()) &&
    				type.subType(superNameDefinition.getType()))){
    			throw new ContextualError("The declaration of " + name.getName() + " is not compatible with its declaration in superclass"
    					, name.getLocation());
    		}
    		methodDefinition.setIndex(superNameMethodDef.getIndex());
    	}
    	else currentClass.incNumberOfMethods();
    }
    
	@Override
	protected void verifyMethodBody(DecacCompiler compiler, ClassDefinition classDefinition) throws ContextualError {
		EnvironmentExp localEnv = new EnvironmentExp(null);
		for (AbstractDeclParam param: listDeclParam.getList()) {
			try {
				localEnv.declare(param.getName().getName(), (ExpDefinition) param.getName().getDefinition());
			} catch (DoubleDefException e) {
				throw new ContextualError("Parameters can't have the same name", name.getLocation());
			}
		}
		methodBody.verifyMethodBody(compiler, localEnv, classDefinition, returnType.getType());
	}
	
	@Override
	protected void buidTable(DecacCompiler compiler) {
		// TODO Auto-generated method stub
		assert(name.getDefinition() instanceof MethodDefinition);//defensive programming
	}

    
}
