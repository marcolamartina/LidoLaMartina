package cliente;

import model.Notify;
import utils.JSONConverter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Questa servlet intercetta le richieste relative alla pagina del carrello.
 * @author Marco La Martina
 */
@WebServlet({ "/Carrello", "/carrello"})
public class Carrello extends ClienteHome {
	private static final long serialVersionUID = 1L;
       


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(checkSession(request, response)) {
			try {
				model.Carrello carrello=(model.Carrello)request.getSession().getAttribute("carrello");
				if(carrello==null) {
					request.getSession().setAttribute("notify",new Notify(2,"Errore","Abbiamo riscontrato un errore"));
					response.sendRedirect(request.getContextPath());
					return;
				}
				else if(request.getParameter("dimension")!= null && request.getParameter("dimension").compareTo("true")==0) {
					response.getWriter().println(carrello.numeroProdotti());
					return;
				}
				else if(request.getParameter("all")!=null && request.getParameter("all").compareTo("true")==0) {
					response.setContentType("application/json");
					response.getWriter().println(JSONConverter.carrelloToJSONArray(carrello));
					return;
				}
				else if(request.getParameter("totale")!=null && request.getParameter("totale").compareTo("true")==0) {
					response.getWriter().println(carrello.restituisciTotale());
					return;
				}
				else if(request.getParameter("idprodotto")!= null && request.getParameter("quantity")!= null) {
					int idprodotto=Integer.parseInt(request.getParameter("idprodotto"));
					carrello.setQuantity(idprodotto,Integer.parseInt(request.getParameter("quantity")));
					response.getWriter().println(carrello.searchProdotto(idprodotto).getPrezzo());
					return;
				}
				
				String address = "/WEB-INF/cliente/carrello.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(address);
				dispatcher.forward(request, response);
			} catch(Exception e) {
				e.printStackTrace();
				request.getSession().setAttribute("notify",new Notify(2,"Errore","Abbiamo riscontrato un errore"));
				response.sendRedirect(request.getContextPath());
			}	
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}