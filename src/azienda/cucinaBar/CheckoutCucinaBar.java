package azienda.cucinaBar;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import database.DBMS;
import model.Comande;
import model.Utente;
import utils.Mailer;



/**
 * Questa servlet intercetta le richieste relative alla pagina che consente ad un cameriere di prendere le ordinazioni per un cliente.
 * @author Marco La Martina
 */
@WebServlet({ "/CheckoutCucinaBar", "/checkoutcucinabar"})
public class CheckoutCucinaBar extends CucinaBarHome {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		if(checkSession(request, response)) {	
			String address = "/WEB-INF/azienda/cucina-bar/checkoutCucinaBar.jsp";
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
				if(request.getParameter("idutente")==null) {
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					return;
				}
				Comande comande=(Comande)request.getSession().getAttribute("comande");
				if(comande==null) {
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					return;
				}
				int IDUtente=Integer.parseInt(request.getParameter("idutente"));
				Utente utente=DBMS.getUtente(IDUtente);
				if(utente==null) {
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					return;
				}
				
				int tavolo=0;
				if(request.getParameter("tavolo")!=null){
					tavolo=Integer.parseInt(request.getParameter("tavolo"));
				}
									
				// Crea il conto con le informazioni dell'ordine appena effettuato se non è presente uno già aperto 
				// e aggiunge le ordinazioni effettuate dal cliente, in modo che risultino nel bar/ristorante.
				DBMS.inserisciOrdinazione(comande.get(IDUtente), utente.getIdUtente(), tavolo);
	
				// Invia una mail di avvenuto pagamento degli ordini nel carrello al cliente.
				DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd/MM/yyyy' alle ore 'HH:mm");
		        String now=LocalDateTime.now().format(formatter);
				String messaggio = "<h1>Ordine avvenuto con successo!</h1> <p>Gentile " + utente.getNome()
					+ " " + utente.getCognome() + ", <br>grazie per il tuo ordine. Abbiamo ricevuto"
					+ " la tua ordinazione avvenuta in data " + now 
					+ ". A breve riceverai l'ordine direttamente al tavolo che hai scelto, pertanto ti invitiamo a non"
					+ " allontanarti dal tavolo nei prossimi minuti.<br>Qui di seguito riportiamo i dettagli del"
					+ " tuo ordine, visibili anche selezionando nel nostro sito.</p>"
					+ "<h2>Resoconto ordine:</h2>" + comande.get(IDUtente).restituisciProdotti() + "<br />Lo staff del lido";
				Mailer mailer = new Mailer("smtp.gmail.com", "lidogorgobeach@gmail.com", "gupzeg-jundI2-muczig" , utente.getEmail(), 
						"Lido Gorgo Beach - Ordine Carrello", messaggio);
				Thread thread = new Thread(mailer);
				thread.start();
				comande.svuotaCarrello(IDUtente);
			} catch(Exception e) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}	
		}
	}

}