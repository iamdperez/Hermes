package parser;

import java_cup.runtime.Symbol;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;

%%
%public
%class Lexer

%char
%line
%column

%cup


%{

    StringBuffer string = new StringBuffer();
        public Lexer(java.io.Reader in, ComplexSymbolFactory sf){
    	this(in);
    	symbolFactory = sf;
        }
        ComplexSymbolFactory symbolFactory;

      private Symbol symbol(String name, int sym) {
          return symbolFactory.newSymbol(name, sym, new Location(yyline+1,yycolumn+1,yyline+1),
          new Location(yyline+1,yycolumn+1,yycolumn+1));
      }

      private Symbol symbol(String name, int sym, Object val) {
          Location left = new Location(yyline+1,yycolumn+1,yyline+1);
          Location right= new Location(yyline+1,yycolumn+1, yycolumn+1);
          return symbolFactory.newSymbol(name, sym, left, right,val);
      }

      private void error(String message) {
        System.out.println("Error at line "+(yyline+1)+", column "+(yycolumn+1)+" : "+message);
      }
%}

%eofval{
     return symbolFactory.newSymbol("EOF", sym.EOF, new Location(yyline+1,yycolumn+1,yychar), new Location(yyline+1,yycolumn+1,yychar+1));
%eofval}

LineTerminator          =       \r|\n|\r\n
InputCharacter          =       [^\r\n]

WhiteSpace              =       {LineTerminator} | [ \t\f]

/* comments */
Comment                 =       {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}
TraditionalComment      =       "/*" [^*] ~"*/" | "/*" "*"+ "/"
EndOfLineComment        =       "//" {InputCharacter}* {LineTerminator}?
DocumentationComment    =       "/**" {CommentContent} "*"+ "/"
CommentContent          =       ( [^*] | \*+ [^/*] )*

/* literals */
int_lit                 =       0 | [1-9][0-9]*
id                      =       [A-Za-z_][A-Za-z_0-9]*

/*%state STRING*/
device                  =       \'[A-Za-z][A-Za-z_0-9]*\'
%%

/*keywords*//*
<YYINITIAL> "module"        { return symbol(sym.MODULE); }
<YYINITIAL> "endmodule"     { return symbol(sym.MODULE_END); }
<YYINITIAL> "main"          { return symbol(sym.MAIN); }
<YYINITIAL> "endmain"       { return symbol(sym.MAIN_END); }
<YYINITIAL> "if"            { return symbol(sym.I); }
<YYINITIAL> "endif"         { return symbol(sym.IF_END); }
<YYINITIAL> "while"         { return symbol(sym.WHILE); }
<YYINITIAL> "endwhile"      { return symbol(sym.WHILE_END); }
<YYINITIAL> "for"           { return symbol(sym.FOR); }
<YYINITIAL> "endfor"        { return symbol(sym.FOR_END); }
<YYINITIAL> "function"      { return symbol(sym.FUNCTION); }
<YYINITIAL> "endfunction"   { return symbol(sym.FUNCTION_END); }
<YYINITIAL> "print"         { return symbol(sym.PRINT); }
<YYINITIAL> "var"           { return symbol(sym.VAR); }
<YYINITIAL> "pin"           { return symbol(sym.PIN); }
<YYINITIAL> "device"        { return symbol(sym.DEVICE); }
<YYINITIAL> "istype"        { return symbol(sym.ISTYPE); }
<YYINITIAL> "input"         { return symbol(sym.INPUT); }
<YYINITIAL> "output"        { return symbol(sym.OUTPUT); }*/


<YYINITIAL> {
    /* id */
    {id}            {   return symbol("identifier",sym.ID, yytext());   }

    /* literals */
    {int_lit}       {   return symbol("int literal",sym.INT_LITERAL, new Integer(Integer.parseInt(yytext())));   }
    
    {device}        {   return symbol("device model",sym.DEVICE_MODEL, yytext());}

    /* operators */
    ","             {   return symbol("comma", sym.COMMA);  }
    "?"             {   return symbol("ternary operator", sym.TERNARY_OP);  }
    ":"             {   return symbol("colon", sym.COLON);  }
    "||"            {   return symbol("or operator", sym.OR_OP);    }
    "&&"            {   return symbol("and operator", sym.AND_OP);  }
    "|"             {   return symbol("or",sym.OR); }
    "^"             {   return symbol("xor",sym.XOR);   }
    "&"             {   return symbol("and",sym.AND);   }
    "=="            {   return symbol("equal operator",sym.EQUAL_OP);   }
    "!="            {   return symbol("not equal operator",sym.NOT_EQUAL_OP);   }
    "<<"            {   return symbol("left shift operator",sym.LSHIFT_OP); }
    ">>"            {   return symbol("right shift operator",sym.RSHIFT_OP);   }
    "<="            {   return symbol("less equal operator",sym.LESS_EQUAL_OP); }
    ">="            {   return symbol("greater equal operator",sym.GREATER_EQUAL_OP);   }
    "<"             {   return symbol("less operator",sym.LESS_OP); }
    ">"             {   return symbol("greater operator",sym.GREATER_OP);   }    
    "++"            {   return symbol("increment operator",sym.INC_OP); }    
    "--"            {   return symbol("decrement operator",sym.DEC_OP); }    
    "+"             {   return symbol("plus",sym.PLUS); }    
    "-"             {   return symbol("minus",sym.MINUS);   }    
    "*"             {   return symbol("times",sym.TIMES);   }    
    "/"             {   return symbol("div",sym.DIV);   }    
    "%"             {   return symbol("mod",sym.MOD);   }    
    "]"             {   return symbol("right square bracket",sym.RSQUARE_BRACKET); }    
    "["             {   return symbol("left square bracket",sym.LSQUARE_BRACKET);   }
    "~"             {   return symbol("not",sym.NOT);   }
    "!"             {   return symbol("not operator",sym.NOT_OP);   }
    "("             {   return symbol("left parenthesis",sym.LPARENTHESIS); }
    ")"             {   return symbol("right parenthesis",sym.RPARENTHESIS);   }
    "="             {   return symbol("assignment operator",sym.ASSIGNMENT_OP); }
    ";"             {   return symbol("semicolon",sym.SEMICOLON); }

    {Comment}       {   /* ignore */    }
    {WhiteSpace}    {   /* ignore */    }
}

/*string literal*//*
<STRING> {
    \"                             { yybegin(YYINITIAL); 
                                    return symbol(sym.STRING_LITERAL, string.toString()); }
    [^\n\r\"\\]+                   { string.append( yytext() ); }
    \\t                            { string.append('\t'); }
    \\n                            { string.append('\n'); }

    \\r                            { string.append('\r'); }
    \\\"                           { string.append('\"'); }
    \\                             { string.append('\\'); }
}*/


[^]                    { throw new Error("Illegal character <"+yytext()+">"); }