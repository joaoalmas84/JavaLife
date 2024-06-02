package pt.isec.pa.javalife.model.command.commands;

import pt.isec.pa.javalife.model.data.Area;
import pt.isec.pa.javalife.model.data.ElementoBase;
import pt.isec.pa.javalife.model.data.SimulacaoManager;

public class MudaAlturaElem extends AbstractCommand {


    ElementoBase elementoBase;
    double valor_novo, valor_velho;

    public MudaAlturaElem(SimulacaoManager receiver, ElementoBase elem, double nv){
        super(receiver);
        valor_novo=nv;
        elementoBase=elem;
    }

    @Override
    public boolean execute() {
        valor_velho = elementoBase.getArea().height();
        Area areaNova= new Area(elementoBase.getArea().xi(),elementoBase.getArea().yi(), elementoBase.getArea().xf(),elementoBase.getArea().yi()+valor_novo);
        success= receiver.setAreaElem(elementoBase,areaNova);
        return true;
    }

    @Override
    public boolean isSuccessful() {
        return success;
    }

    @Override
    public boolean undo() {
        Area areaNova= new Area(elementoBase.getArea().xi(),elementoBase.getArea().yi(), elementoBase.getArea().xf(),elementoBase.getArea().yi()+valor_velho);
        return receiver.setAreaElem(elementoBase, areaNova);
    }
}
