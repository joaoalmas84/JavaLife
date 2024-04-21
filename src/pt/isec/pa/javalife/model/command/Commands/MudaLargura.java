package pt.isec.pa.javalife.model.command.Commands;

import pt.isec.pa.javalife.model.data.Simulacao;

public class MudaLargura extends AbstractCommand{

    int valor_novo, valor_velho;

    MudaLargura(Simulacao receiver,int valor_novo_){
        super(receiver);
        valor_novo=valor_novo_;
    }

    @Override
    public boolean execute() {
        valor_velho = receiver.getLarguraEcossistema();
        success= receiver.setLargura(valor_novo);
        return true;
    }

    @Override
    public boolean isSuccessful() {
        return success;
    }

    @Override
    public boolean undo() {
        return receiver.setLargura(valor_velho);
    }
}