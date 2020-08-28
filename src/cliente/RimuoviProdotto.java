package cliente;


import model.Carrello;
import model.Notify;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Questa servlet intercetta le richieste relative alla rimozione dei prodotti al carrello
 * @author Marco La Martina
 */
@WebServlet({"/RimuoviProdotto", "/rimuoviprodotto"})
public class RimuoviProdotto extends ClienteHome {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(checkSession(request, response)) {
			try {
				if(request.getParameter("idprodotto")==null) {
					request.getSession().setAttribute("notify",new Notify(2,"Errore","Abbiamo riscontrato un errore"));
					response.sendRedirect(request.getContextPath());
					return;
				}
				int IDProdotto = Integer.parseInt(request.getParameter("idprodotto"));
				Carrello carrello = (Carrello) request.getSession().getAttribute("carrello");
				// Se il carrello non esiste c'è stato un errore.
				if(carrello == null) {
					request.getSession().setAttribute("notify",new Notify(2,"Errore","Abbiamo riscontrato un errore"));
					response.sendRedirect(request.getContextPath());
					return;
				}
				carrello.rimuoviProdotto(IDProdotto);
				request.getSession().setAttribute("carrello", carrello);
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