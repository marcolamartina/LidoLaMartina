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
import utils.Mailer;

/**
 * Questa servlet intercetta le richieste relative alla registrazione.
 * @author Marco La Martina
 */
@WebServlet("/Signin")
public class Signin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String address = "/WEB-INF/generale/signin.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String nome, cognome, email, password, confPassword, cellulare, errore;
			nome = request.getParameter("nome");
			cognome = request.getParameter("cognome");
			email = request.getParameter("email");
			password = request.getParameter("password");
			confPassword = request.getParameter("password_conf");
			cellulare = request.getParameter("cellulare");
			errore = verificaDatiRegistrazione(nome, cognome, email, password, confPassword, cellulare);
			
			if(errore!=null) { //sono stati inseriti dati scorretti
				request.getSession().setAttribute("notify",new Notify(2,"Errore",errore));
				response.sendRedirect(request.getContextPath());
				return;
			}
			
			int id = DBMS.getIDUtente(email);
		
			if(id!=-1) { // l'indirizzo email già esiste
				request.getSession().setAttribute("nome", nome);
				request.getSession().setAttribute("cognome", cognome);
				request.getSession().setAttribute("email", email);
				request.getSession().setAttribute("cellulare", cellulare);
				request.getSession().setAttribute("notify",new Notify(2,"Errore","L'indirizzo email inserito è già associato ad un altro account"));
				response.sendRedirect(request.getContextPath()+"/Signin");
				return;
			}
			DBMS.registraUtente(nome, cognome, email, password, cellulare);
			
			String messaggio = "<h1>Il Lido Gorgo Beach ti d&agrave; il benvenuto!</h1> <p>Ciao " + nome + " " + cognome + ", <br>"
					+ "ti comunichiamo che la registrazione del tuo account &egrave avvenuta con successo, "
					+ "<a href='"+ Mailer.getAddress() + request.getContextPath() + "'> visita il nostro sito</a> per usufruire dei nostri servizi."
					+ "<br>Ti auguriamo una buona permanenza nel nostro lido e speriamo di vederti presto! <br><br>"
					+ "Lo staff del lido</p>";
			Mailer mailer = new Mailer("smtp.gmail.com", "lidogorgobeach@gmail.com", "gupzeg-jundI2-muczig" , email, 
					"Lido Gorgo Beach - Benvenuto", messaggio);
			Thread thread = new Thread(mailer);
			thread.start();
			
		   
		    request.getSession().setAttribute("notify",new Notify(1,"Completato","Registrazione completata con successo"));
			
			response.sendRedirect(request.getContextPath());
		    
		}catch(Exception e) {
			e.printStackTrace();
			request.getSession().setAttribute("notify",new Notify(2,"Errore","Abbiamo riscontrato un errore"));
			response.sendRedirect(request.getContextPath());
		}
	}
	
	/**
	 * Verifica che i dati inseriti dall'utente in fase di registrazione non siano nulli e rispettino
	 * il formato richiesto.
	 * @param nome
	 * @param cognome
	 * @param email
	 * @param password
	 * @param confPassword
	 * @return errore
	 */
	public static String verificaDatiRegistrazione(String nome, String cognome, String email, String password, String confPassword, String cellulare) {
		String errore = null;
		
		if(nome == null || cognome == null || email == null || password == null || confPassword == null || cellulare == null) {
			return errore = "I campi sono tutti richiesti.";
		}
		
		if(nome.replaceAll("\\s+","").contentEquals("") || cellulare.replaceAll("\\s+","").contentEquals("") || cognome.replaceAll("\\s+","").contentEquals("") || 
				email.replaceAll("\\s+","").contentEquals("") || password.replaceAll("\\s+","").contentEquals(""))
			return errore = "I campi sono tutti richiesti.";
		
		String regex = "^[A-Za-zèùàòé][a-zA-Z'èùàòé ]*$";
		if(!nome.matches(regex))
			return errore = "Il nome non rispetta il formato richiesto.";
		
		if(!cognome.matches(regex))
			return errore ="Il cognome non rispetta il formato richiesto.";
		
		regex = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+[.][a-zA-Z0-9-.]+$";
		if(!email.matches(regex))
			return errore = "L'email non rispetta il formato richiesto.";
		
		regex = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}";
		if(!password.matches(regex))
			return errore = "La password non rispetta il formato richiesto.";
		
		if(!password.equals(confPassword))
			return errore ="Le password non corrispondono.";
		
		return errore;
	}
}
