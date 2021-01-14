package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BGT;
import fr.ensimag.ima.pseudocode.instructions.BLE;

/**
 *
 * @author gl16
 * @date 01/01/2021
 */
public class Greater extends AbstractOpIneq {

    public Greater(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return ">";
    }


	@Override
	public
	void codeExp(DecacCompiler compiler, int registerPointer) {
        throw new UnsupportedOperationException("not yet implemented");
		
	}

	public void codeCond(DecacCompiler compiler, boolean bool, Label etiquette) {
		//CMP operands
		codeCMP(compiler);
		//Bcc instruction
		if (bool) {
			compiler.addInstruction(new BGT(etiquette));

		}
		else {
			compiler.addInstruction(new BLE(etiquette));
		}
	}
	
}
