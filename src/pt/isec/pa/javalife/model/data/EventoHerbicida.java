package pt.isec.pa.javalife.model.data;

public class EventoHerbicida implements IEvento{
    private IElemento elemento;

    public EventoHerbicida(IElemento elemento){
        this.elemento = elemento;
    }

    @Override
    public boolean execute() {
        if(elemento.getType() == Elemento.FLORA){
            Flora f = (Flora) elemento;
            f.setIsDead(true);
            return true;
        }
        return false;
    }

    @Override
    public int tempoExecucao() {
        return 0;
    }


    /*
    private int[] array;
    private ArrayList<IElemento> referencias;
    private boolean iniciado = false;
    EventoHerbicida(int[] array){
        this.array = new int[array.length];
        System.arraycopy(array, 0, this.array, 0, array.length);
    }

    @Override
    public boolean execute() {
        if(!iniciado) throw new IllegalStateException("Evento não iniciado");
        for (IElemento elemento : referencias) {
            if(elemento instanceof Flora){
                referencias.remove(elemento);
            }
        }
        return false;
    }

    @Override
    public boolean remove(IElemento elemento) {
        return false;
    }

    @Override
    public boolean add(ArrayList<IElemento> elementos) {
        elementos = new ArrayList<>();
        for (IElemento elemento : elementos) {
            if(elemento instanceof Flora && ArrayContains(array, elemento.getId())){
                referencias.add(elemento);
            }
        }
        iniciado = true;
        return true;
    }

    private boolean ArrayContains(int[] array, int value){
        for (int i : array) {
            if(i == value){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean add(IElemento elemento) {
        return false;
    }

    @Override
    public int tempoExecucao() {
        return 0;
    }

    */
}
