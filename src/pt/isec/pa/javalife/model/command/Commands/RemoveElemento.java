package pt.isec.pa.javalife.model.command.commands;

import pt.isec.pa.javalife.model.data.IElemento;

import pt.isec.pa.javalife.model.data.SimulacaoManager;

public class RemoveElemento extends AbstractCommand{
    private IElemento elem;

    int id;

    String tipo;

    public RemoveElemento(SimulacaoManager receiver, int id_, String tipo_){
        super(receiver);
        success=false;
        id=id_;
        tipo=tipo_;
    }

    @Override
    public boolean isSuccessful() {
        return success;
    }

    public boolean execute() {
        elem = receiver.removeElemento(id, tipo);
        success= (elem != null);
        return true;
    }

    public boolean undo(){
        return receiver.addElemento(elem);
    }

}
