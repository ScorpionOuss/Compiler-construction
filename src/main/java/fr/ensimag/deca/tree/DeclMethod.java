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
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.LabelOperand;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.RTS;
import fr.ensimag.ima.pseudocode.instructions.STORE;

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
    		type = compiler.getEnvironment().get(returnType.getName()).getType();
    	} else type = returnType.verifyType(compiler);
    	
    	Signature signature = new Signature();
    	listDeclParam.verifyListDeclParam(compiler, signature);
    	MethodDefinition methodDefinition = new MethodDefinition(type, name.getLocation(),
    			signature, currentClass.incNumberOfMethods());
    	
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
    		if (!(superNameDefinition.asMethodDefinition("The name " + name.getName() + " is declare as a field in the superclass",
    				name.getLocation()).getSignature().equals(signature) &&
    				type.subType(superNameDefinition.getType()))){
    			throw new ContextualError("The declaration of" + name.getName() + " is not compatible with its declaration in superclass"
    					, name.getLocation());
    		}
    	}
    }
    
	@Override
	protected void buidTable(DecacCompiler compiler, String className, int offset) {
		
		assert(name.getDefinition() instanceof MethodDefinition);//defensive programming
		//set method label
		name.getMethodDefinition().setLabel(new Label("code." + className + "." +
				name.getName().getName()));
		
		//add instructions 
		compiler.addInstruction(new LOAD(new LabelOperand(name.getMethodDefinition().getLabel()),
				Register.R0));
		
		compiler.addInstruction(new STORE(Register.R0, 
				new RegisterOffset(offset + name.getMethodDefinition().getIndex(),
						Register.GB)));
	}
	

	@Override
	protected void GenMethodeCode(DecacCompiler compiler) {
		//Set Definition of parameters
		listDeclParam.setParametersDefinition();
		
		//save stackCounter
		compiler.stackManager.saveStackPointers();
		
		//Label Management
		compiler.addLabel(name.getMethodDefinition().getLabel());
		//TSTO and BOV Management
		int snapShotLines = compiler.currentLinesSize();

		//ADDSP
		methodBody.GenbodyCodeVars(compiler);
		//Save Registers
		compiler.registersManag.saveRegisters(compiler);
		//Method code
		methodBody.GenbodyCodeInsts(compiler);
		//Errors handler
		
		/********À FAIRE*******/
		
		//End Label
		
		//Restore registers
		compiler.registersManag.restoreRegisters(compiler);

		//RTS
		compiler.addInstruction(new RTS());

		//Insert TSTO and BOV
		compiler.addStackVerificationBlock(snapShotLines);
		
		//Restore stackCounter
		compiler.stackManager.restoreStackPointers();
	}
	


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
	

}
