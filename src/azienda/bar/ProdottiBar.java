package azienda.bar;

import database.DBMS;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Questa servlet intercetta le richieste relative alla pagina che consente ad un cameriere di prendere le ordinazioni per un cliente.
 * @author Marco La Martina
 */
@WebServlet({ "/ProdottiBar", "/prodottibar"})
public class ProdottiBar extends BarHome {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		if(checkSession(request, response)) {	
			String address = "/WEB-INF/azienda/bar/prodottiBar.jsp";
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

				if(request.getParameter("prodotti")!=null && request.getParameter("prodotti").contentEquals("true")) {
					response.setContentType("application/json");
					response.getWriter().println(DBMS.getAllProdottiBar());
					return;
				}
				else if(request.getParameter("id")!=null && request.getParameter("flag")!=null) {
					int idProdotto=Integer.parseInt(request.getParameter("id"));
					boolean flag=Boolean.parseBoolean(request.getParameter("flag"));
					DBMS.setDisponibile(idProdotto, flag);
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
