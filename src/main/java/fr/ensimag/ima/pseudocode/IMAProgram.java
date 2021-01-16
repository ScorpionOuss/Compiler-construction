package fr.ensimag.ima.pseudocode;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;

import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.ERROR;
import fr.ensimag.ima.pseudocode.instructions.TSTO;
import fr.ensimag.ima.pseudocode.instructions.WNL;
import fr.ensimag.ima.pseudocode.instructions.WSTR;

/**
 * Abstract representation of an IMA program, i.e. set of Lines.
 *
 * @author Ensimag
 * @date 01/01/2021
 */
public class IMAProgram {
    private final LinkedList<AbstractLine> lines = new LinkedList<AbstractLine>();

    public void add(AbstractLine line) {
        lines.add(line);
    }

    public void addComment(String s) {
        lines.add(new Line(s));
    }

    public void addLabel(Label l) {
        lines.add(new Line(l));
    }

    public void addInstruction(Instruction i) {
        lines.add(new Line(i));
    }

    public void addInstruction(Instruction i, String s) {
        lines.add(new Line(null, i, s));
    }

    /**
     * Append the content of program p to the current program. The new program
     * and p may or may not share content with this program, so p should not be
     * used anymore after calling this function.
     */
    public void append(IMAProgram p) {
        lines.addAll(p.lines);
    }
    
    /**
     * Add a line at the front of the program.
     */
    public void addFirst(Line l) {
        lines.addFirst(l);
    }

    /**
     * Display the program in a textual form readable by IMA to stream s.
     */
    public void display(PrintStream s) {
        for (AbstractLine l: lines) {
            l.display(s);
        }
    }

    /**
     * Return the program in a textual form readable by IMA as a String.
     */
    public String display() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream s = new PrintStream(out);
        display(s);
        return out.toString();
    }

    public void addFirst(Instruction i) {
        addFirst(new Line(i));
    }
    
    public void addFirst(Instruction i, String comment) {
        addFirst(new Line(null, i, comment));
    }

    private final int  TSTOLocation = 1;
    
    /*
     * add the handler of stack_overflow_error in the assembly code
     */
	public void addStackException() {
		Label stackErrorLabel = new Label("stack_overflow_error");
		addLabel(stackErrorLabel);
		
		addInstruction(new WSTR(new ImmediateString("Error: Stack Overflow")));
		addInstruction(new WNL());
		addInstruction(new ERROR());
	}
	
	/*
	 * add TSTO and BOV instructions in the code
	 * in order to ensure a correct execution
	 * regarding stack_overflow_error
	 */
	public void addStackVerification(int counterMAx) {
		// add BOV instruction
		lines.add(TSTOLocation, new Line(new BOV(new Label("stack_overflow_error"))));
		
		//add TSTO instruction
		
		lines.add(TSTOLocation, new Line(new TSTO(new ImmediateInteger(counterMAx))));
	}

	
	public void addIOException() {
		Label iOErreurLabel = new Label("io_error");
		addLabel(iOErreurLabel);
		addInstruction(new WSTR(new ImmediateString("Error: Input/Output error")));
		addInstruction(new WNL());
		addInstruction(new ERROR());
	}

	public void addArithFloatException() {
		Label iOErreurLabel = new Label("ArithFloat_Error");
		addLabel(iOErreurLabel);
		addInstruction(new WSTR(new ImmediateString("Error: Débordement arithmétique sur flottants")));
		addInstruction(new WNL());
		addInstruction(new ERROR());
	}

	public void addZeroDivision() {
		// TODO Auto-generated method stub
		Label iOErreurLabel = new Label("ZeroDivision_Error");
		addLabel(iOErreurLabel);
		addInstruction(new WSTR(new ImmediateString("Error: Division entière par 0")));
		addInstruction(new WNL());
		addInstruction(new ERROR());
	}
}
