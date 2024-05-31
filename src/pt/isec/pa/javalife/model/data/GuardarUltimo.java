package pt.isec.pa.javalife.model.data;

public class GuardarUltimo {
    private static int id = -1;
    private static Elemento tipo = null;

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        GuardarUltimo.id = id;
    }

    public static Elemento getTipo() {
        return tipo;
    }

    public static void setTipo(Elemento tipo) {
        GuardarUltimo.tipo = tipo;
    }
}
