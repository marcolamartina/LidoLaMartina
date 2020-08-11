package azienda.cassa;

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
import model.Account;
import model.Utente;
import utils.Mailer;




/**
 * Questa servlet intercetta le richieste relative alla pagina di home del cassiere.
 * @author Marco La Martina
 */
@WebServlet({ "/CassaHome", "/cassahome"})
public class CassaHome extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(checkSession(request, response)) {
			
			String address = "/WEB-INF/azienda/cassa/cassaHome.jsp";
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
				
				if(request.getParameter("id")==null || request.getParameter("sconto")==null || request.getParameter("supplemento")==null|| request.getParameter("totale")==null) {
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					return;
				}
				int IDConto=Integer.parseInt(request.getParameter("id"));
				double sconto=Double.parseDouble(request.getParameter("sconto"));
				double supplemento=Double.parseDouble(request.getParameter("supplemento"));
				double totale=Double.parseDouble(request.getParameter("totale"));
				if(!DBMS.paga(IDConto, sconto, supplemento, totale)) {
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					return;
				}
				
				Utente utente=DBMS.getUtenteFromConto(IDConto); 
				DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd/MM/yyyy' alle ore 'HH:mm");
		        String now=LocalDateTime.now().format(formatter);
				String messaggio = "<h1>Pagamento avvenuto con successo!</h1> <p>Gentile " + utente.getNome()
					+ " " + utente.getCognome() + ", <br>grazie per il tuo acquisto. Abbiamo ricevuto"
					+ " il pagamento in data " + now 
					+ ". Potrai sempre visionare i dettagli del pagamento dalla sezione \"Storico pagamenti\"."
					+ "<br><br>Lo staff del lido</p>";
				Mailer mailer = new Mailer("smtp.gmail.com", "lidogorgobeach@gmail.com", "gupzeg-jundI2-muczig" , utente.getEmail(), 
						"Lido Gorgo Beach - Conferma pagamento", messaggio);
				Thread thread = new Thread(mailer);
				thread.start();
			
				
			}catch(Exception e) {
				e.printStackTrace();
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}	
		}	
	}

	
	/**
	 * Verifica che la sessione sia valida per evitare di eseguire operazioni con privilegi senza
	 * essere loggati. Questo metodo viene ereditato dalle servlet che intercettano le varie operazioni.
	 * @param request
	 * @param response
	 * @return boolean
	 */
	protected boolean checkSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("account") != null ) {
			Account account=(Account)request.getSession().getAttribute("account");
			if(account.getRuoli().containsKey("Cassa")) {
				return true;
			}else {
				response.sendError(401);
				return false;
			}
		}else {
			String address = "/WEB-INF/generale/login.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(address);
			dispatcher.forward(request, response);
			return false;
		}

	}
}
