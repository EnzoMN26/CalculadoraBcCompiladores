
%{
  import java.io.*;
  import java.util.HashMap;
  import java.util.Stack;
%}
      
%token NL          /* newline  */
%token <dval> NUM  /* a number */
%token IF, WHILE, ELSE, PRINT, FOR, DEFINE, RETURN, MaiorIgual, MenorIgual, IGUAL, DIFERENTE, AND, OR, MaisIgual, MultIgual
%token <sval> IDENT, SHOW, SHOWALL, HELP

%type <obj> exp, cmd, line, input, lcmd, lparams, lpassparams, param, passparam, comando

%nonassoc PRINT
%nonassoc MaisIgual
%nonassoc MultIgual
%nonassoc AND
%nonassoc OR
%nonassoc '!'
%nonassoc '='
%nonassoc MaiorIgual
%nonassoc MenorIgual
%nonassoc IGUAL
%nonassoc DIFERENTE
%nonassoc '>'
%nonassoc '<'
%left '-' '+'
%left '*', '/'
%left NEG          /* negation--unary minus */
%right '^'         /* exponentiation        */
      
%%

input:   /* empty string */ {$$=null;}
       | input line  { if ($2 != null) {
                           System.out.print("Avaliacao: " + ((INodo) $2).avalia() +"\n> "); 
							$$=$2;
						}
					    else {
                          System.out.print("\n> "); 
						  $$=null;
						}
					}
       ;
      
line:    NL      { if (interactive) System.out.print("\n> "); $$ = null; }
       | exp NL  { $$ = $1; 
                   if (interactive) System.out.print("\n>: "); }
       | cmd NL

cmd :  exp ';'            { $$ = $1; }
    |  IF '(' exp ')' cmd           { $$ = new NodoNT(TipoOperacao.IF,(INodo)$3, (INodo)$5, null); }
    |  IF '(' exp ')' cmd ELSE cmd  { $$ = new NodoNT(TipoOperacao.IFELSE,(INodo)$3, (INodo)$5, (INodo)$7); }
    |  WHILE '(' exp ')' cmd       { $$ = new NodoNT(TipoOperacao.WHILE,(INodo)$3, (INodo)$5, null); }
    |  FOR '(' exp ';' exp ';' exp ')' cmd {$$ = new NodoNT(TipoOperacao.FOR,(INodo)$3, (INodo)$5, (INodo)$7, (INodo)$9);}
    |  RETURN exp ';' {$$ = new NodoNT(TipoOperacao.RETURN, (INodo)$2);}
    |  DEFINE IDENT '(' lparams ')' cmd {$$ = new NodoNT(TipoOperacao.FUNCDEF, $2, (INodo)$4, (INodo)$6);}
    |  PRINT exp ';' {$$ = new NodoNT(TipoOperacao.PRINT, (INodo)$2);}
    | '{' lcmd '}'                 { $$ = $2; }
    | '#' comando { $$ = $2; }
    | error ';'                    { $$ = new NodoNT(TipoOperacao.NULL, "", null, null); }
    ;


comando : SHOW IDENT {printTableByIdent($2); $$ = new NodoID($1);}
        | SHOWALL {printTable();  $$ = new NodoID($1);}
        | HELP {System.out.println("return e print precisam possuir ';' ao final"); $$ = new NodoID($1);}
        ;

lparams : lparams ',' param  {$$ = new NodoNT(TipoOperacao.PARAMS, (INodo)$1, (INodo)$3);}
        | IDENT              {$$ = new NodoParam($1);}
        |                    {$$ = new NodoParam();}
      ;
    
param: IDENT {$$ = new NodoParam($1);}
      ;

lpassparams : lpassparams ',' passparam  {$$ = new NodoNT(TipoOperacao.PARAMS, (INodo)$1, (INodo)$3);}
        | exp              {$$ = new NodoParam((INodo)$1);}
        |                  {$$ = new NodoParam();}
      ;  

passparam : exp {$$ = new NodoParam((INodo)$1);}
  	      ;
      
lcmd : lcmd cmd                 { $$ = new NodoNT(TipoOperacao.SEQ,(INodo)$1,(INodo)$2); }
     |                          { $$ = new NodoNT(TipoOperacao.NULL, "", null, null); }               
     ;


