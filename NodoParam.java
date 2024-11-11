import java.util.ArrayList;

public class NodoParam implements INodo{
    private ArrayList vals;
    
    public NodoParam() {
        vals = new ArrayList<>();
    }

    public NodoParam(String valor) {
        vals = new ArrayList<>();
        vals.add(valor); 
    }

    public NodoParam(INodo valor) {
        vals = new ArrayList<>();
        vals.add(valor.avalia().getDouble()); 
    }

    public ResultValue avalia() {
        return new ResultValue(vals);          
    }
    
    public String toString() {
            return vals.toString();
        }       
}
