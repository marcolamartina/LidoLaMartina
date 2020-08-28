package generale;

import database.DBMS;
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
 * Questa servlet intercetta le richieste relative alla pagina per recuperare la password.
 * @author Marco La Martina
 */
@WebServlet({ "/PasswordDimenticata", "/passworddimenticata"})
public class PasswordDimenticata extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String address = "/WEB-INF/generale/passwordDimenticata.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		dispatcher.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String email, errore;
			email = request.getParameter("email");
			errore = verificaEmail(email);
			
			if(errore!=null) { //sono stati inseriti dati scorretti
				request.getSession().setAttribute("notify",new Notify(2,"Errore",errore));
				response.sendRedirect(request.getContextPath());
				return;
			}
			
			Utente utente = DBMS.getUtente(email);
		
			if(utente==null) { // l'indirizzo email non è presente nel database
				request.getSession().setAttribute("email", email);
				request.getSession().setAttribute("notify",new Notify(2,"Errore","L'indirizzo email inserito non è associato ad un account"));
				response.sendRedirect(request.getContextPath()+"/PasswordDimenticata");
				return;
			}
			
			
			int token = 100000000 + (int)(Math.random() * ((999999999 - 100000000) + 1));
			DBMS.setToken(utente.getIdUtente(), token);
			String linkResetPass = Mailer.getAddress() + request.getContextPath() +"/ReimpostaPassword?id="
				+ utente.getIdUtente() + "&token=" + token;
			// Invia una mail di richiesta di reimpostazione della password
			String messaggio = "<h1>Reimposta password</h1> <p>Ciao " + utente.getNome() + " " + utente.getCognome()
				+ ", <br> abbiamo ricevuto una richiesta di reimpostazione della password associata al tuo account."
				+ " Per completare l'operazione <a href='" + linkResetPass + "'>clicca qui</a>.<br>"
				+ "Ti invitiamo a scegliere una password complessa per mantenere sicuro il tuo account."
				+ "<br>A presto!<br><br>Lo staff del lido</p>";
			
			Mailer mailer = new Mailer( email, "Lido Gorgo Beach - Richiesta recupero password", messaggio);
			Thread thread = new Thread(mailer);
			thread.start();
			
		   
		    request.getSession().setAttribute("notify",new Notify(1,"Completato","La mail è stata inviata con successo"));
			
			response.sendRedirect(request.getContextPath());
		    
		}catch(Exception e) {
			e.printStackTrace();
			request.getSession().setAttribute("notify",new Notify(2,"Errore","Abbiamo riscontrato un errore"));
			response.sendRedirect(request.getContextPath());
		}
	}
	
	/**
	 * Verifica che l'indirizzo email rispetti il formato richiesto.
	 * @param email
	 * @return errore
	 */
	public static String verificaEmail(String email) {
		String errore = null;
		
		if( email == null ) {
			return errore = "I campi sono tutti richiesti.";
		}
		
		if(email.replaceAll("\\s+","").contentEquals(""))
			return errore = "I campi sono tutti richiesti.";
		
		String regex = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+[.][a-zA-Z0-9-.]+$";
		if(!email.matches(regex))
			return errore = "L'email non rispetta il formato richiesto.";
		
		
		return errore;
	}

}