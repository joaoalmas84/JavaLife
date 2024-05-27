package pt.isec.pa.javalife.model.data;

import java.awt.*;

public record Area(double xi, double yi, double xf, double yf) {

    // Construtor
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

    public boolean isPointOverlapping(double x, double y) {
        return x >= xi && x <= xf && y >= yi && y <= yf;
    }

    public double distanceTo(Area other) {
        if (isOverlapping(other)) {
            return 0.0;
        }

        double dx = Math.max(0, Math.max(other.xi - this.xf, this.xi - other.xf));
        double dy = Math.max(0, Math.max(other.yi - this.yf, this.yi - other.yf));

        return Math.sqrt(dx * dx + dy * dy);
    }

    public double angleTo(Area other) {
        // Calcula o centro de ambas as áreas
        double centerX1 = (xi + xf) / 2.0;
        double centerY1 = (yi + yf) / 2.0;
        double centerX2 = (other.xi + other.xf) / 2.0;
        double centerY2 = (other.yi + other.yf) / 2.0;

        // Calcula a diferença entre as coordenadas dos centros
        double deltaX = centerX2 - centerX1;
        double deltaY = centerY2 - centerY1;

        // Calcula o ângulo usando atan2
        return Math.atan2(deltaY, deltaX);
    }

    @Override
    public String toString() {
        return "Area{" +
                "xi=" + xi +
                ", yi=" + yi +
                ", xf=" + xf +
                ", yf=" + yf +
                '}';
    }

    public double width() {
        return xf - xi;
    }
}
