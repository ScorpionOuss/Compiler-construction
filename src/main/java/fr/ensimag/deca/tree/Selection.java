package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.LabelOperand;
import fr.ensimag.ima.pseudocode.NullOperand;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.LOAD;

import org.apache.commons.lang.Validate;import org.apache.log4j.helpers.Loader;

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
    	ClassType classType = type.asClassType("selection undefined for non class types", obj.getLocation());
    	type = field.verifySelection(compiler, localEnv, classType.getDefinition(), currentClass);
        this.setType(type);
        return type;
    }
        
        
    @Override
    protected void codeGenInst(DecacCompiler compiler){
    	DAddr addr = getAdresse(compiler);
    	compiler.addInstruction(new LOAD(addr, 
    			Register.getR(getRP(compiler))));
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
	public DAddr getAdresse(DecacCompiler compiler) {
		obj.codeGenInst(compiler);
		compiler.addInstruction(new CMP(new NullOperand(),
				Register.getR(getRP(compiler))));
		compiler.addInstruction(new BEQ(new Label("dereferencement.null")));
		return new RegisterOffset(field.getFieldDefinition().getIndex(),
				Register.getR(getRP(compiler)));
	}
	
	@Override
	public boolean adressable() {
		// TODO Auto-generated method stub
		return false;
	}
}

