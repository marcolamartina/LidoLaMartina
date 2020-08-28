package model;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Classe che contiene la mappa delle comande che i clienti ordinano al cameriere.
 * @author La Martina Marco
 */
public class Comande implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Mappa dei carrelli che il cameriere crea quando prende le ordinazioni
	 */
	private Map<Integer, Carrello> carrelli; 
	
	/**
	 * Costruttore della classe senza parametri.
	 */
	public Comande() {
		this.carrelli = new LinkedHashMap<Integer, Carrello>();
	}
	
	
	/**
	 * Metodo get per accedere dall'esterno ai carrelli.
	 */
	public Map<Integer, Carrello> getCarrelli() {
		return carrelli;
	}
	
	/**
	 * Metodo get per accedere dall'esterno al carrello di un utente.
	 */
	public Carrello get(int idutente) {
		return carrelli.get(idutente);
	}
	
	
	/**
	 * Metodo per aggiungere dall'esterno il carrello di un utente.
	 */
	public void aggiungiCarrello(int idutente) {
		if(carrelli.get(idutente)==null) {
			carrelli.put(idutente, new Carrello());
		}
	}
	
	/**
	 * Metodo per eliminare dall'esterno un prodotto di del carrello di un utente.
	 */
	public void rimuoviProdotto(int idProdotto, int idUtente) {
		carrelli.get(idUtente).rimuoviProdotto(idProdotto);
		if(carrelli.get(idUtente).numeroProdotti()==0) {
			carrelli.remove(idUtente);
		}
	}
	
	/**
	 * Rimuove tutti i prodotti dal carrello.
	 * @param IDUtente
	 */
	public void svuotaCarrello(int IDUtente) {
		carrelli.get(IDUtente).svuotaCarrello();
		carrelli.remove(IDUtente);
	}
	
}
