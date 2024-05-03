package pt.isec.pa.javalife.model.data;

public class EventoInjetarForca implements IEvento{
    private int tempo;
    private IElemento elemento;
    private boolean iniciado = false;
    private int forcaAdicional;


    public EventoInjetarForca(IElemento elemento, int forcaAdicional){
        if(elemento == null) throw new IllegalArgumentException("Elemento não pode ser null");
        this.elemento = elemento;
        this.tempo = 10;
        this.forcaAdicional = forcaAdicional;
    }

    @Override
    public boolean execute() {
        if (tempo > 0) {
            tempo--;
            if (!iniciado) {
                if (elemento.getType() == Elemento.FAUNA) {
                    Flora f = (Flora) elemento;
                    //f.setForca(f.getForca() + forcaAdicional);
                    iniciado = true;
                    return true;
                }
            }
            return true;
        }else{
            if (iniciado) {
                if (elemento.getType() == Elemento.FAUNA) {
                    Flora f = (Flora) elemento;
                    //f.setForca(f.getForca() - forcaAdicional);
                    iniciado = false;
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public int tempoExecucao() {
        return tempo;
    }
}
