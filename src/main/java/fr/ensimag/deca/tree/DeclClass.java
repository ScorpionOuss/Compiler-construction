package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.context.TypeDefinition;
import fr.ensimag.deca.tools.IndentPrintStream;

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
    private ListDeclField fields;
    private ListDeclMethod methods;
    
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
    	ClassDefinition classDefinition = (ClassDefinition) name.getDefinition();
    	ClassDefinition superClassDef = (ClassDefinition) superClass.getDefinition();
    	classDefinition.setNumberOfFields(superClassDef.getNumberOfFields());
    	classDefinition.setNumberOfMethods(superClassDef.getNumberOfMethods());
    	fields.verifyListDeclField(compiler, classDefinition);
    	methods.verifyListDeclMethod(compiler, classDefinition);
    	for (AbstractDeclMethod m: methods.getList()) {
    		DeclMethod mm = (DeclMethod) m;
    		MethodDefinition mmm = (MethodDefinition) classDefinition.getMembers().get(mm.getName().getName());
    		System.out.println(mmm.getIndex());
    	}
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
		methods.buildTable(compiler);
	}


}
