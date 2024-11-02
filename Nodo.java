
/**
 * Write a description of class Nodo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Nodo
{
    private int tipo;
    private double dval;
    private boolean bval;
    private TipoOperacao op;
    private Nodo subE, subD;
    
    public Nodo(TipoOperacao op, Nodo se, Nodo sd) {
        tipo = TipoNodo.NaoTerminal;
        this.op = op;
        subE = se;
        subD = sd;
    }

    public Nodo(double valor) {
        tipo = TipoNodo.Terminal;
        dval = valor;
        subE = null;
        subD = null;
    }
    
    public double avalia() {
    
        double result;
        
        if (tipo == TipoNodo.Terminal)
            result = dval;
        else {
            switch (op) {
                case ADD:
                    result = subE.avalia() + subD.avalia();
                    break;
                 case MULL:
                    result = subE.avalia() * subD.avalia();
                    break;
                 default:
                    result = 0;
                }
            }
        
            return result;
                       
    }
    
    public String toString() {
        String result;
        
        if (tipo == TipoNodo.Terminal)
            result = Double.toString(dval);
        else {
            switch (op) {
                case ADD:
                    result = "(" + subE + " + " + subD+")";
                    break;
                 case MULL:
                    result = "(" + subE + " * " + subD +")";                    break;
                 default:
                    result = "?";
                }
            }
        
            return result;
        }       

}
