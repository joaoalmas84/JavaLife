package pt.isec.pa.javalife.model.command.commands;

import java.io.Serializable;

public interface ICommand extends Serializable {
    boolean execute();
    boolean undo();

    boolean isSuccessful();
}
