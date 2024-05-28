package pt.isec.pa.javalife.model.command.commands;

public interface ICommand {
    boolean execute();
    boolean undo();

    boolean isSuccessful();
}
