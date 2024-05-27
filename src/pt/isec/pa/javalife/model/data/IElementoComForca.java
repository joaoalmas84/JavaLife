package pt.isec.pa.javalife.model.data;

public sealed interface IElementoComForca
        permits Fauna, Flora {

    double getForca();
    void addForca(double forca);
}

