lexer grammar DecaLexer;

options {
   language=Java;
   // Tell ANTLR to make the generated lexer class extend the
   // the named class, which is where any supporting code and
   // variables will be placed.
   superClass = AbstractDecaLexer;
}

@members {
}
// Deca lexer rules.
EOL: '\n' -> skip;
PRINTLNX: 'printlnx';
PRINTLN: 'println';
PRINTX: 'printx';
PRINT: 'print';
IF: 'if';
ELSE: 'else';
TRUE: 'true';
FALSE: 'false';
THIS: 'this';
NULL: 'null';
CLASS: 'class';
EXTENDS: 'extends';
NEW: 'new';
READINT: 'readInt';
READFLOAT: 'readFloat';
INSTANCEOF: 'instanceof';
WHILE: 'while';
RETURN: 'return';
ASM: 'asm';
PROTECTED: 'protected';
OR: '||';
AND: '&&';
GEQ: '>=';
LEQ: '<=';
NEQ: '!=';
EQEQ: '==';
GT: '>';
LT: '<';
PLUS: '+';
MINUS: '-';
SEMI: ';';
COMMA: ',';
EQUALS: '=';
OPARENT: '(';
CPARENT: ')';
OBRACE: '{';
CBRACE: '}';
PERCENT: '%';
EXCLAM: '!';
DOT: '.';
TIMES: '*';
SLASH: '/';

fragment DIGIT: '0'..'9';
fragment POSITIVE_DIGIT: '1'..'9';
fragment LETTER: ('a'..'z'|'A'..'Z');

fragment NUM: DIGIT+;
fragment SIGN: '+' | '-' | ;
fragment EXP: ('E'|'e') SIGN NUM;
fragment DEC: NUM '.' NUM;
fragment FLOATDEC: (DEC | (DEC EXP)) ('F' | 'f' | );
fragment DIGITHEX: ('0'..'9' | 'A'..'F' | 'a'..'f');
fragment NUMHEX: DIGITHEX+;
fragment FLOATHEX: ('0x'|'0X') NUMHEX '.' NUMHEX ('P'|'p') SIGN NUM ('F'|'f'|);

fragment FILENAME: (LETTER | DIGIT | '.' | '-' | '_')+;
INCLUDE: '#include' (' ')* '"' FILENAME '"'{
    doInclude(getText());
};

fragment STRING_CAR: ~('"' | '\\' | '\n');
STRING: '"' (STRING_CAR | '\\"' | '\\\\')* '"';
MULTI_LINE_STRING: '"' (STRING_CAR | '\n' | '\\"' | '\\\\')* '"';

FLOAT: (FLOATDEC | FLOATHEX){

    String s = getText();
    float f = Float.parseFloat(s);
        
        if (Float.isInfinite(f)) {
             throw new InfiniteFloatException(s, this, this.getInputStream());
        }
        if (Float.isNaN(f)){
             throw new NaNFLoatException(s, this, this.getInputStream());
        }
        if (Float.compare(f,0.0f)==0){
            throw new NullRoundingException(s, this, this.getInputStream());
        }
        

    };
INT: '0' | (POSITIVE_DIGIT DIGIT*);
IDENT: (LETTER | '$' | '_') (LETTER | DIGIT | '$' | '_')*;
COMMENT: ('//' .*? '\n' | '/*' .*? '*/' ) -> skip;

WHITESPACE: (' ' | '\t' | '\r' | '\n') -> skip;

