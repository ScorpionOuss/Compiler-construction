/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;

import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;

/**
 *
 * @author gl16
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


	@Override
	protected void GenbodyCodeVars(DecacCompiler compiler) {
		throw new UnsupportedOperationException("Not supported yet.");		
		
	}

	@Override
	protected void GenbodyCodeInsts(DecacCompiler compiler, String name) {
	}

	@Override
    protected void verifyMethodBody(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition classDefinition, Type returnType) throws ContextualError {
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        code.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
    }
}
