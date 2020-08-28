package azienda.cameriere;

import model.Account;
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
 * Questa servlet intercetta le richieste relative alla pagina che consente ad un cameriere di prendere le ordinazioni per un cliente.
 * @author Marco La Martina
 */
@WebServlet({ "/Comande", "/comande"})
public class Comande extends CameriereHome {
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
				model.Comande comande=(model.Comande)request.getSession().getAttribute("comande");
				if(comande==null) {
					request.getSession().setAttribute("notify",new Notify(2,"Errore","Abbiamo riscontrato un errore"));
					response.sendRedirect(request.getContextPath());
					return;
				}
				
				else if(request.getParameter("idprodotto")!= null && request.getParameter("idutente")!= null && request.getParameter("rimuovi")!= null && request.getParameter("rimuovi").compareTo("true")==0) {
					int idutente=Integer.parseInt(request.getParameter("idutente"));
					int idprodotto=Integer.parseInt(request.getParameter("idprodotto"));
					comande.rimuoviProdotto(idprodotto, idutente);
					return;
				}
				else if(request.getParameter("all")!=null && request.getParameter("all").compareTo("true")==0) {
					response.setContentType("application/json");
					response.getWriter().println(JSONConverter.comandeToJSONArray(comande));
					return;
				}
				else if(request.getParameter("idutente")!= null && request.getParameter("totale")!=null && request.getParameter("totale").compareTo("true")==0) {
					int idutente=Integer.parseInt(request.getParameter("idutente"));
					response.getWriter().println(comande.get(idutente).restituisciTotale());
					return;
				}
				else if(request.getParameter("idutente")!= null && request.getParameter("idprodotto")!= null && request.getParameter("quantity")!= null) {
					int idprodotto=Integer.parseInt(request.getParameter("idprodotto"));
					int idutente=Integer.parseInt(request.getParameter("idutente"));
					comande.get(idutente).setQuantity(idprodotto,Integer.parseInt(request.getParameter("quantity")));
					response.getWriter().println(comande.get(idutente).searchProdotto(idprodotto).getPrezzo());
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