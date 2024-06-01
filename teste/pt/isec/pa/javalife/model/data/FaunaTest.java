package pt.isec.pa.javalife.model.data;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isec.pa.javalife.model.fsm.FaunaState;
import pt.isec.pa.javalife.model.fsm.states.LookingForFoodState;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FaunaTest {
    private Fauna fauna;
    private FaunaData faunaData;
    private Ecossistema ecossistema;

    @BeforeEach
    public void setUp() {
        ecossistema = new Ecossistema();
        faunaData = new FaunaData(ecossistema);
        fauna = new Fauna(300, 300, 330, 330, faunaData);
    }

    @Test
    public void testInitialState() {
        assertEquals(FaunaState.MOVING, fauna.getState());
    }

    @Test
    public void testTransitionToLookingForFood() {
        ecossistema.addElemento(new Flora(15, 15, 30, 30));
        faunaData.addForca(-20); // Reduz a força para simular a necessidade de comida
        fauna.act_context();
        assertEquals(FaunaState.LOOKING_FOR_FOOD, fauna.getState());
    }

    @Test
    public void testTransitionToHunting() {
        ecossistema.addElemento(new Fauna(15, 15, 30, 30, ecossistema));
        ecossistema.addElemento(new Fauna(15, 15, 30, 30, ecossistema));
        faunaData.addForca(-20); // Reduz a força para simular a necessidade de caçar
        fauna.act_context();
        assertEquals(FaunaState.HUNTING, fauna.getState());
    }

    @Test
    public void testTransitionToChasingPartner() {
        ecossistema.addElemento(new Fauna(15, 15, 30, 30, ecossistema));
        ecossistema.addElemento(new Fauna(15, 15, 30, 30, ecossistema));
        faunaData.addForca(10); // Aumenta a força para simular a busca por parceiro
        fauna.act_context();
        assertEquals(FaunaState.CHASING_PARTNER, fauna.getState());
    }

    @Test
    public void testTransitionToEating() {
        ecossistema.addElemento(new Flora(15, 15, 450-15, 800-15));
        faunaData.addForca(-30);
        fauna.changeState(new LookingForFoodState(fauna, faunaData));
        fauna.act_context();
        assertEquals(FaunaState.EATING, fauna.getState());
        //assertEquals(FaunaState.EATING, fauna.getState());
    }

    @Test
    public void testTransitionToDead() {
        faunaData.addForca(-999); // Reduz a força a zero
        fauna.act_context();
        assertEquals(FaunaState.DEAD, fauna.getState());
    }

    @Test
    public void testMultipleTransitions() {
        ecossistema.addElemento(new Flora(15, 15, 450-15, 800-15));
        ecossistema.addElemento(new Fauna(15, 15, 30, 30, ecossistema));
        // Verifica se a Fauna está no estado inicial
        assertEquals(FaunaState.MOVING, fauna.getState());

        // Simula a necessidade de encontrar comida
        faunaData.addForca(-20);
        fauna.act_context();
        assertEquals(FaunaState.LOOKING_FOR_FOOD, fauna.getState());


        fauna.act_context();
        assertEquals(FaunaState.EATING, fauna.getState());

        // Aumenta a força da Fauna
        faunaData.addForca(55);
        ecossistema.removeFlora(0);
        fauna.act_context();
        assertEquals(FaunaState.MOVING, fauna.getState());

        // Simula a necessidade de caçar
        faunaData.addForca(-50);
        ecossistema.addElemento(new Fauna(15, 15, 30, 30, ecossistema));
        fauna.act_context();
        assertEquals(FaunaState.HUNTING, fauna.getState());

        // Simula caçar
        ecossistema.removeFauna(1);
        ecossistema.removeFauna(2);
        fauna.act_context();
        assertEquals(FaunaState.MOVING, fauna.getState());

        // Simula a necessidade de encontrar um parceiro

        faunaData.addForca(-999);
        fauna.act_context();
        assertEquals(FaunaState.DEAD, fauna.getState());
    }

}
