package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.WFLOATX;
import fr.ensimag.ima.pseudocode.instructions.WNL;

/**
 * @author gl16
 * @date 01/01/2021
 */
public class Println extends AbstractPrint {

    /**
     * @param arguments arguments passed to the print(...) statement.
     * @param printHex if true, then float should be displayed as hexadecimal (printlnx)
     */
    public Println(boolean printHex, ListExpr arguments) {
        super(printHex, arguments);
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler, String name) {
        super.codeGenInst(compiler, name);
        compiler.addInstruction(new WNL());
    }
    
    @Override
    String getSuffix() {
        if (super.getPrintHex())return "lnx";
        return "ln";
    }
}
