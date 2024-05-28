package pt.isec.pa.javalife.model.command.commands;

import pt.isec.pa.javalife.model.data.SimulacaoManager;

public class MudaVelocidade extends AbstractCommand{
    private long valor_novo, valor_velho;
    private boolean success;

    MudaVelocidade(SimulacaoManager receiver, long valor_novo_){
        super(receiver);
        valor_novo = valor_novo_;
        success = false;
    }

    @Override
    public boolean execute() {
        valor_velho = receiver.getTempo();
        success = receiver.setTempo(valor_novo);
        return success;
    }

    @Override
    public boolean undo() {
        return receiver.setTempo(valor_velho);
    }

    @Override
    public boolean isSuccessful() {
        return success;
    }
}
