package pt.isec.pa.javalife.model.data;

import java.io.Serializable;

public interface IEvento extends Serializable {
    public boolean execute();
    public int tempoExecucao();
}
