import java.util.ArrayList;

public class FuncClass {
    private String id;
    private INodo params;
    private INodo cmds;

    public FuncClass(String id, INodo params, INodo cmds){
        this.id = id;
        this.params = params;
        this.cmds = cmds;
    }

    public boolean verificaParametros(INodo valores){
        ArrayList parametros = params.avalia().getArray();
        ArrayList parametrosValores = valores.avalia().getArray();
        if(parametros.size() == parametrosValores.size()){
            for(int i = 0; i<parametros.size();i++){
                Parser.queueContext.peek().put((String)parametros.get(i), new ResultValue((Double)parametrosValores.get(i))); 
            }
            return true;
        }
        return false;
    }

    public void executa(){
        cmds.avalia();
    }
}
