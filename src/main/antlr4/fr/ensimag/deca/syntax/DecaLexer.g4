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
EOL: '\n' { skip(); };

ASM: 'asm';
CLASS: 'class';
EXTENDS: 'extends';
ELSE: 'else';
FALSE: 'false';
IF: 'if';
INSTANCEOF: 'instanceof';
NEW:'new';
NULL: 'null';
READINT:'readFloat';
PRINT:'print';
PRINTLN:'println';
PRINTLNX: 'printlnx';
PRINTX:'printx';
PROTECTED:'protected';
RETURN: 'return';
THIS: 'this';
TRUE: 'true';
WHILE: 'while';
INFERIOR: '<';
SUPERIOR: '>';
EQUALS:'=';
PLUS:'+';
MINUS:'-';
TIMES: '*';
SLASH: '/';
PERCENTAGE:'%';
POINT: '.';
COMMA: ',';
OPARENT:'(';
CPARENT:')';
OBRACE:'{';
CBRACE:'}';
NOT:'!';
SEMI: ';';
DOUBLEEQUALS:'==';
DIFFERENT:'!=';
SUPOREQUAL:'>=';
INFOREQUAL:'<=';
AND:'&&';
OR:'||';



WS  :   ( ' '
        | '\t'
        | '\r'
        ) {
              skip(); // avoid producing a token
          }
    ;
//Comments
CLASSIC_COMMENT : '/*' .*? '*/'{ skip(); } ;
ONE_LINE_COMMENT : '//' (~('\n'))* (EOL|EOF){ skip(); };

fragment LETTER : 'a'..'z' | 'A'..'Z';
fragment DIGIT : '0'..'9';
IDENT : (LETTER|'$'|'_')(LETTER|DIGIT|'$'|'_')*;
//Integer literals
fragment POSITIVE_DIGIT : '1'..'9';
INT: '0'| POSITIVE_DIGIT+;
//FLoat literals
NUM : DIGIT+;
fragment SIGN: '+'|'-';
fragment EXP: ('E'|'e') SIGN NUM;
fragment DEC : NUM '.' NUM ;
fragment FLOATDEC : (DEC|DEC EXP) ('F'|'f'|);
fragment DIGITHEX: '0'.. '9'|'A'..'F'|'a'..'f';
fragment NUMHEX : DIGITHEX+;
fragment FLOATHEX: ('0x' |'0X') NUMHEX'.' NUMHEX('P'|'p')SIGN NUM('F'|'f'|);
FLOAT : FLOATDEC | FLOATHEX;

//Strings

STRING_CAR: ~('"'|'\\'|'\n');
STRING: '"' (STRING_CAR |'\\"'|'\\\\')* '"';
MULTI_LINE_STRING: '"' (STRING_CAR |EOL|'\\"'|'\\\\')* '"';


// Ignore spaces, tabs, newlines and whitespaces

//Include
fragment FILENAME : (LETTER|DIGIT|'.'|'+'|'-'|'_')+;
INCLUDE: '#include' ()* '"' FILENAME '"';
