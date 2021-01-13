package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;


public class Return extends AbstractInst {
    private AbstractExpr exp;

    public AbstractExpr getExp() {
        return exp;
    }
    public Return(AbstractExpr exp) {
        Validate.notNull(exp);
        this.exp = exp;
    }
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        throw new UnsupportedOperationException("not yet implemented");
    }
    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType)
            throws ContextualError {
    }
    @Override
    public void decompile(IndentPrintStream s) {
        s.print("return ");
        getExp().decompile(s);
        s.println(";");
    }
    @Override
    protected void iterChildren(TreeFunction f) {
        exp.iter(f);
    }
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        exp.prettyPrint(s, prefix, false);
    }

}

