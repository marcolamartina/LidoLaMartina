package model;

import java.io.Serializable;

/**
 * Classe che contiene i dati di un prodotto.
 * @author Marco La Martina
 */
public class Prodotto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Codice identificativo del prodotto.
	 */
	private int idProdotto;
	/**
	 * Nome, categoria e ingredienti del prodotto.
	 */
	private String nome, categoria, ingredienti;
	/**
	 * Disponibile è un valore booleano che indica se il prodotto e tutti gli ingredienti del prodotto sono disponibili (1) o no (0).
	 */
	private boolean disponibile;
	/**
	 * Prezzo del prodotto.
	 */
	private double prezzo;
	
	/**
	 * Costruttore della classe senza parametri.
	 */
	public Prodotto() {
		super();
	}
	
	/**
	 * Costruttore della classe con parametri.
	 * @param idProdotto
	 * @param nome
	 * @param categoria
	 * @param ingredienti
	 * @param prezzo
	 * @param disponibile
	 */
	public Prodotto(int idProdotto, String nome, String categoria, String ingredienti, double prezzo, boolean disponibile) {
		this.idProdotto = idProdotto;
		this.nome = nome;
		this.categoria = categoria;
		this.ingredienti = ingredienti;
		this.prezzo = prezzo;
		this.disponibile = disponibile;
	}
	
	
	/* Metodi get per accedere dall'esterno ai dati che identificano un prodotto. */
	public int getIdProdotto() {
		return idProdotto;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getCategoria() {
		return categoria;
	}
	
	public String getIngredienti() {
		return ingredienti;
	}
	
	
	public boolean isDisponibile() {
		return disponibile;
	}
	
	
	public double getPrezzo() {
		return prezzo;
	}
	
 }
