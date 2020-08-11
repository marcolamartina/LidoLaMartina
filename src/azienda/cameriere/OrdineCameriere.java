package azienda.cameriere;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DBMS;
import model.Carrello;
import model.Comande;
import model.Prodotto;



/**
 * Questa servlet intercetta le richieste relative alla pagina che consente ad un cameriere di prendere le ordinazioni per un cliente.
 * @author Marco La Martina
 */
@WebServlet({ "/OrdineCameriere", "/ordinecameriere"})
public class OrdineCameriere extends CameriereHome {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		if(checkSession(request, response)) {	
			String address = "/WEB-INF/azienda/cameriere/ordineCameriere.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(address);
			dispatcher.forward(request, response);
		}	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(checkSession(request, response)) {
			try {
				if(request.getParameter("nome")!= null && request.getParameter("cognome")!=null) {
					String nome=request.getParameter("nome");
					String cognome=request.getParameter("cognome");
					response.setContentType("application/json");
					response.getWriter().println(DBMS.searchClient(nome, cognome)) ;
					return;	
				}
				
				if(request.getParameter("idprodotto")==null || request.getParameter("quantity")==null || request.getParameter("idutente")==null) {
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					return;
				}
				Prodotto prodotto;
				int IDProdotto = Integer.parseInt(request.getParameter("idprodotto"));
				int IDUtente = Integer.parseInt(request.getParameter("idutente"));
				String note = request.getParameter("note");
				int quantity = Integer.parseInt(request.getParameter("quantity"));
				
				Comande comande= (Comande) request.getSession().getAttribute("comande");
				// Se l'oggetto comande è vuoto crea un nuovo oggetto comande.
				if(comande == null) {
					comande = new Comande();
				}
				Carrello carrello=comande.get(IDUtente);
				if(carrello==null) {
					comande.aggiungiCarrello(IDUtente);
					carrello=comande.get(IDUtente);
				}
				
				/* Se il prodotto non è ancora presente all'interno del carrello dell'utente richiede al database i dati del prodotto
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
				
				/* Aggiunge il prodotto al carrello dell'utente (caso in cui non era presente) o ne incrementa la quantità
				 * (caso in cui era già nel carrello del cliente).
				 */
				carrello.aggiungiProdotto(prodotto, quantity, note);
				request.getSession().setAttribute("comande", comande);
				
			} catch(Exception e) {
				e.printStackTrace();
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}	
		}
	}

}