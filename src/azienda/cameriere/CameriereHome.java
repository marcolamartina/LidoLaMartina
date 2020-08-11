package azienda.cameriere;

import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DBMS;
import model.Account;
import model.Comande;





/**
 * Questa servlet intercetta le richieste relative alla pagina di home del cameriere.
 * @author Marco La Martina
 */
@WebServlet({ "/CameriereHome", "/camerierehome"})
public class CameriereHome extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(checkSession(request, response)) {
			
			String address = "/WEB-INF/azienda/cameriere/cameriereHome.jsp";
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
				if(request.getParameter("idordinazione")!= null) {
					int idordinazione=Integer.parseInt(request.getParameter("idordinazione"));
					if(DBMS.setConsegnata(idordinazione)) {
						return;
					}
				}
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			} catch(Exception e) {
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
			if(account.getRuoli().containsKey("Cameriere")) {
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