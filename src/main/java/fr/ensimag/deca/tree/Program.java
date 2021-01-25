package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.ImmediateString;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.LabelOperand;
import fr.ensimag.ima.pseudocode.NullOperand;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.*;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

/**
 * Deca complete program (class definition plus main block)
 *
 * @author gl16
 * @date 01/01/2021
 */
public class Program extends AbstractProgram {
    private static final Logger LOG = Logger.getLogger(Program.class);
    
    public Program(ListDeclClass classes, AbstractMain main) {
        Validate.notNull(classes);
        Validate.notNull(main);
        this.classes = classes;
        this.main = main;
    }
    public ListDeclClass getClasses() {
        return classes;
    }
    public AbstractMain getMain() {
        return main;
    }
    private ListDeclClass classes;
    private AbstractMain main;

    @Override
    public void verifyProgram(DecacCompiler compiler) throws ContextualError {
        //LOG.debug("verify program: start");
        classes.verifyListClass(compiler);
        classes.verifyListClassMembers(compiler);
        classes.verifyListClassBody(compiler);
        main.verifyMain(compiler);
        //LOG.debug("verify program: end");
    }
    
    @Override
    public void codeGenProgram(DecacCompiler compiler) {
    	/*First pass*/
    	codeGenObjectTable(compiler);
    	classes.buildClassesTable(compiler);
    	/*Main execution*/
    	compiler.addComment("Main program");
        main.codeGenMain(compiler);
        //End ...
        compiler.addInstruction(new HALT());
        //Exceptions handlers
        compiler.addStackException();
        compiler.addStackVerification();
        compiler.addIOException();
        compiler.addArithFloatException();
        compiler.addZeroDivision();
        compiler.addNullRefException();
        compiler.addHeapException();
        classes.methodsGeneration(compiler);
        insertObjectCode(compiler);
        codeInstanceOf(compiler);
    }


    /**
     * insertObjectCode
     * @param compiler
     */
	private void insertObjectCode(DecacCompiler compiler) {
    	//code.Object.equals

		Label endSuccess = new Label("end.Success.objet");
		compiler.addLabel(new Label("code.Object.equals"));
		compiler.addInstruction(new LOAD(new RegisterOffset(-2, 
				Register.LB),
				Register.getR(compiler.registersManag.getRegisterPointer())));
		compiler.addInstruction(new LOAD(new RegisterOffset(-3, 
				Register.LB), 
				Register.R0));
		compiler.addInstruction(new CMP(Register.R0,
				Register.getR(compiler.registersManag.getRegisterPointer())));
		compiler.addInstruction(new BEQ(endSuccess));
		compiler.addInstruction(new LOAD(0, 
				Register.R0));
		compiler.addInstruction(new RTS());
		compiler.addLabel(endSuccess);
		compiler.addInstruction(new LOAD(1, 
				Register.R0));
		compiler.addInstruction(new RTS());
    	
    	//init.object

    	compiler.addLabel(new Label("init.object"));
    	compiler.addInstruction(new RTS());
	}
	
	/**
	 * 
	 * @param compiler
	 */
	private void codeInstanceOf(DecacCompiler compiler) {
		Label debutIo = new Label("debut.io");
		Label sinonIo1 = new Label("sinon.io.1");
		Label sinonIo2 = new Label("sinon.io.2");
		//-2(LB) contient l'objet
		// -3(LB) contient la classe
		compiler.addLabel(debutIo);
		compiler.addInstruction(new LOAD(new RegisterOffset(-3, Register.LB), 
				Register.getR(compiler.registersManag.getRegisterPointer())));
		compiler.addInstruction(new LOAD(new RegisterOffset(-2, Register.LB),
				Register.R0));
		compiler.addInstruction(new LOAD(new RegisterOffset(0, 
				Register.R0), Register.R0));
		compiler.addInstruction(new CMP(Register.R0, 
				Register.getR(compiler.registersManag.getRegisterPointer())));
		compiler.addInstruction(new BNE(sinonIo1));
		compiler.addInstruction(new LOAD(1, Register.getR(compiler.registersManag.getRegisterPointer())));
		compiler.addInstruction(new RTS());
		
		compiler.addLabel(sinonIo1);
		
		compiler.addInstruction(new LOAD(new RegisterOffset(0, Register.R0), 
				Register.R1));
		compiler.addInstruction(new CMP(new NullOperand(), Register.R1));
		compiler.addInstruction(new BNE(sinonIo2));
		//compiler.addInstruction(new WSTR(new ImmediateString("beeeeq sinon1")));
		//compiler.addInstruction(new WNL());
		compiler.addInstruction(new LOAD(0, Register.getR(compiler.registersManag.getRegisterPointer())));
		compiler.addInstruction(new RTS());
		
		compiler.addLabel(sinonIo2);
		//compiler.addInstruction(new WSTR(new ImmediateString("bnnnnq sinon1")));
		//compiler.addInstruction(new WNL());
		compiler.addInstruction(new LOAD(new RegisterOffset(-3, Register.LB),
				Register.getR(compiler.registersManag.getRegisterPointer())));
		compiler.addInstruction(new LEA(new RegisterOffset(0, Register.R0), 
				Register.R0));
		compiler.addInstruction(new ADDSP(new ImmediateInteger(2)));
		//class -3(LB)
		compiler.addInstruction(new STORE(Register.R0, 
				new RegisterOffset(0, Register.SP)));
		//expr -2(LB)
		compiler.addInstruction(new STORE(Register.getR(compiler.registersManag.getRegisterPointer()),
				new RegisterOffset(-1, Register.SP)));

		compiler.addInstruction(new BSR(debutIo));
		compiler.addInstruction(new SUBSP(new ImmediateInteger(2)));
		compiler.addInstruction(new RTS());;
		compiler.addInstruction(new ERROR());
		
	}
	
	/*
	 * insertObjectCode
	 */
	private void codeGenObjectTable(DecacCompiler compiler) {
    	//code.Object.equals
		compiler.addInstruction(new LOAD(new NullOperand(), Register.R0));
    	
    	compiler.addInstruction(new STORE(Register.R0, 
    			new RegisterOffset(1, Register.GB)));
    	
    	compiler.addInstruction(new LOAD(new LabelOperand(new 
    			Label("code.Object.equals")), Register.R0));
    	
    	compiler.addInstruction(new STORE(Register.R0, 
    			new RegisterOffset(2, Register.GB)));
    	
	}
    
	@Override
    public void decompile(IndentPrintStream s) {
        getClasses().decompile(s);
        getMain().decompile(s);
    }
    
    @Override
    protected void iterChildren(TreeFunction f) {
        classes.iter(f);
        main.iter(f);
    }
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        classes.prettyPrint(s, prefix, false);
        main.prettyPrint(s, prefix, true);
    }
}
