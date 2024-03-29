package fr.ensimag.deca.syntax;

import org.antlr.v4.runtime.IntStream;

/**
 * Exception raised when a #include is found for a file that cannot be found or opened.
 *
 * @author gl16
 * @date 01/01/2021
 */
public class NullRoundingException extends DecaRecognitionException {
    private final String name;

    public NullRoundingException(String name, AbstractDecaLexer recognizer, IntStream input) {
        super(recognizer, input);
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    @Override
    public String getMessage() {
        return name + ": Null rounding error";
    }
    
    private static final long serialVersionUID = -8541996188279897766L;

}
