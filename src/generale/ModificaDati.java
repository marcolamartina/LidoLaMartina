package generale;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DBMS;
import model.Account;
import model.Notify;
import model.Utente;
import utils.Mailer;

/**
 * Questa servlet intercetta le richieste relative alla pagina per modificare i dati personali.
 * @author Marco La Martina
 */
@WebServlet({ "/ModificaDati", "/modificadati"})
public class ModificaDati extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("account") == null ) {
			response.sendError(401);
		}
		String address = "/WEB-INF/generale/modificaDati.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		dispatcher.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("account") == null ) {
			response.sendError(401);
		}
		try {
			Account account=(Account)request.getSession().getAttribute("account");
			Utente utente=account.getUtente();
			int flag=0;
			String nome, cognome, email, cellulare, errore;
			nome = request.getParameter("nome");
			cognome = request.getParameter("cognome");
			email = request.getParameter("email");
			cellulare = request.getParameter("cellulare");
			errore = verificaDatiModificaDati(nome, cognome, email, cellulare);
			
			if(errore!=null) { //sono stati inseriti dati scorretti
				request.getSession().setAttribute("notify",new Notify(2,"Errore",errore));
				response.sendRedirect(request.getContextPath());
				return;
			}
			
			String messaggio = "<h1>Lido Gorgo Beach</h1> <p>Ciao " + nome + " " + cognome + ", <br>"
					+ "ti comunichiamo che sono state apportate modifiche al tuo account, "
					+ "<a href='"+ Mailer.getAddress() + request.getContextPath() + "'> visita il nostro sito</a> per verificare le modifiche."
					+ "<br>Ti auguriamo una buona permanenza nel nostro lido e speriamo di vederti presto! <br><br>"
					+ "Lo staff del lido</p>";
			
			if (nome.compareTo(utente.getNome())!=0) {
				DBMS.aggiornaNome(utente.getIdUtente(), nome);
				flag=1;
			}
			if (cognome.compareTo(utente.getCognome())!=0) {
				DBMS.aggiornaCognome(utente.getIdUtente(), cognome);
				flag=1;
			}
			if (email.compareTo(utente.getEmail())!=0) {
				DBMS.aggiornaEmail(utente.getIdUtente(), email);
				flag=1;
				Mailer mailer = new Mailer( utente.getEmail(), "Lido Gorgo Beach - Modifica dati", messaggio);
				Thread thread = new Thread(mailer);
				thread.start();
			}
			if (cellulare.compareTo(utente.getCellulare())!=0) {
				DBMS.aggiornaCellulare(utente.getIdUtente(), cellulare);
				flag=1;
			}
			if(flag!=0) {
				account.setUtente(DBMS.getUtente(utente.getIdUtente()));
				request.getSession().setAttribute("account", account);
				
				Mailer mailer2 = new Mailer(email, "Lido Gorgo Beach - Modifica dati", messaggio);
				Thread thread2 = new Thread(mailer2);
				thread2.start();
				request.getSession().setAttribute("notify",new Notify(1,"Completato","Modifica effettuata con successo"));
			}
		    
			
			response.sendRedirect(request.getContextPath()+"/Profile");
		    
		}catch(Exception e) {
			e.printStackTrace();
			request.getSession().setAttribute("notify",new Notify(2,"Errore","Abbiamo riscontrato un errore"));
			response.sendRedirect(request.getContextPath());
		}
	}
	
	
	/**
	 * Verifica che i dati inseriti dall'utente in fase di modifica dei dati personali
	 * @param nome
	 * @param cognome
	 * @param email
	 * @param cellulare
	 */
	public static String verificaDatiModificaDati(String nome, String cognome, String email, String cellulare) {
		
		if(nome == null || cognome == null || email == null || cellulare == null) {
			return "I campi sono tutti richiesti.";
		}
		
		if(nome.replaceAll("\\s+","").contentEquals("") || cellulare.replaceAll("\\s+","").contentEquals("") || cognome.replaceAll("\\s+","").contentEquals("") || 
				email.replaceAll("\\s+","").contentEquals("") )
			return "I campi sono tutti richiesti.";
		
		String regex = "^[A-Za-zèùàòé][a-zA-Z'èùàòé ]*$";
		if(!nome.matches(regex))
			return "Il nome non rispetta il formato richiesto.";
		
		if(!cognome.matches(regex))
			return "Il cognome non rispetta il formato richiesto.";
		
		regex = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+[.][a-zA-Z0-9-.]+$";
		if(!email.matches(regex))
			return "L'email non rispetta il formato richiesto.";
		
		
		return null;
	}

}