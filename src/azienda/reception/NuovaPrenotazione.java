package azienda.reception;

import java.io.IOException;
import java.time.LocalDate;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DBMS;
import model.Account;
import model.Notify;
import model.Prenotazione;
import model.Utente;


/**
 * Questa servlet intercetta le richieste relative alla pagina per prenotare una postazione da parte del personale della reception.
 * @author Marco La Martina
 */
@WebServlet({ "/NuovaPrenotazione", "/nuovaprenotazione"})
public class NuovaPrenotazione extends ReceptionHome {
    private static final long serialVersionUID = 1L;



    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(checkSession(request, response)) {
            String address = "/WEB-INF/azienda/reception/nuovaPrenotazione.jsp";
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
                if(request.getParameter("nome")!= null && request.getParameter("cognome")!=null) {
                    String nome=request.getParameter("nome");
                    String cognome=request.getParameter("cognome");
                    response.setContentType("application/json");
                    response.getWriter().println(DBMS.searchClient(nome, cognome)) ;
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