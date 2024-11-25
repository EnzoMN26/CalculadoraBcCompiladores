  //ARQUIVO MODIFICADO POR:
  //ENZO NOBRE - 21200756 - Enzo.Martins@edu.pucrs.br
  //LUCIANO SCHWALM - 20106983 - luciano.schwalm@edu.pucrs.br

import java.util.ArrayList;

/**
 * Write a description of class ResultValue here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ResultValue
{
    private TypeEnum type;
    private double dval;
    private boolean bval;
    private ArrayList listVal;
    
    public ResultValue(double val){
        type = TypeEnum.DOUBLE;
        dval = val;
    }

    public ResultValue(boolean val){
        type = TypeEnum.BOOLEAN;
        bval = val;
    }

    public ResultValue(ArrayList val){
        type = TypeEnum.ARRAY;
        listVal = val;
    }
    
    public double getDouble() {
        return dval;
    }
    
    public boolean getBool() {
        return bval;
    }

    public ArrayList getArray() {
        return listVal;
    }

    public String toString() {
        switch (type) {
            case DOUBLE:
                return Double.toString(dval);
            case BOOLEAN:
                return Boolean.toString(bval);
            case ARRAY:
                return listVal.toString();
            }
            
         return "erro! tipo nao tratado em ResultValue";
        }       
}