exp:     NUM                { $$ = new NodoTDouble($1); }
       | IDENT '=' exp  { $$ = new NodoNT(TipoOperacao.ATRIB, $1, (INodo)$3); }
       | IDENT MaisIgual exp  { $$ = new NodoNT(TipoOperacao.MAISIGUAL, $1, (INodo)$3); }
       | IDENT MultIgual exp  { $$ = new NodoNT(TipoOperacao.MULTIGUAL, $1, (INodo)$3); }
       | IDENT              { $$ = new NodoID($1);}
       | exp '+' exp        { $$ = new NodoNT(TipoOperacao.ADD,(INodo)$1,(INodo)$3); }
       | exp '-' exp        { $$ = new NodoNT(TipoOperacao.SUB,(INodo)$1,(INodo)$3); }
       | exp '*' exp        { $$ = new NodoNT(TipoOperacao.MULL,(INodo)$1,(INodo)$3); }
       | exp '/' exp        { $$ = new NodoNT(TipoOperacao.DIV,(INodo)$1,(INodo)$3); }
       | exp '<' exp        { $$ = new NodoNT(TipoOperacao.LESS,(INodo)$1,(INodo)$3); }
       | exp '>' exp        { $$ = new NodoNT(TipoOperacao.MAIOR,(INodo)$1,(INodo)$3); }
       | exp MaiorIgual exp { $$ = new NodoNT(TipoOperacao.MAIORIGUAL,(INodo)$1,(INodo)$3); }
       | exp MenorIgual exp { $$ = new NodoNT(TipoOperacao.MENORIGUAL,(INodo)$1,(INodo)$3); }
       | exp IGUAL exp      { $$ = new NodoNT(TipoOperacao.IGUAL,(INodo)$1,(INodo)$3); }
       | exp DIFERENTE exp  { $$ = new NodoNT(TipoOperacao.DIFERENTE,(INodo)$1,(INodo)$3); }
       | exp AND exp        { $$ = new NodoNT(TipoOperacao.AND,(INodo)$1,(INodo)$3); }
       | exp OR exp         { $$ = new NodoNT(TipoOperacao.OR,(INodo)$1,(INodo)$3); }
       | IDENT '(' lpassparams ')'  {$$ = new NodoNT(TipoOperacao.FUNCCALL, $1, (INodo)$3);}
       | '!' exp            { $$ = new NodoNT(TipoOperacao.NEGACAO,(INodo)$2); }
       | '-' exp  %prec NEG { $$ = new NodoNT(TipoOperacao.UMINUS,(INodo)$2,null); }
       | exp '^' exp        { $$ = new NodoNT(TipoOperacao.POW,(INodo)$1,(INodo)$3); }
       | '(' exp ')'        { $$ = $2; }
       ;

%%

  public static HashMap<String, ResultValue> memory = new HashMap<>();
  public static HashMap<String, FuncClass> funcMemory = new HashMap<>();
  public static Stack<HashMap<String, ResultValue>> stackContext = new Stack<>();
  public static boolean returnFlag = false;
  private Yylex lexer;


  private int yylex () {
    int yyl_return = -1;
    try {
      yylval = new ParserVal(0);
      yyl_return = lexer.yylex();
    }
    catch (IOException e) {
      System.err.println("IO error :"+e);
    }
    return yyl_return;
  }


  public void yyerror (String error) {
    System.err.println ("Error: " + error);
  }


  public Parser(Reader r) {
    lexer = new Yylex(r, this);
  }


  static boolean interactive;

  public static void main(String args[]) throws IOException {
    System.out.println("BYACC/Java with JFlex Calculator Demo");

    Parser yyparser;
    if ( args.length > 0 ) {
      // parse a file
      yyparser = new Parser(new FileReader(args[0]));
    }
    else {
      // interactive mode
      System.out.println("[Quit with CTRL-D]");
      System.out.print("Expression: ");
      interactive = true;
	    yyparser = new Parser(new InputStreamReader(System.in));
    }

    stackContext.push(memory);

    yyparser.yyparse();
    
    if (interactive) {
      System.out.println();
      System.out.println("Have a nice day");
    }
  }

  public static void printTable(){
    HashMap<String, ResultValue> pilha = Parser.stackContext.peek();
    for (HashMap.Entry<String, ResultValue> linha : pilha.entrySet()) {
      String chave = linha.getKey();
      ResultValue valor = linha.getValue();
      System.out.println(chave + " -> " + valor.toString());
    }
  }

  public static void printTableByIdent(String ident){
    HashMap<String, ResultValue> pilha = Parser.stackContext.peek();
    if(pilha.containsKey(ident)){
      System.out.println(ident + " -> " + pilha.get(ident));
    }
    else{
      System.out.println("Nenhum valor encontrado que corresponda ao identificador informado.");
    }
  }
