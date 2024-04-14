package pt.isec.pa.javalife.model.command;

import pt.isec.pa.javalife.model.command.Commands.ICommand;

import java.util.Stack;

/*ao fazer esta implementação considerei que os comandos retornam true se forem undoable, se não gostarem dá sempre para
fazer uma interface "IUndoable" e dizer que certos comandos a implementam, e depois perguntar se implementa undoable
está da primeira maneira pq foi como fizemos na aula*/
public class CommandManager{
    private Stack<ICommand> undos;
    private Stack<ICommand> redos;

    CommandManager(){
        undos=new Stack<>();
        redos=new Stack<>();
    }

    public boolean invokeCommand(ICommand cmd) {
        boolean undoable= cmd.execute();
        if(undoable && cmd.isSuccessful()){
            undos.push(cmd);
        }
        else{
            if(!undoable)
                undos.clear();
        }
        redos.clear();
        return cmd.isSuccessful();
    }
    public boolean undo() {
        if(hasUndo()){
            ICommand ic = undos.pop();
            if (ic.undo()) {
                redos.push(ic);
                return true;
            }
            return false;
        }
        return false;
    }


    public boolean redo() {
        if(hasRedo()){
            ICommand ic = redos.pop();
            if (ic.execute()) {
                undos.push(ic);
                return true;
            }
            return false;
        }
        return false;
    }

    //pode servir para mudar a cor do icon de undo
    public boolean hasUndo() {
        return !undos.isEmpty();
    }

    //pode servir para mudar a cor do icon de redo
    public boolean hasRedo() {
        return !redos.isEmpty();
    }
}