package cliente;


import database.DBMS;
import model.Carrello;
import model.Notify;
import model.Prodotto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Questa servlet intercetta le richieste relative all'aggiunta dei prodotti al carrello
 * @author Marco La Martina
 */
@WebServlet({"/AggiungiProdotto", "/aggiungiprodotto"})
public class AggiungiProdotto extends ClienteHome {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(checkSession(request, response)) {
			try {
				if(request.getParameter("idprodotto")==null || request.getParameter("quantity")==null) {
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					return;
				}
				Prodotto prodotto;
				int IDProdotto = Integer.parseInt(request.getParameter("idprodotto"));
				String note = request.getParameter("note");
				int quantity = Integer.parseInt(request.getParameter("quantity"));
				Carrello carrello = (Carrello) request.getSession().getAttribute("carrello");
				// Se il carrello è vuoto crea un nuovo oggetto carrello.
				if(carrello == null) {
					carrello = new Carrello();
				}
				/* Se il prodotto non è ancora presente all'interno del carrello richiede al database i dati del prodotto
				 * selezionato. 
				 */
				prodotto=carrello.searchProdotto(IDProdotto);
				if(prodotto==null) {
					prodotto = DBMS.getProdotto(IDProdotto);
					if(prodotto == null) {
						response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						return;
					}
				}
				
				/* Aggiunge il prodotto al carrello (caso in cui non era presente) o ne incrementa la quantità
				 * (caso in cui era già nel carrello del cliente).
				 */
				carrello.aggiungiProdotto(prodotto, quantity, note);
				request.getSession().setAttribute("carrello", carrello);
			} catch(Exception e) {
				e.printStackTrace();
				request.getSession().setAttribute("notify",new Notify(2,"Errore","Abbiamo riscontrato un errore"));
				response.sendRedirect(request.getContextPath());
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

