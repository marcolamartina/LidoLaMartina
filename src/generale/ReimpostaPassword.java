package generale;

import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DBMS;
import model.Notify;
import model.Utente;
import utils.Mailer;


/**
 * Questa servlet intercetta le richieste per reimpostare la password.
 * @author Marco La Martina
 */
@WebServlet({ "/ReimpostaPassword", "/reimpostapassword"})
public class ReimpostaPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			if(request.getParameter("id")!=null && request.getParameter("token")!=null) {
				int IDUtente = Integer.parseInt(request.getParameter("id"));
				int token = Integer.parseInt(request.getParameter("token"));
				request.getSession().setAttribute("email", DBMS.getEmail(IDUtente));
				if(!DBMS.verificaToken(IDUtente, token)) {
					response.sendError(404);
				} else {
					String address = "/WEB-INF/generale/reimpostaPassword.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(address);
					dispatcher.forward(request, response);
				}
			
			}else {
				response.sendError(401);
			}
		
		} catch(Exception e) {
			e.printStackTrace();
			request.getSession().setAttribute("notify",new Notify(2,"Errore","Abbiamo riscontrato un errore"));
			response.sendRedirect(request.getContextPath());
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			String password, password_conf, errore, id, token, nome, cognome, email;
			password = request.getParameter("password");
			password_conf = request.getParameter("password_conf");
			id=request.getParameter("id");
			token=request.getParameter("token");
			
			int flag=0;
			int IDUtente=Integer.parseInt(id);
			int tokenResetPassword=Integer.parseInt(token);
			Utente utente=DBMS.getUtente(IDUtente);
			nome=utente.getNome();
			cognome=utente.getCognome();
			email=utente.getEmail();
			
			errore = verificaDatiReimpostaPassword(password, password_conf);
			
			if(errore!=null) { //sono stati inseriti dati scorretti
				request.getSession().setAttribute("notify",new Notify(2,"Errore",errore));
				response.sendRedirect(request.getContextPath());
				return;
			}
			
			
			flag=DBMS.reimpostaPassword(IDUtente, password, tokenResetPassword);
			if(flag==0) {
				request.getSession().setAttribute("notify",new Notify(2,"Errore","Abbiamo riscontrato un errore"));
				response.sendRedirect(request.getContextPath());
				return;
			}
			
				
			String messaggio = "<h1>Lido Gorgo Beach</h1> <p>Ciao " + nome + " " + cognome + ", <br>"
					+ "ti comunichiamo che &egrave stata correttamente reimpostata la password, "
					+ "<a href=\"mailto:lidogorgobeach@gmail.com\">scrivici</a> se non sei stato tu a modificare la password."
					+ "<br>Ti auguriamo una buona permanenza nel nostro lido e speriamo di vederti presto! <br><br>"
					+ "Lo staff del lido</p>";
			Mailer mailer = new Mailer( email, "Lido Gorgo Beach - Password reimpostata", messaggio);
			Thread thread = new Thread(mailer);
			thread.start();
			
			request.getSession().setAttribute("notify",new Notify(1,"Completato","Password reimpostata con successo"));
			response.sendRedirect(request.getContextPath());
		    
		}catch(Exception e) {
			e.printStackTrace();
			request.getSession().setAttribute("notify",new Notify(2,"Errore","Abbiamo riscontrato un errore"));
			response.sendRedirect(request.getContextPath());
		}
	}

	
	/**
	 * Verifica che i dati inseriti dall'utente in fase di recupero della password siano corretti
	 * @param password
	 * @param password_conf
	 * @return errore
	 */
	public static String verificaDatiReimpostaPassword(String password, String password_conf) {
		String errore = null;
		
		if(password == null || password_conf == null ) {
			return errore = "I campi sono tutti richiesti.";
		}
		
		if(password_conf.replaceAll("\\s+","").contentEquals("") || password.replaceAll("\\s+","").contentEquals(""))
			return errore = "I campi sono tutti richiesti.";
		
		String regex = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}";
		if(!password.matches(regex))
			return errore = "La nuova password non rispetta il formato richiesto.";
		
		
		if(!password.equals(password_conf))
			return errore ="Le password non corrispondono.";
		
		return errore;
	}
}