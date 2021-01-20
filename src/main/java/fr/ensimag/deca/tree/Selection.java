package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.GPRegister;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;
public class Selection extends AbstractLValue {

    AbstractExpr obj;
    AbstractIdentifier field;

    public Selection(AbstractExpr obj, AbstractIdentifier field) {
        Validate.notNull(obj);
        Validate.notNull(field);
        this.obj = obj;
        this.field = field;
    }

    public  SymbolTable.Symbol getName(){
        return field.getName();
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
    		ClassDefinition currentClass) throws ContextualError {
    	Type type = obj.verifyExpr(compiler, localEnv, currentClass);
    	type = field.verifyExpr(compiler, type.asClassType("selection undefined for non class types", obj.getLocation()).getDefinition().getMembers(),
    			currentClass);
        this.setType(type);
        return type;
    }
        
        
    @Override
    protected void codeGenInst(DecacCompiler compiler){
    	
    }
    
    
    @Override
    protected void codeGenPrint(DecacCompiler compiler){}
    protected void codeGenPrintX(DecacCompiler compiler){}
    @Override
    public void decompile(IndentPrintStream s) {
        obj.decompile(s);
        s.print(".");
        field.decompile(s);
    }
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        obj.prettyPrint(s, prefix, false);
        field.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        obj.iter(f);
        field.iter(f);
    }
    
	@Override
	public DAddr getAdresse() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean adressable() {
		// TODO Auto-generated method stub
		return false;
	}
}

