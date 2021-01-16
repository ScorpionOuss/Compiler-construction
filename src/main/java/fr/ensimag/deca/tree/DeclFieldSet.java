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
public class DeclFieldSet extends AbstractDeclFieldSet {
    private Visibility visibility;
    private AbstractIdentifier type;
    private ListDeclField listDeclField;
    public DeclFieldSet(Visibility v, AbstractIdentifier t, ListDeclField ldf){
        visibility = v;
        type = t;
        listDeclField = ldf;
        
    }
    
    @Override
    public void decompile(IndentPrintStream s) {
        s.print(visibility.toString());
        s.print(" ");
        type.decompile(s);
        s.print(" ");
        listDeclField.decompile(s);
        s.println(";");
        
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
//      visibility.prettyPrint(s, prefix, false);
      type.prettyPrint(s, prefix, false);
      listDeclField.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
