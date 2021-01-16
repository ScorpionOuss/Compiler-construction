/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.deca.tree;

import fr.ensimag.deca.tools.IndentPrintStream;

/**
 *
 * @author ensimag
 */
public  class ListDeclFieldSet extends TreeList<AbstractDeclFieldSet> {

    @Override
    public void decompile(IndentPrintStream s) {
            for (AbstractDeclFieldSet c : getList()) {
            c.decompile(s);
            s.println();
        }
    }
    
}
