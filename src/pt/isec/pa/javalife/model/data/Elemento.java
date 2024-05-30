package pt.isec.pa.javalife.model.data;

import java.io.Serializable;

public enum Elemento implements Serializable { INANIMADO, FLORA, FAUNA;
    public boolean makeElemento(double xI,double yI,double xF,double yF, SimulacaoManager SM){
        switch (this){
            case INANIMADO -> {
                return SM.adicionarInanimado(xI,yI,xF,yF);
            }
            case FLORA -> {
                return SM.adicionarFlora(xI,yI,xF,yF);
            }
            case FAUNA -> {
                return SM.adicionarFauna(xI,yI,xF,yF);
            }

        }
        return false;
    }
}