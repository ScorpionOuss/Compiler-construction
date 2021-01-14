package fr.ensimag.deca.context;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tree.Location;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Operand;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.STORE;

/**
 * Definition of a variable.
 *
 * @author gl16
 * @date 01/01/2021
 */
public class VariableDefinition extends ExpDefinition {
    public VariableDefinition(Type type, Location location) {
        super(type, location);
    }

    @Override
    public String getNature() {
        return "variable";
    }

    @Override
    public boolean isExpression() {
        return true;
    }


}
