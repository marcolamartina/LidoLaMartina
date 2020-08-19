package model;

public class Postazione {
    private int numero;
    private int x;
    private int y;

    public Postazione() {
    }

    public Postazione(int numero, int x, int y) {
        this.numero = numero;
        this.x = x;
        this.y = y;
    }

    public int getNumero() {
        return numero;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
