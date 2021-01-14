/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.deca.tree;

import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;

/**
 *
 * @author ensimag
 */
public class DeclField extends AbstractDeclField {
    private AbstractIdentifier ident;
    private AbstractInitialization expr;
    public DeclField(AbstractIdentifier ident, AbstractInitialization expr){
        this.ident = ident;
        this.expr  = expr;
    }
    @Override
    public void decompile(IndentPrintStream s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
           ident.prettyPrint(s, prefix, false);
           expr.prettyPrint(s, prefix, false);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
