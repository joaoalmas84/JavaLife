package pt.isec.pa.javalife.model.data;

public class EventoSol implements IEvento{
    private int tempo;
    private IElemento elemento;
    private boolean iniciado = false;

    EventoSol(IElemento elemento){
        if(elemento == null) throw new IllegalArgumentException("Elemento não pode ser null");
        this.elemento = elemento;
        this.tempo = 10;
    }

    @Override
    public boolean execute() {
        if (tempo > 0) {
            tempo--;
            if(!iniciado) {
                if (elemento.getType() == Elemento.FLORA) {
                    Flora f = (Flora) elemento;
                    f.setForca(f.getForca() * 2);
                    iniciado = true;
                    return true;
                } else if (elemento.getType() == Elemento.FAUNA) {
                    Fauna f = (Fauna) elemento;
                    //f.setVelucidade(f.getVelucidade() / 2);
                    iniciado = true;
                    return true;
                }
            }
        }else{
            if(iniciado == true) {
                if (elemento.getType() == Elemento.FLORA) {
                    Flora f = (Flora) elemento;
                    f.setForca(f.getForca() / 2);
                    return false;
                } else if (elemento.getType() == Elemento.FAUNA) {
                    Fauna f = (Fauna) elemento;
                    //f.setVelucidade(f.getVelucidade() * 2);
                    return false;
                }
            }
            iniciado = false;
        }
        return false;
    }

    @Override
    public int tempoExecucao() {
        return tempo;
    }






    /*
    private int tempo;
    private Map<IElemento, Boolean> referencias;
    EventoSol(){
        this.tempo = 10;
    }



    @Override
    public boolean execute() {
        if(tempo > 0) {
            tempo--;
            for (Map.Entry<IElemento, Boolean> entrada : referencias.entrySet()) {
                if (!entrada.getValue()) {
                    Flora f = (Flora) entrada.getKey();
                    f.setForca(f.getForca() * 2);
                    entrada.setValue(true);
                }
            }
            return true;
        }else{
            for (Map.Entry<IElemento, Boolean> entrada : referencias.entrySet()) {
                if (entrada.getValue()) {
                    Flora f = (Flora) entrada.getKey();
                    f.setForca(f.getForca() / 2);
                    entrada.setValue(false);
                }
            }
            return false;
        }
    }



    @Override
    public int tempoExecucao() {
        return tempo;
    }

    */

}
