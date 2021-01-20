package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.BNE;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.SEQ;

/**
 *
 * @author gl16
 * @date 01/01/2021
 */
public class Equals extends AbstractOpExactCmp {

    public Equals(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "==";
    }


	@Override
	public
	void codeExp(DecacCompiler compiler, int registerPointer) {
		codeCMP(compiler, registerPointer);
		compiler.addInstruction(new SEQ(Register.getR(registerPointer)));
	}    
	
//	public void codeGenCond(DecacCompiler compiler, Label etiquette) {
//		if (getRightOperand().adressable()) {
//			getLeftOperand().codeExp(compiler, compiler.getRegisterPointer());
//			compiler.addInstruction(new CMP(getRightOperand().getAdresse(),
//					Register.getR(compiler.getRegisterPointer())));
//			compiler.addInstruction(new BEQ(etiquette));
//		}
//	}
    
	public void codeCond(DecacCompiler compiler, boolean bool, Label etiquette) {
		codeCMP(compiler, compiler.getRegisterPointer());
		if (bool) {
			compiler.addInstruction(new BEQ(etiquette));

		}
		else {
			compiler.addInstruction(new BNE(etiquette));
		}
	}
}
