package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;

/**
 * List of expressions (eg list of parameters).
 *
 * @author gl16
 * @date 01/01/2021
 */
public class ListExpr extends TreeList<AbstractExpr> {


    @Override
    public void decompile(IndentPrintStream s) {
         for (AbstractExpr c : getList()) {
            c.decompile(s);
            s.println();
        }
    }

    void decompilePrint(IndentPrintStream s) {
         int i;
         for ( i = 0; i < getList().size()-1; i++) {
            AbstractExpr c =  getList().get(i);
            c.decompile(s);
            s.print(",");
        }
         getList().get(i).decompile(s);
    }
}
