package fr.ensimag.deca.context;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tree.ListDeclMethod;
import fr.ensimag.deca.tree.Location;
import fr.ensimag.ima.pseudocode.LabelOperand;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;

import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.commons.lang.Validate;

/**
 * Definition of a class.
 *
 * @author gl16
 * @date 01/01/2021
 */
public class ClassDefinition extends TypeDefinition {


    public void setNumberOfFields(int numberOfFields) {
        this.numberOfFields = numberOfFields;
    }

    public int getNumberOfFields() {
        return numberOfFields;
    }

    public int incNumberOfFields() {
        numberOfFields++;
        return numberOfFields;
    }

    public int getNumberOfMethods() {
        return numberOfMethods;
    }

    public void setNumberOfMethods(int n) {
        Validate.isTrue(n >= 0);
        numberOfMethods = n;
    }
    
    public int incNumberOfMethods() {
        numberOfMethods++;
        return numberOfMethods;
    }

    private int numberOfFields = 0;
    private int numberOfMethods = 0;
    
    @Override
    public boolean isClass() {
        return true;
    }
    
    @Override
    public ClassType getType() {
        // Cast succeeds by construction because the type has been correctly set
        // in the constructor.
        return (ClassType) super.getType();
    };

    public ClassDefinition getSuperClass() {
        return superClass;
    }

    private final EnvironmentExp members;
    private final ClassDefinition superClass; 

    public EnvironmentExp getMembers() {
        return members;
    }

    public ClassDefinition(ClassType type, Location location, ClassDefinition superClass) {
        super(type, location);
        EnvironmentExp parent;
        if (superClass != null) {
            parent = superClass.getMembers();
        } else {
            parent = null;
        }
        members = new EnvironmentExp(parent);
        this.superClass = superClass;
    }
    
    @Override
    public String getNature() {
        return "class";
    }

//	public void buildTable(DecacCompiler compiler, int offset) {
//		for (ExpDefinition def : members.environment.values()) {
//			if (def.isMethod()) {
//				compiler.addInstruction(new LOAD(new LabelOperand(def.getLabel()),
//						Register.R0));
//				
//				compiler.addInstruction(new STORE(Register.R0, 
//						new RegisterOffset(offset + def.getIndex(),
//								Register.GB)));
//			}
//		}
//		if (superClass!= null) {
//			superClass.buildTable(compiler, offset);
//		}
//	}
    
	public void buildTable(DecacCompiler compiler, ArrayList<MethodDefinition> tab) {
	for (ExpDefinition def : members.environment.values()) {
		if (def.isMethod()) {
			if (tab.get(def.getIndex() - 1) == null) {
				tab.set(def.getIndex() - 1, (MethodDefinition)def);
			}
		}
	}
	if (superClass!= null) {
		superClass.buildTable(compiler, tab);
	}
}

	public boolean instanceOf(ClassDefinition definition) {
		if (getOperand() == definition.getOperand()) {
			return true;
		}
		else {
			if (superClass == null) {
				return false;
			}
			else {
				return instanceOf(superClass);
			}
		}
	}

//    public void buildTable(DecacCompiler compiler, LinkedList<Definition> tableau) {
//    	for (ExpDefinition def : members.environment.values()) {
//    		if (def.isMethod()) {
////    			tableau.add(e)
//    		}
//    	}
//    }

}
