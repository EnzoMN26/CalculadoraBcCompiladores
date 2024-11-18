import java.util.ArrayList;

public class NodoParam implements INodo{
    private ArrayList vals;
    private String valorStr = null;
    private INodo valorNodo = null;
    
    public NodoParam() {
        vals = new ArrayList<>();
    }

    public NodoParam(String valor) {
        valorStr = valor;
    }

    public NodoParam(INodo valor) {

        valorNodo = valor;

    }

    public ResultValue avalia() {
        vals = new ArrayList<>();
        if(valorStr != null){
            vals.add(valorStr);
        }
        else{
            vals.add(valorNodo.avalia().getDouble());
        }

        return new ResultValue(vals);          
    }
    
    public String toString() {
            return vals.toString();
        }       
}
