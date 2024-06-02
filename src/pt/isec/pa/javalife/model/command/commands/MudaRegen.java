package pt.isec.pa.javalife.model.command.commands;

import pt.isec.pa.javalife.model.data.SimulacaoManager;

public class MudaRegen extends AbstractCommand {

    double valor_novo, valor_velho;

    public MudaRegen(SimulacaoManager receiver, double valor_novo_){
        super(receiver);
        valor_novo = valor_novo_;
    }

    @Override
    public boolean execute() {
        valor_velho = receiver.getRegenFlora();
        success = receiver.setRegenFlora(valor_novo);
        return true;
    }

    @Override
    public boolean undo() {
        return receiver.setRegenFlora(valor_velho);
    }

    @Override
    public boolean isSuccessful() {
        return success;
    }

}
