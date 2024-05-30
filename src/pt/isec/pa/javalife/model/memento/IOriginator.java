package pt.isec.pa.javalife.model.memento;

public interface IOriginator {
    IMemento save();
    void restore();
}