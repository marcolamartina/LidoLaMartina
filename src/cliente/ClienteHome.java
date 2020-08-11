package cliente;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Account;


/**
 * Questa servlet intercetta le richieste relative alla pagina di home del cliente.
 * @author Marco La Martina
 */
@WebServlet({ "/ClienteHome", "/clientehome"})
public class ClienteHome extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(checkSession(request, response)) {
			
			String address = "/WEB-INF/cliente/clienteHome.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(address);
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	
	/**
	 * Verifica che la sessione sia valida per evitare di eseguire operazioni con privilegi senza
	 * essere loggati. Questo metodo viene ereditato dalle servlet che intercettano le varie operazioni.
	 * @param request
	 * @param response
	 * @return boolean
	 */
	protected boolean checkSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("account") == null ) {
			String address = "/WEB-INF/generale/login.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(address);
			dispatcher.forward(request, response);
			return false;
		}
		return true;
	}
}
