package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BGE;
import fr.ensimag.ima.pseudocode.instructions.BGT;
import fr.ensimag.ima.pseudocode.instructions.BLE;
import fr.ensimag.ima.pseudocode.instructions.BLT;
import fr.ensimag.ima.pseudocode.instructions.SGE;
import fr.ensimag.ima.pseudocode.instructions.SGT;

/**
 * Operator "x >= y"
 * 
 * @author gl16
 * @date 01/01/2021
 */
public class GreaterOrEqual extends AbstractOpIneq {

    public GreaterOrEqual(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return ">=";
    }


	@Override
	public
	void codeExp(DecacCompiler compiler, int registerPointer) {
		//CMP operands
		codeCMP(compiler);
		//Scc instruction
		compiler.addInstruction(new SGE(Register.getR(registerPointer)));
	}

	public void codeCond(DecacCompiler compiler, boolean bool, Label etiquette) {
		//CMP operands
		codeCMP(compiler);
		//Bcc instruction
		if (bool) {
			compiler.addInstruction(new BGE(etiquette));

		}
		else {
			compiler.addInstruction(new BLT(etiquette));
		}
	}
}
