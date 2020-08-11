package model;

import java.io.Serializable;



/**
 * Classe che contiene i prodotti che il cliente desidera ordinare (il carrello degli ordini).
 * @author La Martina Marco
 */
public class Ordinazione implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * ID del prodotto, note aggiuntive e quantità 
	 */
	private int quantita;
	private String note;

	/**
	 * Costruttore della classe senza parametri.
	 */
	public Ordinazione() {
		
	}
	
	/**
	 * Costruttore della classe con parametri.
	 * @param prodotti
	 */
	public Ordinazione(int quantita, String note ) {
		this.quantita = quantita;
		this.setNote(note);
	}
	
	
	/**
	 * Metodo get per accedere dall'esterno ai dati che caratterizzano un'ordinazione.
	 */
	public String getNote() {
		return note;
	}
	
	/**
	 * Metodo get per accedere dall'esterno ai dati che caratterizzano un'ordinazione.
	 */
	public int getQuantita() {
		return quantita;
	}
	
	/**
	 * Metodo set per modificare dall'esterno i dati che caratterizzano un'ordinazione.
	 */
	public void setNote(String note) {
		if(note!=null && note.length()>0) {
			this.note=note;
		}else {
			this.note=null;
		}
		
	}
	
	/**
	 * Metodo set per modificare dall'esterno i dati che caratterizzano un'ordinazione.
	 */
	public void setQuantita(int quantita) {
		this.quantita=quantita;
	}
}
