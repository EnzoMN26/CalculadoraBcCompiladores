ENZO NOBRE - 21200756 - Enzo.Martins@edu.pucrs.br
LUCIANO SCHWALM - 20106983 - luciano.schwalm@edu.pucrs.br

para executar o programa siga os seguintes comandos:

java -jar JFlex.jar calc.flex

yacc -tv -J calc.y

javac Parser.java

java Parser


Observacoes: 
- Deve ser utilizado ';' depois de returns e prints.
- As funcoes sao declaradas de maneira in line.