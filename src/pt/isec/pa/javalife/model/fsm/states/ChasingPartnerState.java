package pt.isec.pa.javalife.model.fsm.states;

import pt.isec.pa.javalife.model.data.Area;
import pt.isec.pa.javalife.model.data.FaunaData;
import pt.isec.pa.javalife.model.data.Fauna;
import pt.isec.pa.javalife.model.fsm.FaunaState;
import pt.isec.pa.javalife.model.fsm.FaunaStateAdapter;

public class ChasingPartnerState extends FaunaStateAdapter implements IFaunaState {

    public ChasingPartnerState(Fauna context, FaunaData data) {
        super(context, data);
    }

    @Override
    public void act() {

        if (data.isDead()) {
            changeState(FaunaState.DEAD);
            return;
        } else if (!data.existePartnerPrey()) {
            changeState(FaunaState.MOVING);
            return;
        } else {
            Area novaArea =data.act_chasingPartner(context.getArea());
            if(!novaArea.isInvalid()){
                context.setArea(novaArea);
            }
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
    public FaunaState getState() { return FaunaState.CHASING_PARTNER; }
}
