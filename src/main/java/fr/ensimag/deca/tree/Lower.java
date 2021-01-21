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
	void codeGenInst(DecacCompiler compiler) {
		int registerPointer = getRP(compiler);
		//CMP operands
		codeCMP(compiler);
		//Scc instruction
		compiler.addInstruction(new SLT(Register.getR(getRP(compiler))));		
		
		assert registerPointer == getRP(compiler);
	}

	public void codeCond(DecacCompiler compiler, boolean bool, Label etiquette) {
		//CMP operands
		codeCMP(compiler);
		//Bcc instruction
		if (bool) {
			compiler.addInstruction(new BLT(etiquette));

		}
		else {
			compiler.addInstruction(new BGE(etiquette));
		}
	}
}
