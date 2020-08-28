package generale;

import database.DBMS;
import model.Account;
import model.Notify;
import model.Utente;
import utils.Mailer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Questa servlet intercetta le richieste relative alla pagina per modificare i dati personali.
 * @author Marco La Martina
 */
@WebServlet({ "/ModificaPassword", "/modificapassword"})
public class ModificaPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("account") == null ) {
			response.sendError(401);
		}
		String address = "/WEB-INF/generale/modificaPassword.jsp";
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
			String password, password_conf, password_attuale, errore, nome, cognome, email;
			password = request.getParameter("password");
			password_conf = request.getParameter("password_conf");
			password_attuale = request.getParameter("password_attuale");
			nome=utente.getNome();
			cognome=utente.getCognome();
			email=utente.getEmail();
			int IDUtente=utente.getIdUtente();
			
			errore = verificaDatiModificaPassword(password, password_conf, password_attuale);
			
			if(errore!=null) { //sono stati inseriti dati scorretti
				request.getSession().setAttribute("notify",new Notify(2,"Errore",errore));
				response.sendRedirect(request.getContextPath());
				return;
			}
			
			
			flag=DBMS.aggiornaPassword(IDUtente, password, password_attuale);
			if(flag==0) {
				request.getSession().setAttribute("notify",new Notify(2,"Errore","La password inserita non corrisponde alla tua password attuale"));
				response.sendRedirect(request.getContextPath()+"/ModificaPassword");
				return;
			}
			
				
			String messaggio = "<h1>Lido Gorgo Beach</h1> <p>Ciao " + nome + " " + cognome + ", <br>"
					+ "ti comunichiamo che &egrave stata modificata la password, "
					+ "<a href=\"mailto:lidogorgobeach@gmail.com\">scrivici</a> se non sei stato tu a modificare la password."
					+ "<br>Ti auguriamo una buona permanenza nel nostro lido e speriamo di vederti presto! <br><br>"
					+ "Lo staff del lido</p>";
			Mailer mailer = new Mailer( email, "Lido Gorgo Beach - Modifica password", messaggio);
			Thread thread = new Thread(mailer);
			thread.start();
			request.getSession().setAttribute("notify",new Notify(1,"Completato","Password modificata con successo"));
			
			response.sendRedirect(request.getContextPath()+"/Profile");
		    
		}catch(Exception e) {
			e.printStackTrace();
			request.getSession().setAttribute("notify",new Notify(2,"Errore","Abbiamo riscontrato un errore"));
			response.sendRedirect(request.getContextPath());
		}
	}

	
	/**
	 * Verifica che i dati inseriti dall'utente in fase di modifica della password siano corretti
	 * @param password
	 * @param password_conf
	 * @param password_attuale
	 * @return errore
	 */
	public static String verificaDatiModificaPassword(String password, String password_conf, String password_attuale) {
		String errore = null;
		
		if(password == null || password_conf == null || password_attuale == null) {
			return errore = "I campi sono tutti richiesti.";
		}
		
		if(password_conf.replaceAll("\\s+","").contentEquals("") || password_attuale.replaceAll("\\s+","").contentEquals("") || password.replaceAll("\\s+","").contentEquals(""))
			return errore = "I campi sono tutti richiesti.";
		
		String regex = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}";
		if(!password.matches(regex))
			return errore = "La nuova password non rispetta il formato richiesto.";
		
		if(!password_attuale.matches(regex))
			return errore = "La password attuale non rispetta il formato richiesto.";
		
		if(!password.equals(password_conf))
			return errore ="Le password non corrispondono.";
		
		return errore;
	}
}