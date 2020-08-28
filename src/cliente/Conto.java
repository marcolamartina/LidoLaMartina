package cliente;

import database.DBMS;
import model.Account;
import model.Utente;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Questa servlet intercetta le richieste relative alla pagina del conto.
 * @author Marco La Martina
 */
@WebServlet({ "/Conto", "/conto"})
public class Conto extends ClienteHome {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(checkSession(request, response)) {
			String address = "/WEB-INF/cliente/conto.jsp";
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
				Account account=(Account)request.getSession().getAttribute("account");
				Utente utente=account.getUtente();
				int id = utente.getIdUtente();
				response.setContentType("application/json");
				response.getWriter().println(DBMS.getPagamenti(id, false));

			} catch(Exception e) {
				e.printStackTrace();
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
		}	
	}

}
