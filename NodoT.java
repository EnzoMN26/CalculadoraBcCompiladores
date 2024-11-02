
/**
 * Write a description of class Nodo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class NodoT implements INodo
{
private int tipo;
    private ResultValue dval;
    

    public NodoT(double valor) {
        dval = new ResultValue(valor);
    }
    
    public ResultValue avalia() {
         return dval;              
    }
    
    public String toString() {
            return dval.toString();
        }       
 }
    