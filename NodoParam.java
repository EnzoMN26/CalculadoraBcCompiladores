import java.util.ArrayList;

public class NodoParam implements INodo{
    private ArrayList vals;
    

    public NodoParam(String valor) {
        vals = new ArrayList<>();
        vals.add(valor); 
    }

    public NodoParam(double valor) {
        vals = new ArrayList<>();
        vals.add(valor); 
    }

    public ResultValue avalia() {
        return new ResultValue(vals);          
    }
    
    public String toString() {
            return vals.toString();
        }       
}
