package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.VariableDefinition;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;
/**
 * @author gl16
 * @date 01/01/2021
 */
public class DeclVar extends AbstractDeclVar {

    
    final private AbstractIdentifier type;
    final private AbstractIdentifier varName;
    final private AbstractInitialization initialization;

    public DeclVar(AbstractIdentifier type, AbstractIdentifier varName, AbstractInitialization initialization) {
        Validate.notNull(type);
        Validate.notNull(varName);
        Validate.notNull(initialization);
        this.type = type;
        this.varName = varName;
        this.initialization = initialization;
    }

    @Override
    protected void verifyDeclVar(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
		Type varType = type.verifyType(compiler);
    	try {
    		VariableDefinition varDefinition = new VariableDefinition(varType, varName.getLocation());
			localEnv.declare(varName.getName(), varDefinition);
			varName.setType(varType);
			varName.setDefinition(varDefinition);
		} catch (DoubleDefException e) {
			throw new ContextualError("Variable " + varName.getName().toString() + " is already declared", varName.getLocation());
		}
    	initialization.verifyInitialization(compiler, varType, localEnv, currentClass);
    }

    
    @Override
    public void decompile(IndentPrintStream s) {
       type.decompile(s);
       s.print(" ");
       varName.decompile(s);
       initialization.decompile(s);
       s.print(";");
    }

    @Override
    protected
    void iterChildren(TreeFunction f) {
        type.iter(f);
        varName.iter(f);
        initialization.iter(f);
    }
    
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        varName.prettyPrint(s, prefix, false);
        initialization.prettyPrint(s, prefix, true);
    }

	@Override
	protected void codeGenAndLinkDeclVariable(DecacCompiler compiler) {
		//setOperand Daddr
		assert(varName.getDefinition() instanceof VariableDefinition);//defensive programming
		
		varName.getDefinition().setOperand(new RegisterOffset(compiler.stackManager.recoverAndIncrement(),
				Register.LB));
		
		//à faire: traiter l'initialisation
		initialization.codeGenInitialization(compiler);
		initialization.STOREInstrution(compiler, varName.getDefinition());
	}
	
	@Override
	protected void codeGenAndLinkDeclVariableMain(DecacCompiler compiler) {
		//setOperand Daddr
		assert(varName.getDefinition() instanceof VariableDefinition);//defensive programming
		
		varName.getDefinition().setOperand(new RegisterOffset(compiler.stackManager.recoverAndIncrement()+
				compiler.stackManager.getMethodStackCounter(),
				Register.GB));
		
		//à faire: traiter l'initialisation
		initialization.codeGenInitialization(compiler);
		initialization.STOREInstrution(compiler, varName.getDefinition());
	}
}
