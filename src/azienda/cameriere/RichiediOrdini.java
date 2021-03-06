package azienda.cameriere;

import database.DBMS;
import model.Account;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Questa servlet intercetta le richieste relative alle ordinazioni.
 * @author Marco La Martina
 */
@WebServlet({ "/RichiediOrdini", "/richiediordini"})
public class RichiediOrdini extends CameriereHome {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(checkSession(request, response)) {	
			try {
				if(request.getParameter("ruolo")!=null){
					if(request.getParameter("ruolo").compareTo("cucina")==0){
						response.setContentType("application/json");
						response.getWriter().println(DBMS.getOrdiniCucina());
						return;
					}else if(request.getParameter("ruolo").compareTo("bar")==0){
						response.setContentType("application/json");
						response.getWriter().println(DBMS.getOrdiniBar());
						return;
					}else if (request.getParameter("ruolo").compareTo("cameriere")==0){
						response.setContentType("application/json");
						response.getWriter().println(DBMS.getOrdiniCameriere());
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
	@Override
	protected boolean checkSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("account") != null ) {
			Account account=(Account)request.getSession().getAttribute("account");
			if(account.getRuoli().containsKey("Cameriere") || account.getRuoli().containsKey("Cucina-Bar")) {
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

