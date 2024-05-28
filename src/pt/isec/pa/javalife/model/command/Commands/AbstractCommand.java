package pt.isec.pa.javalife.model.command.commands;

import pt.isec.pa.javalife.model.data.SimulacaoManager;

abstract public class AbstractCommand implements ICommand{

    protected boolean success;

    protected SimulacaoManager receiver;

    protected AbstractCommand(SimulacaoManager receiver_){
        receiver=receiver_;
    }

    @Override
    public boolean isSuccessful() {
        return success;
    }

}
