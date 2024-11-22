import java.util.ArrayList;

public class NodoParam implements INodo{
    private ArrayList vals;
    private String valorStr = null;
    private double valorDouble = 0;
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
            valorDouble = valorNodo.avalia().getDouble();
            vals.add(valorDouble);
        }

        return new ResultValue(vals);          
    }
    
    public String toString() {
        if(valorStr != null){
            return valorStr;
        }
        else{
            return ""+valorDouble;
        }
    }       
}
