package pt.isec.pa.javalife.model.data;

import java.io.Serializable;

public enum Elemento implements Serializable { INANIMADO, FLORA, FAUNA;
    public void makeElemento(double xI,double yI,double xF,double yF, SimulacaoManager SM){
        switch (this){
            case INANIMADO -> SM.adicionarInanimado(xI,yI,xF,yF);
            case FLORA -> SM.adicionarFlora(xI,yI,xF,yF);
            case FAUNA -> SM.adicionarFauna(xI,yI,xF,yF);

        }
    }
}