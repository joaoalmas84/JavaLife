package pt.isec.pa.javalife.model.command.Commands;

import pt.isec.pa.javalife.model.data.Simulacao;

abstract public class AbstractCommand implements ICommand{

    protected boolean success;

    protected Simulacao receiver;

    protected AbstractCommand(Simulacao receiver_){
        receiver=receiver_;
    }

    @Override
    public boolean isSuccessful() {
        return success;
    }

}
