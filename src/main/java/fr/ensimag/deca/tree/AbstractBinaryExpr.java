package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.POP;
import fr.ensimag.ima.pseudocode.instructions.PUSH;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * Binary expressions.
 *
 * @author gl16
 * @date 01/01/2021
 */
public abstract class AbstractBinaryExpr extends AbstractExpr {

    public AbstractExpr getLeftOperand() {
        return leftOperand;
    }

    public AbstractExpr getRightOperand() {
        return rightOperand;
    }

    protected void setLeftOperand(AbstractExpr leftOperand) {
        Validate.notNull(leftOperand);
        this.leftOperand = leftOperand;
    }

    protected void setRightOperand(AbstractExpr rightOperand) {
        Validate.notNull(rightOperand);
        this.rightOperand = rightOperand;
    }

    private AbstractExpr leftOperand;
    private AbstractExpr rightOperand;

    public AbstractBinaryExpr(AbstractExpr leftOperand,
            AbstractExpr rightOperand) {
        Validate.notNull(leftOperand, "left operand cannot be null");
        Validate.notNull(rightOperand, "right operand cannot be null");
        Validate.isTrue(leftOperand != rightOperand, "Sharing subtrees is forbidden");
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }


    @Override
    public void decompile(IndentPrintStream s) {
        s.print("(");
        getLeftOperand().decompile(s);
        s.print(" " + getOperatorName() + " ");
        getRightOperand().decompile(s);
        s.print(")");
    }

    abstract protected String getOperatorName();

    @Override
    protected void iterChildren(TreeFunction f) {
        leftOperand.iter(f);
        rightOperand.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        leftOperand.prettyPrint(s, prefix, false);
        rightOperand.prettyPrint(s, prefix, true);
    }
	@Override
	public boolean adressable() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public DVal getAdresse(DecacCompiler compiler) {
		// TODO Auto-generated method stub
		return null;
	}

	public void depassementCapacite(DecacCompiler compiler) {
		assert(getRP(compiler) == getMP(compiler));
		// PUSH Rmax
		compiler.addInstruction(new PUSH(Register.getR(getMP(compiler))));
		compiler.stackManager.incrementStackCounterMax(1);
		//Execution 
		getRightOperand().codeGenInst(compiler);;
		
		//LOAD Rmax, R1
		compiler.addInstruction(new LOAD(Register.getR(getMP(compiler)), Register.R1));
		
		//POP Rmax
		compiler.addInstruction(new POP(Register.getR(getMP(compiler))));
	}
}
