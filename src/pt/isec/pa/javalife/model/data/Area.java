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

    public Area getAriaAdjacente(int direcao) {
        switch (direcao) {
            case 0 -> {
                Area novaAria = new Area(xi, yi - (yf - yi), xf, yi);
                return novaAria.isInvalid() ? null : novaAria;
            }
            case 1 -> {
                Area novaAria = new Area(xf, yi, xf + (xf - xi), yf);
                return novaAria.isInvalid() ? null : novaAria;
            }
            case 2 -> {
                Area novaAria = new Area(xi, yf, xf, yf + (yf - yi));
                return novaAria.isInvalid() ? null : novaAria;
            }
            case 3 -> {
                Area novaAria = new Area(xi - (xf - xi), yi, xi, yf);
                return novaAria.isInvalid() ? null : novaAria;
            }
            default -> {
                return null;
            }
        }

    }
}
