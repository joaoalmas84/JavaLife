package pt.isec.pa.javalife.model.fsm.states;

import pt.isec.pa.javalife.model.data.Area;
import pt.isec.pa.javalife.model.data.FaunaData;
import pt.isec.pa.javalife.model.data.Fauna;
import pt.isec.pa.javalife.model.fsm.FaunaState;
import pt.isec.pa.javalife.model.fsm.FaunaStateAdapter;

public class MovingState extends FaunaStateAdapter implements IFaunaState {

    public MovingState(Fauna context, FaunaData data) {
        super(context, data);
    }

    @Override
    public void move() {
        Area novaArea;
        
        System.out.println("MovingState");
        novaArea = data.move(context.getArea());

        if(!novaArea.isInvalid()){
            context.setArea(novaArea);
        }

        if(data.isDead()){
            changeState(FaunaState.DEAD);
            return;
        }

        if (data.getForca() <= 35 && data.existemArvores()) {
            System.out.println("Looking for food");
            changeState(FaunaState.LOOKING_FOR_FOOD);
        }

        if (data.getForca() <= 35 && !data.existemArvores() && data.existePartnerPrey()) {
            System.out.println("Hunting");
            changeState(FaunaState.HUNTING);
        }

        if (data.getForca() > 50 && data.existePartnerPrey()) {
            System.out.println("Chasing partner");
            changeState(FaunaState.CHASING_PARTNER);
        }
    }


    @Override
    public void eat() {}

    @Override
    public void multiply() {}

    @Override
    public FaunaState getState() { return FaunaState.MOVING; }
}
