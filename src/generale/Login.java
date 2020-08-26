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
import model.Carrello;
import model.Notify;


/**
* Questa servlet intercetta le richieste relative al login.
* @author Marco La Martina
*/
@WebServlet({"/Login", "/login"})
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("account") == null) {
			String address = "/WEB-INF/generale/login.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(address);
			dispatcher.forward(request, response);
		}else {
			response.sendRedirect(request.getContextPath()+"/ClienteHome");
		}	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			// Se il cliente non è loggato avvia la procedura per il login.
			if(request.getSession().getAttribute("account") == null) {
				
				String email = request.getParameter("email");
				String password = request.getParameter("password");
				
				if(email == null || password == null) {
					request.getSession().setAttribute("notify",new Notify(2,"Errore","Credenziali non inserite correttamente"));
					response.sendRedirect(request.getContextPath()+"/Login");
					return;
				}
				// Verifica della validità dei dati del form di login.
				String errore = verificaDatiLoginCliente(email, password);
				if(errore != null) {
					request.getSession().setAttribute("notify",new Notify(2,"Errore",errore));
					response.sendRedirect(request.getContextPath()+"/Login");
					return;
				}
				// Verifica della corrispondenza dei dati con un account esistente.
				
				Account account = DBMS.login(email, password);
				// Se esiste una corrispondenza e il login avviene con successo, imposta l'attributo di sessione "account" e l'attributo di sessione "carrello".
				if(account != null) {
					request.getSession().setAttribute("account", account);
					if(request.getSession().getAttribute("carrello")==null) {
						request.getSession().setAttribute("carrello", new Carrello());
					}
					
					response.sendRedirect(request.getContextPath()+"/ClienteHome");
				}
				// Se non esiste una corrispondenza il client viene reindirizzato alla pagina di login.
				else {
					request.getSession().setAttribute("email", email);
					request.getSession().setAttribute("notify",new Notify(2,"Errore","Credenziali non corrette"));
					response.sendRedirect(request.getContextPath()+"/Login");
					return;
				}
			}
			// Se il cliente è loggato, lo reindirizza alla home.
			else {
				response.sendRedirect(request.getContextPath()+"/ClienteHome");
			}
			
			
		    
		}catch(Exception e) {
			e.printStackTrace();
			request.getSession().setAttribute("notify",new Notify(2,"Errore","Abbiamo riscontrato un errore"));
			response.sendRedirect(request.getContextPath());
		}
		
		
		
	}
	

	
	/**
	 * Verifica la validità dei campi email e password relativi al form di login dell'utente,
	 * in particolare verifica che non siano nulli e che l'email rispetti il formato richiesto. 
	 * @param email
	 * @param password
	 * @return errore
	 */
	public static String verificaDatiLoginCliente(String email, String password) {
		String errore = null;
		
		if(email.replaceAll("\\s+","").contentEquals("") || password.replaceAll("\\s+","").contentEquals(""))
			return errore = "Inserisci i tuoi dati per effettuare l'accesso.";
		
		String regex = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+[.][a-zA-Z0-9-.]+$";
		if(!email.matches(regex))
			return errore = "L'email non rispetta il formato richiesto.";
		
		return errore;
	}

}
