package fr.ensimag.deca.context;

import java.util.HashMap;
import java.util.Map;

import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.deca.tree.Location;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;

/**
 * Dictionary associating identifier's TypeDefinition to their names.
 * 
 * This is actually a linked list of dictionaries: each EnvironmentType has a
 * pointer to a parentEnvironment, corresponding to superblock (eg superclass).
 * 
 * The dictionary at the head of this list thus corresponds to the "current" 
 * block (eg class).
 * 
 * Searching a definition (through method get) is done in the "current" 
 * dictionary and in the parentEnvironment if it fails. 
 * 
 * Insertion (through method declare) is always done in the "current" dictionary.
 * 
 * @author gl16
 * @date 01/01/2021
 */
public class EnvironmentType {
    // A FAIRE : implémenter la structure de donnée représentant un
    // environnement (association nom -> définition, avec possibilité
    // d'empilement).
	Map<Symbol, TypeDefinition> environment; 

    
    public EnvironmentType() {
    	environment = new HashMap<Symbol, TypeDefinition>();
    	SymbolTable symbolTable = new SymbolTable();
    	try {
    		// type definition
			this.declare(symbolTable.create("int"), new TypeDefinition(new IntType(symbolTable.create("int")), Location.BUILTIN));
			this.declare(symbolTable.create("float"), new TypeDefinition(new FloatType(symbolTable.create("float")), Location.BUILTIN));
			this.declare(symbolTable.create("boolean"), new TypeDefinition(new BooleanType(symbolTable.create("boolean")), Location.BUILTIN));
			this.declare(symbolTable.create("void"), new TypeDefinition(new VoidType(symbolTable.create("void")), Location.BUILTIN));

			// env_exp_object definition
			ClassType objectType = new ClassType(symbolTable.create("Object"), Location.BUILTIN, null);
			ClassDefinition objectDefinition = objectType.getDefinition();
			Signature signature = new Signature();
			signature.add(objectDefinition.getType());
			Type returnType = this.get(symbolTable.create("boolean")).getType();
			MethodDefinition equalsDefinition = new MethodDefinition(returnType, Location.BUILTIN, signature, 1);
			objectDefinition.getMembers().declare(symbolTable.create("equals"), equalsDefinition);
			objectDefinition.setNumberOfMethods(1);
			// type_class(Object) definition
			this.declare(symbolTable.create("Object"), objectDefinition); 
			
		} catch (DoubleDefException e) {
			e.printStackTrace();
			throw new DecacInternalError("EnvironmentType definition Error");
		}
    }


    /**
     * Return the definition of the symbol in the environment, or null if the
     * symbol is undefined.
     */
    public TypeDefinition get(Symbol key) {
    	if (environment.containsKey(key)) {
    		return environment.get(key);
    	} else {
    		return null;
    	}
    }

    /**
     * Add the definition def associated to the symbol name in the environment.
     * 
     * Adding a symbol which is already defined in the environment,
     * - throws DoubleDefException if the symbol is in the "current" dictionary 
     * - or, hides the previous declaration otherwise.
     * 
     * @param name
     *            Name of the symbol to define
     * @param def
     *            Definition of the symbol
     * @throws DoubleDefException
     *             if the symbol is already defined at the "current" dictionary
     *
     */
    public void declare(Symbol name, TypeDefinition def) throws DoubleDefException {
    	// Implemented
    	// to be verified
    	if (environment.containsKey(name)) {
    		throw new DoubleDefException();
    	} else {
    		environment.put(name, def);
    	}
    }

}
