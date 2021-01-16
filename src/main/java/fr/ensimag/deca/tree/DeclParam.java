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
public class DeclParam extends AbstractDeclParam{
    
    private AbstractIdentifier type;
    private AbstractIdentifier ident;
   
    
    public DeclParam(AbstractIdentifier type,AbstractIdentifier ident){
        this.type = type;
        this.ident = ident;
       
    }

    @Override
    public void decompile(IndentPrintStream s) {
       type.decompile(s);
       s.print(" ");;
       ident.decompile();
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        ident.prettyPrint(s, prefix, false);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
