package pt.isec.pa.javalife.model.fsm.states;

import pt.isec.pa.javalife.model.data.Area;
import pt.isec.pa.javalife.model.data.Fauna;
import pt.isec.pa.javalife.model.data.FaunaData;

import pt.isec.pa.javalife.model.fsm.FaunaState;
import pt.isec.pa.javalife.model.fsm.FaunaStateAdapter;

public class LookingForFoodState extends FaunaStateAdapter implements IFaunaState {

    public LookingForFoodState(Fauna context, FaunaData data) {
        super(context, data);
    }

    @Override
    public void act() {
        Area novaArea;
        Boolean [] success = new Boolean[1]; // <- Encontrou comida

        success[0] = false;


        if (data.isDead()) {
            changeState(FaunaState.DEAD);
        }

        if (!data.existemArvores()) {
            changeState(FaunaState.HUNTING);
        } else {
            novaArea = data.act_lookingForFood(context.getArea(), success);
            if (!novaArea.isInvalid()) {
                context.setArea(novaArea);

                if (success[0]) {
                    changeState(FaunaState.EATING);
                }

            }
        }

    }

    @Override
    public FaunaState getState() { return FaunaState.LOOKING_FOR_FOOD; }
}
