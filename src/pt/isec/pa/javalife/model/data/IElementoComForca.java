package pt.isec.pa.javalife.model.data;

public sealed interface IElementoComForca
        permits FaunaData, Flora {

    double getForca();
    void setForca(double forca);
}

