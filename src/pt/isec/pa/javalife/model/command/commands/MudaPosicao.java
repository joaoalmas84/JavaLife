package pt.isec.pa.javalife.model.command.commands;

import pt.isec.pa.javalife.model.data.Area;
import pt.isec.pa.javalife.model.data.ElementoBase;
import pt.isec.pa.javalife.model.data.SimulacaoManager;

public class MudaPosicao extends AbstractCommand{
    ElementoBase elementoBase;
    Area valor_novo, valor_velho;

    public MudaPosicao(SimulacaoManager receiver, ElementoBase elem, Area nv){
        super(receiver);
        valor_novo=nv;
        elementoBase=elem;
    }

    @Override
    public boolean execute() {
        valor_velho = elementoBase.getArea();
        success= receiver.setAreaElem(elementoBase,valor_novo);
        return true;
    }

    @Override
    public boolean isSuccessful() {
        return success;
    }

    @Override
    public boolean undo() {
        return receiver.setAreaElem(elementoBase, valor_velho);
    }
}
