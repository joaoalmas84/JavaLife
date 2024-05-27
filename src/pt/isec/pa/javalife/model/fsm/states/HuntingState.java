package pt.isec.pa.javalife.model.fsm.states;

import pt.isec.pa.javalife.model.data.Area;
import pt.isec.pa.javalife.model.data.FaunaData;
import pt.isec.pa.javalife.model.data.Fauna;
import pt.isec.pa.javalife.model.fsm.FaunaState;
import pt.isec.pa.javalife.model.fsm.FaunaStateAdapter;

public class HuntingState extends FaunaStateAdapter implements IFaunaState {

    public HuntingState(Fauna context, FaunaData data) {
        super(context, data);
    }

    @Override
    public void act() {
        Area novaArea;

        if (data.isDead()) {
            changeState(FaunaState.DEAD);
            return;
        }

        if (!data.existePartnerPrey()) {
            changeState(FaunaState.MOVING);
        } else {
            novaArea = data.move_hunting(context.getArea());
            if (!novaArea.isInvalid()) {
                context.setArea(novaArea);

                if (data.isDead()) {
                    changeState(FaunaState.DEAD);
                } else if (data.getForca() <= 35) {

                    if (data.existemArvores()) {
                        changeState(FaunaState.LOOKING_FOR_FOOD);
                    } else if (!data.existemArvores() && !data.existePartnerPrey()) {
                        changeState(FaunaState.MOVING);
                    }

                } else {
                    changeState(FaunaState.MOVING);
                }

            }
        }

        if (!data.existemArvores() && !data.existeFauna()) {
            changeState(FaunaState.MOVING);
        }

    }

    @Override
    public FaunaState getState() { return FaunaState.HUNTING; }
}
