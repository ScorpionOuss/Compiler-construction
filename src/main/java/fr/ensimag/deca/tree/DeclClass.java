package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.context.TypeDefinition;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.LabelOperand;
import fr.ensimag.ima.pseudocode.NullOperand;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.BSR;
import fr.ensimag.ima.pseudocode.instructions.LEA;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.POP;
import fr.ensimag.ima.pseudocode.instructions.PUSH;
import fr.ensimag.ima.pseudocode.instructions.RTS;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import fr.ensimag.ima.pseudocode.instructions.SUBSP;

import java.io.PrintStream;
import java.util.LinkedList;

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
    private ListDeclField fields;
    private ListDeclMethod methods;
    private LinkedList<Definition> tableau;
    
    public DeclClass(AbstractIdentifier name,AbstractIdentifier superClass,
            ListDeclField fields, ListDeclMethod methods){
    		Validate.notNull(name);
    		Validate.notNull(superClass);
    		Validate.notNull(fields);
    		Validate.notNull(methods);
            this.name = name;
            this.superClass = superClass;
            this.fields = fields; 
            this.methods = methods;
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
    	TypeDefinition definition = compiler.getEnvironment().get(superClass.getName()); 
    	if (definition == null) {
    		throw new ContextualError("The class " + superClass.getName().getName() + " is not defined", superClass.getLocation());
    	}
    	if (!definition.getType().isClass()) {
    		throw new ContextualError("The type " + superClass.getName().getName() + " is not a class", superClass.getLocation());
    	}
    	ClassDefinition superClassDefinition = (ClassDefinition) definition;
    	ClassType type = new ClassType(name.getName(), name.getLocation(), superClassDefinition); 
    	ClassDefinition classDefinition = type.getDefinition();
    	try {
			compiler.getEnvironment().declare(name.getName(), classDefinition);
			name.setDefinition(classDefinition);
			name.setType(type);
			superClass.setDefinition(superClassDefinition);
			superClass.setType(superClassDefinition.getType());
		} catch (DoubleDefException e) {
			throw new ContextualError("class already defined or forbidden name used", name.getLocation());
		}
    }

    @Override
    protected void verifyClassMembers(DecacCompiler compiler)
            throws ContextualError {
    	fields.verifyListDeclField(compiler, (ClassDefinition)name.getDefinition());
    	methods.verifyListDeclMethod(compiler, (ClassDefinition)name.getDefinition());
    }
    
    @Override
    protected void verifyClassBody(DecacCompiler compiler) throws ContextualError {
    	methods.verifyListMethodsBody(compiler, (ClassDefinition)name.getDefinition());
    }

	@Override
	protected void prettyPrintChildren(PrintStream s, String prefix) {
		name.prettyPrint(s, prefix, false);
		superClass.prettyPrint(s, prefix, false);
		fields.prettyPrint(s, prefix, false);
		methods.prettyPrint(s, prefix, true);
	}
	

	@Override
	protected void iterChildren(TreeFunction f) {
		name.iter(f);
		superClass.iter(f);
		fields.iter(f);
		methods.iter(f);
	}


	@Override
	protected void buildTable(DecacCompiler compiler) {
		//Il faut régler le problème de dAddr..
		int offset = compiler.stackManager.getMethodStackCounter() + 1;
		assert(name.getDefinition() instanceof ClassDefinition);
		name.getClassDefinition().setOperand(new 
				RegisterOffset(offset, Register.GB));
		System.out.println(offset);
		compiler.stackManager.incrementMethodStackCounter(name.getClassDefinition().getNumberOfMethods() + 1);
		//add superClass methods table pointer
		//À Revoir TODO
		/******Ce qu'on peut faire je pense c'est d'initialiser la dAddr pour Objet*****/
		DAddr addr;
		if (superClass.getType().getName().getName() == "Object") {
			addr = new RegisterOffset(1, Register.GB);
		}
		else {
			assert(superClass.getType().getName().getName() != "Object");
			addr = superClass.getClassDefinition().getOperand();
			assert(addr != null);
		}
		//Instructions LEA et STORE
		assert(addr != null);
		compiler.addInstruction(new LEA(addr, Register.R0));
		DAddr classStackAddr = name.getClassDefinition().getOperand();
		assert classStackAddr != null;
		compiler.addInstruction(new STORE(Register.R0, classStackAddr));
		//add methods label
		methods.setLabels(compiler, name.getName().getName());
		name.getClassDefinition().buildTable(compiler, offset);
	}


	@Override
	protected void fieldsInitMethodsGen(DecacCompiler compiler) {
		//save stackCounter
		compiler.stackManager.saveStackPointers();
		//deal with labels
		compiler.addLabel(new Label("init." + name.getName().getName()));
		//pick up insertion line 
		int snapShotLines = compiler.currentLinesSize();
		//ADDSP
		
		//Save registers
		compiler.registersManag.saveRegisters(compiler);
		
		//Method code
		RegisterOffset offSetLB = new RegisterOffset(-2, Register.LB);
		compiler.addInstruction(new LOAD(offSetLB, Register.R1));
		
		/*****TODO revoir si il faut initialiser avant les champs propres à 0*****/
		////SuperClass attributes initialization
		compiler.addInstruction(new PUSH(Register.R1));
		compiler.stackManager.incrementStackCounterMax(1);
		compiler.addInstruction(new BSR(new LabelOperand( new 
				Label("init." +superClass.getName().getName()))));
		compiler.addInstruction(new SUBSP(new ImmediateInteger(1)));
		
		////PROPRE attributes [OK]
		/*****À REVOIR SI ON TRAVAILLE TOUJOURS AVEC R0 ET R1*****/

		fields.initFields(compiler);
		
		/*****On restore le nombre de registres qu'utilise la classe*****/
		//insert save Instructions
		for (int i = compiler.registersManag.getMaxRegisterPointer(); i >= 2; i--) {
			compiler.addInstruction(new PUSH(Register.getR(i)), snapShotLines);
		}
		//Restore registers.
		for (int i = compiler.registersManag.getMaxRegisterPointer(); i >= 2; i--) {
			compiler.addInstruction(new POP(Register.getR(i)));
		}
		
		compiler.registersManag.restoreRegisters(compiler);
		//RTS
		compiler.addInstruction(new RTS());
		//Insert TSTO and BOV
		compiler.addStackVerificationBlock(snapShotLines);
		//Restore stackCounter
		compiler.stackManager.restoreStackPointers();
	}


	@Override
	protected void classMethodsGen(DecacCompiler compiler) {
		for (AbstractDeclMethod method : methods.getList()) {
			method.GenMethodeCode(compiler, name.getName().getName());
		}
	}
}
