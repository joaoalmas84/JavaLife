package pt.isec.pa.javalife;

import pt.isec.pa.javalife.model.data.IElemento;
import pt.isec.pa.javalife.model.gameengine.IGameEngine;
import pt.isec.pa.javalife.model.gameengine.IGameEngineEvolve;

import java.io.Serializable;
import java.util.Set;

public class Ecossistema
        implements Serializable, IGameEngineEvolve {

    private Set<IElemento> elementos;

    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {

    }

    //TODO
}