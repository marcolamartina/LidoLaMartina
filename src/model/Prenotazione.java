package model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Classe che contiene il numero massimo di sdraio acquistabili e i prezzi delle sdraio e delle postazioni
 * @author La Martina Marco
 */

public class Prenotazione implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Intero che indica il numero massimo di sdraio disponibili nello stabilimento
     */
    private static int sdraioMax=40;

    /**
     * Double che indica il prezzo delle sdraio singole
     */
    private static double prezzoSdraio=7.00;



    /**
     * Metodo get per accedere dall'esterno al numero massimo di sdraio.
     * */
    public static int getSdraioMax() {
        return sdraioMax;
    }


    /**
     * Metodo get per accedere dall'esterno al prezzo delle sdraio singole.
     * */
    public static double getPrezzoSdraio() {
        return prezzoSdraio;
    }

    /**
     * Metodo per ottenere il prezzo delle postazioni in base alla data.
     * */
    public static double getPrezzoPostazione(LocalDate data) {
        if(data.getMonthValue()==6){ //prezzo Giugno
            return 14.00;
        } else if(data.getMonthValue()==7){ //prezzo Luglio
            return 16.00;
        } else if(data.getMonthValue()==8){ //prezzo Agosto
            return 18.00;
        } else if(data.getMonthValue()==9){ //prezzo Settembre
            return 14.00;
        }
        return 14.00;
    }
}
