package model;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Classe che contiene i prodotti che il cliente desidera ordinare (il carrello degli ordini).
 * @author La Martina Marco
 */
public class Carrello implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Mappa dei prodotti con le relative quantità e note che il cliente ha inserito nel carrello
	 * (ogni prodotto è una chiave e la relativa quantità e le note sono il valore corrispondente a quella
	 * chiave).
	 */
	private Map<Prodotto, Ordinazione> prodotti; 
	
	/**
	 * Costruttore della classe senza parametri.
	 */
	public Carrello() {
		this.prodotti = new LinkedHashMap<Prodotto, Ordinazione>();
	}
	
	/**
	 * Costruttore della classe con parametri.
	 * @param prodotti
	 */
	public Carrello(Map<Prodotto, Ordinazione> prodotti) {
		this.prodotti = prodotti;
	}
	
	/**
	 * Metodo get per accedere dall'esterno ai dati che caratterizzano un carrello.
	 */
	public Map<Prodotto, Ordinazione> getProdotti() {
		return prodotti;
	}
	
	/**
	 * Aggiunge un prodotto al carrello.
	 * @param prodotto
	 * @param quantita
	 * @param note
	 */
	public void aggiungiProdotto(Prodotto prodotto, int quantita, String note) {
		Prodotto p=searchProdotto(prodotto.getIdProdotto());
		if(p!=null) {
			Ordinazione ordinazione=prodotti.get(p);
			if(note!=null) {
				if(ordinazione.getNote()==null) {
					ordinazione.setNote(note);
				}else {
					ordinazione.setNote(ordinazione.getNote()+"\n"+quantita+" "+note);
				}
				
			}
			ordinazione.setQuantita(quantita+ordinazione.getQuantita());
		}else {
			prodotti.put(prodotto, new Ordinazione(quantita,note));
		}	
	}
	
	/**
	 * Ritorna il prodotto se è già presente nel carrello altrimenti ritorna null
	 * @param idProdotto
	 */
	public Prodotto searchProdotto(int idProdotto) {
		for (Prodotto p : prodotti.keySet()) {
        	if(p.getIdProdotto() == idProdotto)
        		return p;
		}
		return null;
	}
	
	/**
	 * Restituisce il costo totale dei prodotti nel carrello.
	 * @return totale
	 */
	public float restituisciTotale() {
		float totale = 0;
		for (Prodotto p : prodotti.keySet()) {
			totale += p.getPrezzo() * prodotti.get(p).getQuantita();
		}
		return totale;
	}
	
	/**
	 * Rimuove tutti i prodotti dal carrello.
	 */
	public void svuotaCarrello() {
		prodotti.clear();
	}
	
	/**
	 * Restituisce i prodotti ordinati, il loro prezzo e le loro quantità. Questa funzione viene
	 * chiamata per mostrare i dettagli dell'ordine nel corpo della mail inviata al cliente a 
	 * seguito del pagamento di tutti gli ordini nel carrello.
	 * @return listaAcquisti
	 */
	public String restituisciProdotti() {
		String listaAcquisti = "<table>";
		float totale = 0;
		for (Prodotto p : prodotti.keySet()) {
			listaAcquisti += "<tr><td>"+p.getNome() + "</td><td>x" + prodotti.get(p).getQuantita() + "</td><td>" + p.getPrezzo() * prodotti.get(p).getQuantita() + "0&euro;</td></tr>";
			totale += p.getPrezzo() * prodotti.get(p).getQuantita();
		}
		listaAcquisti += "<tr><td colspan=\"3\"><hr /></td></tr><tr><td>Totale: " + totale + "0&euro;</td></tr></table>";
		return listaAcquisti;
	}
	
	/**
	 * Rimuove un prodotto dal carrello.
	 * @param idProdotto
	 */
	public void rimuoviProdotto(int idProdotto) {
		Prodotto p = searchProdotto(idProdotto);
		if(p != null)
			prodotti.remove(p);
	}
	
	/**
	 * Restituisce il numero dei prodotti nel carrello
	 */
	public int numeroProdotti() {
		return prodotti.keySet().size();
	}
	
	/**
	 * Modifica la quantità di un'ordinazione.
	 * @param idProdotto
	 */
	public void setQuantity(int idProdotto, int quantita) {
		Prodotto p = searchProdotto(idProdotto);
		if(p != null)
			prodotti.get(p).setQuantita(quantita);
	}
}
