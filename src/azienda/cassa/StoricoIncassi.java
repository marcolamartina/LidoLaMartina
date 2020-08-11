package azienda.cassa;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DBMS;


/**
 * Questa servlet intercetta le richieste relative alla pagina dello storico degli incassi.
 * @author Marco La Martina
 */
@WebServlet({ "/StoricoIncassi", "/storicoincassi"})
public class StoricoIncassi extends CassaHome {
	private static final long serialVersionUID = 1L;
       


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(checkSession(request, response)) {
			String address = "/WEB-INF/azienda/cassa/storicoIncassi.jsp";
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
				if(request.getParameter("data")==null) {
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					return;
				}
				LocalDate data=LocalDate.parse(request.getParameter("data"));
				response.setContentType("application/json");
				response.getWriter().println(DBMS.getStoricoIncassi(data));

			} catch(Exception e) {
				e.printStackTrace();
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
		}	
	}


}
