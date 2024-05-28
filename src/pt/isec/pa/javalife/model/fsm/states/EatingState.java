package pt.isec.pa.javalife.model.fsm.states;

import pt.isec.pa.javalife.model.data.FaunaData;
import pt.isec.pa.javalife.model.data.Fauna;
import pt.isec.pa.javalife.model.fsm.FaunaState;
import pt.isec.pa.javalife.model.fsm.FaunaStateAdapter;

public class EatingState
        extends FaunaStateAdapter
        implements IFaunaState {

    public EatingState(Fauna context, FaunaData data) {
        super(context, data);
    }

    @Override
    public void act() {
        if (data.act_eat(context.getArea()) && data.getForca()<100){
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
    public FaunaState getState() { return FaunaState.EATING; }
}
