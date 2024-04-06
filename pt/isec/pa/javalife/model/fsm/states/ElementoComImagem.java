package pt.isec.pa.javalife.model.fsm.states;

public sealed interface IElementoComImagem
        permits Flora {
    String getImagem(); // path da imagem
    void setImagem(String imagem);
}