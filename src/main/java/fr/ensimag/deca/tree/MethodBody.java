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
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;

/**
 *
 * @author ensimag
 */
public class MethodBody extends AbstractMethodBody {
    private ListDeclVar listDeclVar;
    private ListInst listInst;
    public MethodBody(){}
    public MethodBody(ListDeclVar listDeclVar,  ListInst listInst){
        this.listDeclVar = listDeclVar;
        this.listInst = listInst;
    }

    protected void verifyMethodBody(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void decompile(IndentPrintStream s) {
       s.println("{");
       s.indent();
       listDeclVar.decompile(s);
       listInst.decompile(s);
       s.unindent();
       s.println("}");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        listDeclVar.prettyPrint(s, prefix, false);
        listInst.prettyPrint(s, prefix, false);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
	@Override
	public void GenbodyCodeVars(DecacCompiler compiler) {
    	//Variables declaration
    	listDeclVar.codeGenAndLinkListDeclVariable(compiler);
	}
    	
	@Override
	public void GenbodyCodeInsts(DecacCompiler compiler) {	
    	//instructions
        listInst.codeGenListInst(compiler);
	}
}
