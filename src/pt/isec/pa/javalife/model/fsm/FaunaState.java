package pt.isec.pa.javalife.model.fsm;

import pt.isec.pa.javalife.model.data.Fauna;
import pt.isec.pa.javalife.model.data.FaunaData;
import pt.isec.pa.javalife.model.fsm.states.*;

public enum FaunaState  {
    MOVING, LOOKING_FOR_FOOD, EATING, DEAD, HUNTING, CHASING_PARTNER;

    public static IFaunaState getInstance(
            FaunaState type, Fauna context, FaunaData data
    ) {
        return switch(type) {
            case MOVING -> new MovingState(context, data);
            case LOOKING_FOR_FOOD -> new LookingForFoodState(context, data);
            case EATING -> new EatingState(context, data);
            case DEAD -> new DeadState(context, data);
            case HUNTING -> new HuntingState(context, data);
            case CHASING_PARTNER -> new ChasingPartnerState(context, data);
        };
    }

    public IFaunaState getInstance(Fauna context, FaunaData data) {
        return switch(this) {
            case MOVING -> new MovingState(context, data);
            case LOOKING_FOR_FOOD -> new LookingForFoodState(context, data);
            case EATING -> new EatingState(context, data);
            case DEAD -> new DeadState(context, data);
            case HUNTING -> new HuntingState(context, data);
            case CHASING_PARTNER -> new ChasingPartnerState(context, data);
        };
    }
}
