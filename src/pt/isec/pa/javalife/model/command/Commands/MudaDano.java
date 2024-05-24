package pt.isec.pa.javalife.model.command.Commands;

import pt.isec.pa.javalife.model.data.Simulacao;

public class MudaDano extends AbstractCommand{

    double valor_novo, valor_velho;

    public MudaDano(Simulacao receiver, double valor_novo_){
        super(receiver);
        valor_novo=valor_novo_;
    }

    @Override
    public boolean execute() {
        valor_velho = receiver.getDano();
        success= receiver.setDano(valor_novo);
        return true;
    }

    @Override
    public boolean undo() {
        return receiver.setDano(valor_velho);
    }

    @Override
    public boolean isSuccessful() {
        return success;
    }

}
