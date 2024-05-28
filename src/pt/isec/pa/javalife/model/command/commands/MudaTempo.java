package pt.isec.pa.javalife.model.command.commands;

import pt.isec.pa.javalife.model.data.SimulacaoManager;

public class MudaTempo extends AbstractCommand {

    long tempo_novo, tempo_velho;

    public MudaTempo(SimulacaoManager receiver, long tempo_novo_) {
        super(receiver);
        tempo_novo = tempo_novo_;
    }

    @Override
    public boolean execute() {
        tempo_velho = receiver.getTempoDeInstante();
        success = receiver.setTempo(tempo_novo);
        return true;
    }

    @Override
    public boolean undo() { return receiver.setTempo(tempo_velho); }
}
