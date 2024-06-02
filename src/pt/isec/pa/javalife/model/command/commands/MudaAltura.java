package pt.isec.pa.javalife.model.command.commands;

import pt.isec.pa.javalife.model.data.SimulacaoManager;

public class MudaAltura extends AbstractCommand{

    double valor_novo, valor_velho;

    public MudaAltura(SimulacaoManager receiver, double valor_novo_){
        super(receiver);
        valor_novo = valor_novo_;
    }

    @Override
    public boolean execute() {
        valor_velho = receiver.getAlturaEcossistema();
        success = receiver.setAltura(valor_novo);
        return true;
    }

    @Override
    public boolean isSuccessful() {
        return success;
    }

    @Override
    public boolean undo() {
        return receiver.setAltura(valor_velho);
    }
}
