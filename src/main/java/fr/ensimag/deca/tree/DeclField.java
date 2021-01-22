package fr.ensimag.deca.tree;

import java.io.PrintStream;
import fr.ensimag.deca.context.Type;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;

/**
 * @author gl16
 * @date 14/01/2021
*/
public class DeclField extends AbstractDeclField {
    
    final private Visibility visibility;
    final private AbstractIdentifier type;
    final private AbstractIdentifier name;
    final private AbstractInitialization initialization;

    public DeclField(Visibility visibility, AbstractIdentifier type, AbstractIdentifier name,
    		AbstractInitialization initialization) {
    	
        Validate.notNull(visibility);
        Validate.notNull(type);
        Validate.notNull(name);
        Validate.notNull(initialization);
       
        this.visibility = visibility;
        this.type = type;
        this.name = name;
        this.initialization = initialization;
    }
    

    @Override
    protected void verifyDeclField(DecacCompiler compiler, ClassDefinition currentClass)
            throws ContextualError {
    	
    	Type fieldType = type.verifyType(compiler);
    	
    	FieldDefinition fieldDefinition = new FieldDefinition(fieldType, name.getLocation(),
    			visibility, currentClass, currentClass.incNumberOfFields());
    	
    	// the definition used for the name in this class or in a superclass (if it exists)
    	// only used if the name is not defined in this class
    	Definition superNameDefinition = currentClass.getMembers().get(name.getName());
    	
    	// verifies if the name is already defined in this class
    	try {
			currentClass.getMembers().declare(name.getName(), fieldDefinition);
			name.setType(fieldType);
			name.setDefinition(fieldDefinition);
		} catch (DoubleDefException e) {
			throw new ContextualError("The name " + name.getName() + " is already used", name.getLocation());
		}
    	
    	// verifies if the name is defined as a field in the superclass (if it is defined in the superclass)
    	if (superNameDefinition != null && !superNameDefinition.isField()) {
    		throw new ContextualError("The name " + name.getName() + " was declared as method name in the superclass",
    				name.getLocation());
    	}

    	initialization.verifyInitialization(compiler, fieldType, currentClass.getMembers(), currentClass);
    }


    @Override
    public void decompile(IndentPrintStream s) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected
    void iterChildren(TreeFunction f) {
        type.iter(f);
        name.iter(f);
        initialization.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        name.prettyPrint(s, prefix, false);
        initialization.prettyPrint(s, prefix, true);
    }

    @Override
    String prettyPrintNode() {
        return "[visibility=" + visibility + "] " + this.getClass().getSimpleName();
    }

	/**
	 * Initialization of field.
	 */
    @Override
	protected void initField(DecacCompiler compiler) {
    	//LOAD -> R
    	int registerPointer = compiler.registersManag.getRegisterPointer();
    	initialization.codeGenInitialization(compiler);
    	assert(registerPointer == compiler.registersManag.getRegisterPointer());
    	//LOAD -2(LB), R1
		compiler.addInstruction(new LOAD(new RegisterOffset(-2, Register.LB), 
				Register.R1));

    	//STORE R , index(R1)
    	assert(name.getDefinition() instanceof FieldDefinition);
    	compiler.addInstruction(new 
    			STORE(Register.getR(compiler.registersManag.getRegisterPointer()),
    			new RegisterOffset(name.getDefinition().getIndex(),
    					Register.R1)));
	}
}

