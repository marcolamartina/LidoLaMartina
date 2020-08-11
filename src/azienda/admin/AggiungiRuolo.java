package azienda.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DBMS;

/**
 * Questa servlet intercetta le richieste relative all'aggiunta di un ruolo da parte dell'admin.
 * @author Marco La Martina
 */
@WebServlet({ "/AggiungiRuolo", "/aggiungiruolo"})
public class AggiungiRuolo extends AdminHome {
	private static final long serialVersionUID = 1L;
       
   

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(checkSession(request, response)) {
			String address = "/WEB-INF/azienda/admin/aggiungiRuolo.jsp";
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
				
				if(request.getParameter("id")!= null && request.getParameter("ruolo")!=null) {
					int id=Integer.parseInt(request.getParameter("id"));
					String ruolo=request.getParameter("ruolo");
					if(DBMS.aggiungiRuolo(id, ruolo)) {
						return;
					}
				}else if(request.getParameter("nome")!= null && request.getParameter("cognome")!=null) {
					String nome=request.getParameter("nome");
					String cognome=request.getParameter("cognome");
					response.setContentType("application/json");
					response.getWriter().println(DBMS.searchAccount(nome, cognome)) ;
					return;	
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

}
