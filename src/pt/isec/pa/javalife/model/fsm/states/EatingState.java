package pt.isec.pa.javalife.model.fsm.states;

import pt.isec.pa.javalife.model.data.Area;
import pt.isec.pa.javalife.model.data.FaunaData;
import pt.isec.pa.javalife.model.data.Fauna;
import pt.isec.pa.javalife.model.data.IElemento;
import pt.isec.pa.javalife.model.fsm.FaunaState;
import pt.isec.pa.javalife.model.fsm.FaunaStateAdapter;

import java.sql.SQLOutput;
import java.util.Set;

public class EatingState
        extends FaunaStateAdapter
        implements IFaunaState {

    public EatingState(Fauna context, FaunaData data) {
        super(context, data);
    }

    @Override
    public void eat() {
        System.out.println("Skirt");
    }

    @Override
    public void move() {
        System.out.println("EatingState");
        if (data.eat(context.getArea()) && data.getForca()<80){
            return;
        }

        if (data.getForca() >= 80) {
            changeState(FaunaState.MOVING);
        } else {
            if (data.existemArvores()) {
                changeState(FaunaState.LOOKING_FOR_FOOD);
            } else if (data.existeFauna()) {
                changeState(FaunaState.HUNTING);
            } else {
                changeState(FaunaState.MOVING);
            }
        }
    }

    @Override
    public void multiply() {}

    @Override
    public FaunaState getState() { return FaunaState.EATING; }
}
