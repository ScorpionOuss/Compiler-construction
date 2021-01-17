package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BGE;
import fr.ensimag.ima.pseudocode.instructions.BLT;
import fr.ensimag.ima.pseudocode.instructions.SGT;
import fr.ensimag.ima.pseudocode.instructions.SLT;

/**
 *
 * @author gl16
 * @date 01/01/2021
 */
public class Lower extends AbstractOpIneq {

    public Lower(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "<";
    }


	@Override
	public
	void codeExp(DecacCompiler compiler, int registerPointer) {
		//CMP operands
		codeCMP(compiler, registerPointer);
		//Scc instruction
		compiler.addInstruction(new SLT(Register.getR(registerPointer)));		
	}

	public void codeCond(DecacCompiler compiler, boolean bool, Label etiquette) {
		//CMP operands
		codeCMP(compiler, compiler.getRegisterPointer());
		//Bcc instruction
		if (bool) {
			compiler.addInstruction(new BLT(etiquette));

		}
		else {
			compiler.addInstruction(new BGE(etiquette));
		}
	}
}
