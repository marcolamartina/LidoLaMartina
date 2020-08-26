package cliente;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DBMS;
import utils.Mailer;
import model.Account;
import model.Notify;
import model.Utente;



/**
 * Questa servlet intercetta le richieste relative alla pagina del carrello.
 * @author Marco La Martina
 */
@WebServlet({ "/Ordina", "/ordina"})
public class Ordina extends ClienteHome {
	private static final long serialVersionUID = 1L;
       


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(checkSession(request, response)) {
			try {
				
				model.Carrello carrello=(model.Carrello)request.getSession().getAttribute("carrello");
				if(carrello==null) {
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					return;
				}
				
				Account account=(Account)request.getSession().getAttribute("account");
				Utente utente=account.getUtente();
				int tavolo=0;
				if(request.getParameter("tavolo")!=null){
					tavolo=Integer.parseInt(request.getParameter("tavolo"));
				}
									
				// Crea il conto con le informazioni dell'ordine appena effettuato se non è presente uno già aperto 
				// e aggiunge le ordinazioni effettuate dal cliente, in modo che risultino nel bar/ristorante.
				DBMS.inserisciOrdinazione(carrello, utente.getIdUtente(), tavolo);
	
				// Invia una mail di avvenuta ordinazione dei prodotti nel carrello.
				DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd/MM/yyyy' alle ore 'HH:mm");
		        String now=LocalDateTime.now().format(formatter);
				String messaggio = "<h1>Ordine avvenuto con successo!</h1> <p>Gentile " + utente.getNome()
					+ " " + utente.getCognome() + ", <br>grazie per il tuo ordine. Abbiamo ricevuto"
					+ " la tua ordinazione avvenuta in data " + now 
					+ ". A breve riceverai l'ordine direttamente al tavolo che hai scelto, pertanto ti invitiamo a non"
					+ " allontanarti dal tavolo nei prossimi minuti.<br>Qui di seguito riportiamo i dettagli del"
					+ " tuo ordine, visibili anche selezionando nel nostro sito.</p>"
					+ "<h2>Resoconto ordine:</h2>" + carrello.restituisciProdotti() + "<br />Lo staff del lido";
				Mailer mailer = new Mailer("smtp.gmail.com", "lidogorgobeach@gmail.com", "gupzeg-jundI2-muczig" , utente.getEmail(), 
						"Lido Gorgo Beach - Ordine Carrello", messaggio);
				Thread thread = new Thread(mailer);
				thread.start();
				carrello.svuotaCarrello();
								
				
				
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