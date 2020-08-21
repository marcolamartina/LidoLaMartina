package model;

import java.io.Serializable;

/**
 * Classe che contiene i dati delle postazioni.
 * @author La Martina Marco
 */

public class Postazione implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Numero identificativo della postazione
     */
    private int numero;

    /**
     * Coordinata X nella mappa della spiaggia
     */
    private int x;

    /**
     * Coordinata Y nella mappa della spiaggia
     */
    private int y;


    /**
     * Costruttore senza parametri
     */
    public Postazione() {
    }


    /**
     * Costruttore con parametri
     */
    public Postazione(int numero, int x, int y) {
        this.numero = numero;
        this.x = x;
        this.y = y;
    }

    /* Metodi get per accedere dall'esterno ai dati della postazione */
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
