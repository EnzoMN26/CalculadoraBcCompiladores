  //ARQUIVO MODIFICADO POR:
  //ENZO NOBRE - 21200756 - Enzo.Martins@edu.pucrs.br
  //LUCIANO SCHWALM - 20106983 - luciano.schwalm@edu.pucrs.br
  
import java.util.ArrayList;
import java.util.HashMap;

public class FuncClass {
    private String id;
    private ArrayList params;
    private INodo cmds;

    public FuncClass(String id, INodo params, INodo cmds){
        this.id = id;
        this.params = params.avalia().getArray();
        this.cmds = cmds;
    }

    public boolean verificaParametros(INodo valores){
        ArrayList parametrosValores = valores.avalia().getArray();
        Parser.stackContext.push(new HashMap<String, ResultValue>());
        if(params.size() == parametrosValores.size()){
            for(int i = 0; i<params.size();i++){
                Parser.stackContext.peek().put((String)params.get(i), new ResultValue((Double)parametrosValores.get(i))); 
            }
            return true;
        }
        return false;
    }

    public void executa(){
        cmds.avalia();
    }
}
