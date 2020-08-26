package azienda.cassa;

import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DBMS;
import model.Account;


/**
 * Questa servlet intercetta le richieste relative alla pagina per liberare una postazione.
 * @author Marco La Martina
 */
@WebServlet({ "/LiberaPostazione", "/liberapostazione"})
public class LiberaPostazione extends HttpServlet {
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
                if(request.getParameter("idprenotazione")!=null) {
                    int idprenotazione=Integer.parseInt(request.getParameter("idprenotazione"));
                    if(DBMS.liberaPostazione(idprenotazione)) {
                        return;
                    }

                }else if(request.getParameter("request")!=null) {
                    boolean flag=Boolean.parseBoolean(request.getParameter("request"));
                    if(flag) {
                        response.setContentType("application/json");
                        response.getWriter().println(DBMS.getPostazioniDaLiberare());
                        return;
                    }
                }
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;

            }catch(Exception e) {
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
    protected boolean checkSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession().getAttribute("account") != null ) {
            Account account=(Account)request.getSession().getAttribute("account");
            if(account.getRuoli().containsKey("Cassa") || account.getRuoli().containsKey("Reception")) {
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