package pt.isec.pa.javalife.model.command.Commands;

public interface ICommand {

    boolean execute();

    boolean undo();

    boolean isSuccessful();
}
