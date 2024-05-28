package pt.isec.pa.javalife.model.command.commands;

import pt.isec.pa.javalife.model.data.SimulacaoManager;

public class MudaDano extends AbstractCommand {

    double valor_novo, valor_velho;

    public MudaDano(SimulacaoManager receiver, double valor_novo_){
        super(receiver);
        valor_novo = valor_novo_;
    }

    @Override
    public boolean execute() {
        valor_velho = receiver.getDanoFauna();
        success = receiver.setDanoFauna(valor_novo);
        return true;
    }

    @Override
    public boolean undo() {
        return receiver.setDanoFauna(valor_velho);
    }

    @Override
    public boolean isSuccessful() {
        return success;
    }

}
