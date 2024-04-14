package pt.isec.pa.javalife.model.command.Commands;

import pt.isec.pa.javalife.model.Ecossistema;
import pt.isec.pa.javalife.model.data.IElemento;

public class AdicionaElemento extends AbstractCommand{

    private Ecossistema reciever;

    private IElemento elem;

    public AdicionaElemento(Ecossistema reciever, IElemento elem){
        this.reciever=reciever;
        this.elem=elem;
        success=false;
    }

    @Override
    public boolean execute() {
        //success = reciever.adicionaElemento(elem);
        return true;
    }

    public boolean undo(){
        //return reciever.removeElemento(elem);
    }


}
