package pt.isec.pa.javalife.model.data;

public sealed interface IElementoComImagem
        permits Fauna, Flora {
    String getImagem(); // path da imagem
    void setImagem(String imagem);
}

