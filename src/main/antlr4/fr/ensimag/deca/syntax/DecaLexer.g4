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
PRINTLN : 'println' ;
OBRACE :'{';
CBRACE : '}' ;
OPARENT : '(';
CPARENT : ')';
SEMI : ';';
fragment STRING_CAR: ~('"' | '\\' | '\n') ;
STRING : '"' (STRING_CAR | '\\"' | '\\\\')*  '"';
MULTI_LINE_STRING : '"' (STRING_CAR | '\n' | '\\"' | '\\\\')*  '"';
EOL: '\n' -> skip;
