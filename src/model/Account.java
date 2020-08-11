package model;

import java.util.Map;
import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * Classe che contiene i dati di un account.
 * @author Marco La Martina
 */
public class Account implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Dati identificativi dell'utente.
	 */
	private Utente utente;
	
	/**
	 * Mappa dei ruoli dell'utente.
	 */
	private Map<String, Integer> ruoli; 
	
	
	
	/**
	 * Costruttore della classe senza parametri.
	 */
	public Account() {
		this.ruoli = new LinkedHashMap<String, Integer>();
		this.utente = new Utente();
	}
	
	
	/**
	 * Costruttore della classe con parametri.
	 * @param utente
	 * @param ruoli
	 */
	public Account(Utente utente, Map<String, Integer> ruoli) {
		this.utente = utente;
		this.ruoli = ruoli;
	}
	
	
	
	/* Metodi get per accedere dall'esterno ai dati che identificano un account. */
	
	
	public Utente getUtente() {
		return utente;
	}
	
	
	public void setUtente(Utente utente) {
		this.utente=utente;
	}
	
	public Map<String, Integer> getRuoli() {
		return ruoli;
	}
 }
