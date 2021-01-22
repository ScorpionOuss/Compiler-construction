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

import org.apache.commons.lang.Validate;

/**
 *
 * @author gl16
 */
public class MethodBody extends AbstractMethodBody {

    private ListDeclVar listDeclVar;
    private ListInst listInst;

    public MethodBody(ListDeclVar listDeclVar,  ListInst listInst){
    	Validate.notNull(listDeclVar);
    	Validate.notNull(listInst);
        this.listDeclVar = listDeclVar;
        this.listInst = listInst;
    }

	@Override
	protected void verifyMethodBody(DecacCompiler compiler, EnvironmentExp localEnv,
			ClassDefinition currentClass, Type returnType) throws ContextualError {
		listDeclVar.verifyListDeclVariable(compiler, localEnv, currentClass);
		listInst.verifyListInst(compiler, localEnv, currentClass, returnType);
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
        listInst.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
    	listDeclVar.iter(f);
    	listInst.iter(f);
    }

    
	@Override
	public void GenbodyCodeVars(DecacCompiler compiler) {
    	//Variables declaration
    	listDeclVar.codeGenAndLinkListDeclVariable(compiler);
	}
    	
	@Override
	public void GenbodyCodeInsts(DecacCompiler compiler, String name) {	
    	//instructions
        listInst.codeGenListInst(compiler, name);
	}
}
