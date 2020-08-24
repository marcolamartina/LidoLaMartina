package cliente;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DBMS;
import model.Notify;


/**
 * Questa servlet intercetta le richieste relative alla visualizzazione dei prodotti
 * @author Marco La Martina
 */
@WebServlet({"/VisualizzaProdotti", "/visualizzaprodotti"})
public class VisualizzaProdotti extends ClienteHome {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			try {
				if(request.getParameter("prodotti")!=null && request.getParameter("prodotti").contentEquals("true")) {
					response.setContentType("application/json");
					response.getWriter().println(DBMS.getProdotti());
					return;
				}
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			} catch(Exception e) {
				e.printStackTrace();
				request.getSession().setAttribute("notify",new Notify(2,"Errore","Abbiamo riscontrato un errore"));
				response.sendRedirect(request.getContextPath());
			}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
