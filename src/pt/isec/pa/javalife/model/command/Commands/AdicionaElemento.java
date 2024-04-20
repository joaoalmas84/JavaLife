package pt.isec.pa.javalife.model.command.Commands;

import pt.isec.pa.javalife.model.data.IElemento;
import pt.isec.pa.javalife.model.data.Simulacao;

public class AdicionaElemento extends AbstractCommand{

    private IElemento elem;

    public AdicionaElemento(Simulacao receiver, IElemento elem_){
        super(receiver);
        elem=elem_;
        success=false;
    }

    @Override
    public boolean isSuccessful() {
        return success;
    }

    public boolean execute() {
        success = receiver.addElemento(elem);
        return true;
    }

    public boolean undo(){
        return receiver.removeElemento(elem) != null;
    }


}
