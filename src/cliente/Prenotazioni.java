package cliente;

import database.DBMS;
import model.Account;
import model.Utente;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Questa servlet intercetta le richieste relative alla pagina del conto.
 * @author Marco La Martina
 */
@WebServlet({ "/Prenotazioni", "/prenotazioni"})
public class Prenotazioni extends ClienteHome {
    private static final long serialVersionUID = 1L;


    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(checkSession(request, response)) {
            String address = "/WEB-INF/cliente/prenotazioni.jsp";
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
                Account account=(Account)request.getSession().getAttribute("account");
                Utente utente=account.getUtente();
                if(request.getParameter("all")!=null && request.getParameter("all").compareTo("true")==0){
                    response.setContentType("application/json");
                    response.getWriter().println(DBMS.getPrenotazioniUtente(utente.getIdUtente()));
                    return;
                }
                if(request.getParameter("idprenotazione")!=null && request.getParameter("idconto")!=null){
                    int IDPrenotazione=Integer.parseInt(request.getParameter("idprenotazione"));
                    int IDConto=Integer.parseInt(request.getParameter("idconto"));
                    if(!DBMS.rimuoviPrenotazione(IDPrenotazione, utente.getIdUtente(), IDConto))response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
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
