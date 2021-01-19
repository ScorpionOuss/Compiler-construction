package fr.ensimag.deca.tree;

import java.io.PrintStream;
import fr.ensimag.deca.context.Type;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.RegisterOffset;

/**
 * @author gl16
 * @date 14/01/2021
*/
public class DeclField extends AbstractDeclField {
    
	final private Visibility visibility;
    final private AbstractIdentifier type;
    final private AbstractIdentifier name;
    final private AbstractInitialization initialization;

    public DeclField(Visibility visibility, AbstractIdentifier type, AbstractIdentifier name, AbstractInitialization initialization) {
    	
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
    	try {
			currentClass.getMembers().declare(name.getName(), fieldDefinition);
			name.setType(fieldType);
			name.setDefinition(fieldDefinition);
		} catch (DoubleDefException e) {
			e.printStackTrace();
			throw new ContextualError("field name already exists", name.getLocation());
		}
    	// vérification de l'utilisation des champs de la classe mère
    	// l'utilisation de la classe dans le corps de la classe elle même 
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
        return "[visibility=" + visibility + "] " + this.getClass().getName();
    }

	/**
	 * Initialization of field.
	 */
    @Override
	protected void initField(DecacCompiler compiler, RegisterOffset spot) {
    	
	}
    
}

