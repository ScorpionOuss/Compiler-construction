package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BRA;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * Full if/else if/else statement.
 *
 * @author gl16
 * @date 01/01/2021
 */
public class IfThenElse extends AbstractInst {
    
    private final AbstractExpr condition; 
    private final ListInst thenBranch;
    private ListInst elseBranch;

    public IfThenElse(AbstractExpr condition, ListInst thenBranch, ListInst elseBranch) {
        Validate.notNull(condition);
        Validate.notNull(thenBranch);
        Validate.notNull(elseBranch);
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }
    
    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType)
            throws ContextualError {
    	Type type = condition.verifyExpr(compiler, localEnv, currentClass);
    	if (type.isBoolean()) {
    		for (AbstractInst inst: thenBranch.getList()) {
    			inst.verifyInst(compiler, localEnv, currentClass, returnType);
    		}
    		for (AbstractInst inst: elseBranch.getList()) {
    			inst.verifyInst(compiler, localEnv, currentClass, returnType);
    		}
    	} else throw new ContextualError("if condition must be of boolean type", this.getLocation());
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
    	int labelIndex = compiler.incrementIfCounter();
    	Label eSinon = new Label("E_Sinon." + String.valueOf(labelIndex));
    	Label eFin = new Label("E_Fin." + String.valueOf(labelIndex));
    	condition.codeCond(compiler, false, eSinon);
    	thenBranch.codeGenListInst(compiler);
    	compiler.addInstruction(new BRA(eFin));
    	compiler.addLabel(eSinon);
    	elseBranch.codeGenListInst(compiler);
    	compiler.addLabel(eFin);
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("if(");
        condition.decompile();
        s.println("){");
        s.indent();
        thenBranch.decompile();
        s.indent();
        s.println("}");
        s.println("else{");
        s.indent();
        elseBranch.decompile();
        s.indent();
        s.println("}");
    }

    @Override
    protected
    void iterChildren(TreeFunction f) {
        condition.iter(f);
        thenBranch.iter(f);
        elseBranch.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        condition.prettyPrint(s, prefix, false);
        thenBranch.prettyPrint(s, prefix, false);
        elseBranch.prettyPrint(s, prefix, true);
    }
}
