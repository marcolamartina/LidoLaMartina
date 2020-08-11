package model;

import java.io.Serializable;

/**
 * Classe che contiene i dati di un utente.
 * @author Marco La Martina
 */
public class Utente implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Codice identificativo dell'utente.
	 */
	private int idUtente;
	/**
	 * Nome, cognome, email e cellulare dell'utente.
	 */
	private String nome, cognome, email, cellulare;
	
	
	/**
	 * Costruttore della classe senza parametri.
	 */
	public Utente() {
		super();
	}
	
	/**
	 * Costruttore della classe con parametri.
	 * @param idUtente
	 * @param nome
	 * @param cognome
	 * @param email
	 * @param cellulare
	 */
	public Utente(int idUtente, String nome, String cognome, String email, String cellulare) {
		this.idUtente = idUtente;
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.cellulare = cellulare;
	}
	
	
	/* Metodi get per accedere dall'esterno ai dati che identificano un utente. */
	public int getIdUtente() {
		return idUtente;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getCognome() {
		return cognome;
	}
	
	public String getEmail() {
		return email;
	}
	
	
	public String getCellulare() {
		return cellulare;
	}
	
 }

