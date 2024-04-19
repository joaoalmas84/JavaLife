package pt.isec.pa.javalife.model.command.Commands;

import pt.isec.pa.javalife.model.Ecossistema;
import pt.isec.pa.javalife.model.data.IElemento;

public class AdicionaElemento extends AbstractCommand{

    private IElemento elem;

    public AdicionaElemento(Simulacao reciever, IElemento elem_){
        super(reciever);
        elem=elem_;
        success=false;
    }

    @Override
    public boolean execute() {
        //success = reciever.adicionaElemento(elem);
        return true;
    }

    public boolean undo(){
        //...
        //return reciever.removeElemento(elem);
    }


}
