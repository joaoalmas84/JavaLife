package pt.isec.pa.javalife.model.fsm.states;

import pt.isec.pa.javalife.model.data.FaunaData;
import pt.isec.pa.javalife.model.data.Fauna;
import pt.isec.pa.javalife.model.data.IElemento;
import pt.isec.pa.javalife.model.fsm.FaunaState;
import pt.isec.pa.javalife.model.fsm.FaunaStateAdapter;

import java.util.Set;

public class ChasingPartnerState extends FaunaStateAdapter implements IFaunaState {

    public ChasingPartnerState(Fauna context, FaunaData data) {
        super(context, data);
    }

    @Override
    public void move() {
        System.out.println("ChasingPartnerState");

        if (data.isDead()) {
            changeState(FaunaState.DEAD);
            return;
        } else if (!data.existeFauna()) {
            return;
        } else {
            data.move_chasingPartner(context.getArea());

            if (data.getMatingCounter() == 10) {
                if (data.getForca() < 35) {

                    if (data.existemArvores()) {
                        changeState(FaunaState.LOOKING_FOR_FOOD);
                    } else if (data.existeFauna()) {
                        changeState(FaunaState.HUNTING);
                    } else {
                        changeState(FaunaState.MOVING);
                    }

                }
                data.setMatingCounter(0);
            } else if (data.getForca() < 35) {
                data.setMatingCounter(0);
                changeState(FaunaState.LOOKING_FOR_FOOD);
            }

            return;
        }
    }

    @Override
    public void eat() {}

    @Override
    public void multiply() {}

    @Override
    public FaunaState getState() { return FaunaState.CHASING_PARTNER; }
}
