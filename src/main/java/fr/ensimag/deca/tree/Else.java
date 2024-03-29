package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * Full if/else if/else statement.
 *
 * @author gl16
 * @date 01/01/2021
 */
public class Else extends AbstractInst {
    private final ListInst thenBranch;
    private ListInst elseBranch;

    public Else(AbstractExpr condition, ListInst thenBranch, ListInst elseBranch) {
        Validate.notNull(thenBranch);
        Validate.notNull(elseBranch);
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }
    
    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType)
            throws ContextualError {
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler, String ame) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.println("else{");
        s.indent();
        thenBranch.decompile();
        elseBranch.decompile();
        s.indent();
        s.println("}");
    }

    @Override
    protected
    void iterChildren(TreeFunction f) {
        thenBranch.iter(f);
        elseBranch.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        thenBranch.prettyPrint(s, prefix, false);
        elseBranch.prettyPrint(s, prefix, true);
    }
}
