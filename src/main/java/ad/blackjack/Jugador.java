package ad.blackjack;

public class Jugador {

    private String nombre;
    private int puntuacion;

    public Jugador() {
        this.nombre = "";
        this.puntuacion = 500;
    }

    public Jugador(String nombre, int puntuacion) {
        this.nombre = nombre;
        this.puntuacion = puntuacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    @Override
    public String toString() {
        return nombre + ";" + puntuacion;
    }
}
