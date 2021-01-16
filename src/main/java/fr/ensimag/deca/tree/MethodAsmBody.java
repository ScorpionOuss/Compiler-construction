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
public class MethodAsmBody extends AbstractMethodBody{
    
   private StringLiteral code;
   public MethodAsmBody(StringLiteral code){
       this.code =  code;
   }
   
    @Override
    public void decompile(IndentPrintStream s) {
        s.print("asm(");
        code.decompile(s);
        s.println(");");
    }
}
