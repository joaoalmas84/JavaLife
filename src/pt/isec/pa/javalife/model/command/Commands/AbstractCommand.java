package pt.isec.pa.javalife.model.command.Commands;

abstract public class AbstractCommand implements ICommand{

    protected boolean success;

    @Override
    public boolean isSuccessful() {
        return success;
    }

}
