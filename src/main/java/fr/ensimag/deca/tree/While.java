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
 *
 * @author gl16
 * @date 01/01/2021
 */
public class While extends AbstractInst {
    private AbstractExpr condition;
    private ListInst body;

    public AbstractExpr getCondition() {
        return condition;
    }

    public ListInst getBody() {
        return body;
    }

    public While(AbstractExpr condition, ListInst body) {
        Validate.notNull(condition);
        Validate.notNull(body);
        this.condition = condition;
        this.body = body;
    }

    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType)
            throws ContextualError {
    	condition.verifyCondition(compiler, localEnv, currentClass);
		for (AbstractInst inst: body.getList()) {
			inst.verifyInst(compiler, localEnv, currentClass, returnType);
		}
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("while (");
        getCondition().decompile(s);
        s.println(") {");
        s.indent();
        getBody().decompile(s);
        s.unindent();
        s.print("}");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        condition.iter(f);
        body.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        condition.prettyPrint(s, prefix, false);
        body.prettyPrint(s, prefix, true);
    }
    
    @Override
    protected void codeGenInst(DecacCompiler compiler, String name) {
    	String labelIndex = String.valueOf(compiler.labelsManager.incrementWhileCounter());
    	Label E_Cond = new Label("E_Cond" + labelIndex);
    	Label E_Debut = new Label("E_Debut" + labelIndex);
    	compiler.addInstruction(new BRA(E_Cond));
    	compiler.addLabel(E_Debut);
    	body.codeGenListInst(compiler, name);
    	compiler.addLabel(E_Cond);
    	condition.codeCond(compiler, true, E_Debut);
    }
}
