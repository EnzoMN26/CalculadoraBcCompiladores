
import java.util.ArrayList;
import java.util.HashMap;

public class NodoNT implements INodo
{
    private TipoOperacao op;
    private INodo subE, subD, cmdFor;
    private INodo expr;
    private String ident;

    public NodoNT(TipoOperacao op, INodo exp) {
        this.op = op;
        expr = exp;
    }

    public NodoNT(TipoOperacao op, INodo se, INodo sd) {
        this.op = op;
        subE = se;
        subD = sd;
    }

    public NodoNT(TipoOperacao op, String id, INodo se) {
        this.op = op;
        subE = se;
        ident = id;
    }

    public NodoNT(TipoOperacao op, String id, INodo se, INodo sd) {
        this.op = op;
        subE = se;
        subD = sd;
        ident = id;
    }

    public NodoNT(TipoOperacao op, INodo exp, INodo caseT, INodo caseF ) {
        this.op = op;
        subE = caseT;
        subD = caseF;
        expr = exp;
    }

    public NodoNT(TipoOperacao op, INodo exp, INodo caseT, INodo caseF, INodo cmdFor) {
        this.op = op;
        expr = exp;
        subE = caseT;
        subD = caseF;
        this.cmdFor = cmdFor;

    }
   
    public ResultValue avalia() {

        ResultValue result = null;
        ResultValue  left, right, expressao;

        if(Parser.returnFlag){
            return result;
        }

        if(op == TipoOperacao.FUNCDEF){
            FuncClass funcao = new FuncClass(ident, subE, subD);
            Parser.funcMemory.put(ident, funcao);
        }
        else if(op == TipoOperacao.FUNCCALL){
            FuncClass funcao = Parser.funcMemory.get(ident);
            Parser.stackContext.push(new HashMap<String, ResultValue>());
            if(funcao.verificaParametros(subE)){
                funcao.executa();
            }
            if(Parser.stackContext.peek().containsKey("return")){
                result = Parser.stackContext.peek().get("return");
            }
            Parser.stackContext.pop();
            Parser.returnFlag = false;
        }
        else if (op == TipoOperacao.RETURN){
            Parser.stackContext.peek().put("return", expr.avalia());
            Parser.returnFlag = true;
        }
        else if (op == TipoOperacao.NULL)
           return null; 

        else if (op == TipoOperacao.UMINUS) 
             result = new ResultValue(-1.0 * subE.avalia().getDouble()) ;

        else if (op == TipoOperacao.ATRIB) {
             result = subE.avalia();
             Parser.stackContext.peek().put(ident, result);    
             //System.out.printf("sube: %s, %s <- %f\n", subE, ident, result.getDouble());         
        }

       else if (op == TipoOperacao.IF) {
             expressao = expr.avalia();
             if (expressao.getBool())
                result = subE.avalia();
        }

        else if (op == TipoOperacao.IFELSE) {
             expressao = expr.avalia();
             if (expressao.getBool())
                result = subE.avalia();
             else 
                result = subD.avalia();
        }

        else if (op == TipoOperacao.FOR) {
            expr.avalia();
            while ( (subE.avalia()).getBool()) {
                cmdFor.avalia();
                subD.avalia();
            }
        }

        else if (op == TipoOperacao.WHILE) {
            while ( (expr.avalia()).getBool()) {
                   subE.avalia();
            }
        }
        else if (op == TipoOperacao.SEQ) {
            subE.avalia();
            subD.avalia();
            
        }
        else if (op == TipoOperacao.PARAMS) {
            ArrayList arrayAux = subE.avalia().getArray();
            arrayAux.addAll(subD.avalia().getArray());
            
            return new ResultValue(arrayAux);
        }
        else {        
            left = subE.avalia();
            right = subD.avalia();
          switch (op) {
            case ADD:
               result = new ResultValue((left.getDouble() + right.getDouble()));
               break;
            case SUB:
               result = new ResultValue(left.getDouble() - right.getDouble());
               break;
            case MULL:
               result = new ResultValue(left.getDouble() * right.getDouble());
               break;
            case DIV:
              result = new ResultValue(left.getDouble() / right.getDouble());
              break;
            case POW:
              result = new ResultValue(Math.pow(left.getDouble(),right.getDouble()));
              break;
            case LESS:
              result = new ResultValue(left.getDouble() < right.getDouble());
              break;                    
            default:
              result = new ResultValue(0);
            }
        }
        
        return result;               
    }
    
    public String toString() {
        String opBin, result;
        if (op == TipoOperacao.ATRIB) 
            result =  ident + "=" + subE  ;
        else if (op == TipoOperacao.IF) 
            result = "if (" + expr + ")" + subE + " else " + subD  ;
        else if (op == TipoOperacao.WHILE) 
            result = "while (" + subE + ")" + subD   ;
        else if (op == TipoOperacao.UMINUS) 
            result = "-" + subE  ;
        else {
            switch (op) {
           
                case ADD:
                    opBin = " + ";
                    break;
                 case SUB:
                    opBin = " - ";
                    break;
                 case MULL:
                    opBin  = " * ";
                    break;
                 case DIV:
                    opBin  = " / ";
                    break;
                 case POW:
                    opBin  = " ^ ";
                    break;

                 case LESS:
                    opBin = " < ";
                    break;

                 default:
                    opBin = " ? ";
                }
                result = "(" + subE + opBin + subD+")";
            }
                 return result;
        }       
  
}
