package fr.ensimag.deca.tree;


import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.*;
import fr.ensimag.ima.pseudocode.instructions.*;
import org.apache.commons.lang.Validate;
import java.io.PrintStream;
import java.util.List;
public class MethodCall extends AbstractExpr{

    AbstractExpr obj;
    AbstractIdentifier method;
    ListExpr params;

    public MethodCall(AbstractExpr obj, AbstractIdentifier method, ListExpr params) {
        Validate.notNull(obj);
        Validate.notNull(method);
        Validate.notNull(params);

        this.obj = obj;
        this.method = method;
        this.params = params;
    }


    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
    	Type type = obj.verifyExpr(compiler, localEnv, currentClass);
    	ClassType classType = type.asClassType("selection undefined for non class types", obj.getLocation());
    	type = method.verifyExpr(compiler, classType.getDefinition().getMembers(), currentClass);
    	Definition def= classType.getDefinition().getMembers().get(method.getName()); 
    	MethodDefinition methodDef = def.asMethodDefinition("undefined method identifier", getLocation());
    	Signature signature = methodDef.getSignature();
    	if (params.size() != signature.size()) {
    		throw new ContextualError("the number of parameters is incorrect", getLocation());
    	}
    	List<AbstractExpr> listParam = params.getList();
    	for (int i = 0; i < params.size(); i++) {
    		listParam.get(i).verifyRValue(compiler, localEnv, currentClass, signature.paramNumber(i));
    	}
    	return type;
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler){}

    @Override
    protected void codeGenPrint(DecacCompiler compiler){}

    @Override
    public void decompile(IndentPrintStream s) {
        obj.decompile(s);
        s.print("(");
        method.decompile(s);
        s.print(")");
        params.decompile(s);
         s.println(")");
        
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        obj.prettyPrint(s, prefix, false);
        method.prettyPrint(s, prefix, false);
        params.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        obj.iter(f);
        method.iter(f);
        params.iter(f);
    }


	@Override
	public void codeExp(DecacCompiler compiler, int registerPointer) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean adressable() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public DVal getAdresse() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void codeCond(DecacCompiler compiler, boolean bool, Label endAnd) {
		// TODO Auto-generated method stub
		
	}
}

