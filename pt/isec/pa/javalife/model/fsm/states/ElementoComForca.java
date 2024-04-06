package pt.isec.pa.javalife.model.fsm.states;

public sealed interface IElementoComForca
        permits Fauna, Flora {
    double getForca();
    void setForca(double forca);
}

