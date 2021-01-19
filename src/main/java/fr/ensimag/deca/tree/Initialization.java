package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.STORE;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * @author gl16
 * @date 01/01/2021
 */
public class Initialization extends AbstractInitialization {

    public AbstractExpr getExpression() {
        return expression;
    }

    private AbstractExpr expression;

    public void setExpression(AbstractExpr expression) {
        Validate.notNull(expression);
        this.expression = expression;
    }

    public Initialization(AbstractExpr expression) {
        Validate.notNull(expression);
        this.expression = expression;
    }

    @Override
    protected void verifyInitialization(DecacCompiler compiler, Type t,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
    	Type type = expression.verifyExpr(compiler, localEnv, currentClass);
    	if (!t.assignCompatible(type)) {
    		throw new ContextualError("Incompatible types for assignement", this.getLocation());
    	}
    	if (t.isFloat() && type.isInt()) {
    		expression = new ConvFloat(expression);
    		expression.setType(t);
    	}
    }


    @Override
    public void decompile(IndentPrintStream s) {
       s.print(" = ");
       expression.decompile(s);
    }

    @Override
    protected
    void iterChildren(TreeFunction f) {
        expression.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        expression.prettyPrint(s, prefix, true);
    }

	@Override
	protected void codeGenInitialization(DecacCompiler compiler) {
		expression.codeGenInst(compiler);
	}

	@Override
	protected void STOREInstrution(DecacCompiler compiler, Definition definition) {
		compiler.addInstruction(new STORE(Register.getR(compiler.getRegisterPointer()),
				definition.getOperand()));
	}
	
	

	@Override
	protected void codeGenexp(DecacCompiler compiler, int registerPointer) {
		expression.codeExp(compiler, registerPointer);
	}

	@Override
	protected void STOREInstrution(DecacCompiler compiler, Definition definition, int registerPointer) {
		compiler.addInstruction(new STORE(Register.getR(registerPointer),
				definition.getOperand()));
	}
}
