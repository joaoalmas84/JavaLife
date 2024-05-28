package pt.isec.pa.javalife.model.command.commands;

import pt.isec.pa.javalife.model.data.SimulacaoManager;

public class MudaLargura extends AbstractCommand {

    double valor_novo, valor_velho;

    public MudaLargura(SimulacaoManager receiver, double valor_novo_){
        super(receiver);
        valor_novo=valor_novo_;
    }

    @Override
    public boolean execute() {
        valor_velho = receiver.getLarguraEcossistema();
        success= receiver.setLargura(valor_novo);
        return true;
    }

    @Override
    public boolean isSuccessful() {
        return success;
    }

    @Override
    public boolean undo() {
        return receiver.setLargura(valor_velho);
    }
}
