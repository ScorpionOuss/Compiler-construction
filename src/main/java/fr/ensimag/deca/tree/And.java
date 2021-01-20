package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.MUL;

/**
 *
 * @author gl16
 * @date 01/01/2021
 */
public class And extends AbstractOpBool {

    public And(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return "&&";
    }

    /**
     * subFunction of codeGenInst dealing with non overload case
     * @param compiler
     */
   private void normalCase(DecacCompiler compiler) {
	   compiler.registersManag.incrementRegisterPointer();
		getRightOperand().codeGenInst(compiler);
		compiler.addInstruction(new MUL(Register.getR(getRP(compiler)), 
				Register.getR(getRP(compiler) - 1)));
		compiler.registersManag.decrementRegisterPointer();
   }
   
   /**
    * SubFunction of codeGenInst dealing with overload case
    * @param compiler
    */
   private void depassementCase(DecacCompiler compiler) {
	   assert(getRP(compiler) == getMP(compiler));
		/*Manage capacity overrun*/
		depassementCapacite(compiler);
		//Plus
		compiler.addInstruction(new MUL(Register.R1,
				Register.getR(getRP(compiler))));
   }
   
	@Override
	protected void codeGenInst(DecacCompiler compiler) {
		
		getLeftOperand().codeGenInst(compiler);
		if (getRP(compiler) < getMP(compiler)) {
			normalCase(compiler);
		}
		else {
			depassementCase(compiler);
		}
	}


	public void codeCond(DecacCompiler compiler, boolean bool, Label etiquette) {
		
		//〈Code(C,faux,E)〉
		if (!bool) {
			getLeftOperand().codeCond(compiler, bool, etiquette);
			getRightOperand().codeCond(compiler, bool, etiquette);
		}
		//〈Code(C,vrai,E)〉
		else {
			Label endAnd = new Label("endAnd." + 
					String.valueOf(compiler.labelsManager.incrementAndCounter()));
			getLeftOperand().codeCond(compiler, !bool, endAnd);
			getRightOperand().codeCond(compiler, bool, etiquette);
			compiler.addLabel(endAnd);
		}
	}
}
