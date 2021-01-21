package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.TypeDefinition;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.deca.context.VariableDefinition;
import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.LOAD;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * Deca Identifier
 *
 * @author gl16
 * @date 01/01/2021
 */
public class Identifier extends AbstractIdentifier {
    
    @Override
    protected void checkDecoration() {
        if (getDefinition() == null) {
            throw new DecacInternalError("Identifier " + this.getName() + " has no attached Definition");
        }
    }

    @Override
    public Definition getDefinition() {
        return definition;
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * ClassDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *             if the definition is not a class definition.
     */
    @Override
    public ClassDefinition getClassDefinition() {
        try {
            return (ClassDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a class identifier, you can't call getClassDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * MethodDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *             if the definition is not a method definition.
     */
    @Override
    public MethodDefinition getMethodDefinition() {
        try {
            return (MethodDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a method identifier, you can't call getMethodDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * FieldDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *             if the definition is not a field definition.
     */
    @Override
    public FieldDefinition getFieldDefinition() {
        try {
            return (FieldDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a field identifier, you can't call getFieldDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * VariableDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *             if the definition is not a field definition.
     */
    @Override
    public VariableDefinition getVariableDefinition() {
        try {
            return (VariableDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a variable identifier, you can't call getVariableDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a ExpDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *             if the definition is not a field definition.
     */
    @Override
    public ExpDefinition getExpDefinition() {
        try {
            return (ExpDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a Exp identifier, you can't call getExpDefinition on it");
        }
    }

    @Override
    public void setDefinition(Definition definition) {
        this.definition = definition;
    }

    @Override
    public Symbol getName() {
        return name;
    }

    private Symbol name;

    public Identifier(Symbol name) {
        Validate.notNull(name);
        this.name = name;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
    	ExpDefinition localExpDefinition = localEnv.get(name);
    	ExpDefinition classExpDefinition = null;
    	if (currentClass != null) {
    		classExpDefinition = currentClass.getMembers().get(name);
    	}
    	if (localExpDefinition == null) {
    		if (classExpDefinition == null) {
				throw new ContextualError("The variable " + name.getName() + " is not defined", this.getLocation());
    		}
    		else {
    			if (classExpDefinition.isField()) {
    				FieldDefinition fieldDef = classExpDefinition.asFieldDefinition("", this.getLocation());
    				if (fieldDef.getVisibility().equals(Visibility.PROTECTED)) {
    				}
    			}
    			this.setType(classExpDefinition.getType());
    			this.setDefinition(classExpDefinition);
    			return classExpDefinition.getType();
    		}
    	} else {
    		if (classExpDefinition == null) {
    			if (localExpDefinition.isField()) {
    				FieldDefinition fieldDef = localExpDefinition.asFieldDefinition("", this.getLocation());
    				if (fieldDef.getVisibility().equals(Visibility.PROTECTED)) {
    					throw new ContextualError("inaccessible protected variable", getLocation());
    				}
    			}
    			this.setType(localExpDefinition.getType());
    			this.setDefinition(localExpDefinition);
    			return localExpDefinition.getType();
    		} else {
    			throw new ContextualError("this name can't be resolved field and variable definitions exist for this name",
    					getLocation());
    		}
    	}
    }

    /**
     * Implements non-terminal "type" of [SyntaxeContextuelle] in the 3 passes
     * @param compiler contains "env_types" attribute
     */
    @Override
    public Type verifyType(DecacCompiler compiler) throws ContextualError {
    	TypeDefinition typeDefinition = compiler.getEnvironment().get(name);
    	if (typeDefinition == null) {
    		throw new ContextualError("The type " + name.getName() + " is not defined", this.getLocation());
    	} else if (typeDefinition.getType().isVoid()) {
    		throw new ContextualError("Type void variable declaration", this.getLocation());
    	}
    	this.setDefinition(typeDefinition);
    	this.setType(typeDefinition.getType());
    	return typeDefinition.getType();
    }
    
    
    private Definition definition;


    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print(name.toString());
    }

    @Override
    String prettyPrintNode() {
        return "Identifier (" + getName() + ")";
    }

    @Override
    protected void prettyPrintType(PrintStream s, String prefix) {
        Definition d = getDefinition();
        if (d != null) {
            s.print(prefix);
            s.print("definition: ");
            s.print(d);
            s.println();
        }
    }
    
//    public void codeCond(DecacCompiler compiler, boolean bool, Label etiquette) {
//		assert(getType().isBoolean());
//    	compiler.addInstruction(new LOAD(getAdresse(), Register.R0));
//		compiler.addInstruction(new CMP(0, Register.R0));
//    	if (bool) {
//			compiler.addInstruction(new BNE(etiquette));
//		}
//    	else {
//			compiler.addInstruction(new BEQ(etiquette));
//    	}
//	}

	@Override
	public
	void codeExp(DecacCompiler compiler, int registerPointer) {
		compiler.addInstruction(new LOAD(getAdresse(), Register.getR(registerPointer)));
	}
	
	@Override
	public boolean adressable() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public DAddr getAdresse() {
		// TODO Auto-generated method stub
		return definition.getOperand();
	}

}
