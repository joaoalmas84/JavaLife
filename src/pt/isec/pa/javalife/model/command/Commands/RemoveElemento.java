package pt.isec.pa.javalife.model.command.Commands;

import pt.isec.pa.javalife.model.data.IElemento;

public class RemoveElemento extends AbstractCommand{
    private IElemento elem;

    int id;

    String tipo;

    public RemoveElemento(Simulacao reciever, int id_, String tipo_){
        super(reciever);
        success=false;
        id=id_;
        tipo=tipo_;
    }

    @Override
    public boolean isSuccessful() {
        return success;
    }

    public boolean execute() {
        success=receiver.removeElemento(id, tipo);
        return true;
    }

    public boolean undo(){
        return receiver.adicionaElemento(elem);
    }


    @Override
}
