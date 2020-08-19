package azienda.bar;

import model.Account;
import model.Comande;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Questa servlet intercetta le richieste relative alla pagina di home dei dipendenti della cucina o del bar.
 * @author Marco La Martina
 */
@WebServlet({ "/BarHome", "/barhome"})
public class BarHome extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(checkSession(request, response)) {
			
			String address = "/WEB-INF/azienda/bar/barHome.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(address);
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
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
			if(account.getRuoli().containsKey("Bar")) {
				if(request.getSession().getAttribute("comande")==null) {
					request.getSession().setAttribute("comande", new Comande());
				}
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