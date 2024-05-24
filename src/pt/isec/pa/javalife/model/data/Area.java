package pt.isec.pa.javalife.model.data;

import java.awt.*;

public record Area(double xi, double yi, double xf, double yf){

    public Area clone() throws CloneNotSupportedException {
        return (Area) super.clone();
    }

    public boolean isOverlapping(Area other) {
        // Verifica se uma área está totalmente à direita, à esquerda, acima ou abaixo da outra
        if (this.yf <= other.yi || this.yi >= other.yf ||
                this.xi >= other.xf || this.xf <= other.xi) {
            return false;
        }
        // Caso contrário, há sobreposição
        return true;
    }

    public boolean isInvalid() {
        return xi < 0 || yi < 0 || xf < 0 || yf < 0 || xi > xf || yi > yf;
    }
}
