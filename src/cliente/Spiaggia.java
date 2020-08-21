package cliente;

import database.DBMS;
import model.*;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Questa servlet intercetta le richieste relative alla pagina di menu di cibo e bevande.
 * @author Marco La Martina
 */
@WebServlet({ "/Spiaggia", "/spiaggia"})
public class Spiaggia extends ClienteHome {
    private static final long serialVersionUID = 1L;


    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(checkSession(request, response)) {
            String address = "/WEB-INF/cliente/spiaggia.jsp";
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
                if(request.getParameter("posti")!= null && request.getParameter("sdraio")!=null && request.getParameter("date")!=null) {
                    int sdraio=Integer.parseInt(request.getParameter("sdraio"));
                    String[] postazioni = request.getParameter("posti").split("-");
                    LocalDate data=LocalDate.parse(request.getParameter("date"));
                    Account account=(Account)request.getSession().getAttribute("account");
                    Utente utente=account.getUtente();
                    if(sdraio>0){
                        if(!DBMS.prenotaSdraio(sdraio, data, utente.getIdUtente(), Prenotazione.getPrezzoSdraio())){

                            request.getSession().setAttribute("notify",new Notify(2,"Errore","Abbiamo riscontrato un errore"));
                            response.sendRedirect(request.getContextPath());
                            return;
                        }
                    }
                    if(request.getParameter("posti").length()>0){
                        for(String p:postazioni){
                            if(!DBMS.prenotaPostazione(Integer.parseInt(p), data, utente.getIdUtente(), Prenotazione.getPrezzoPostazione(data))){
                                request.getSession().setAttribute("notify",new Notify(2,"Errore","Abbiamo riscontrato un errore"));
                                response.sendRedirect(request.getContextPath());
                                return;
                            }
                        }
                    }


                    request.getSession().setAttribute("notify",new Notify(1,"Completato","Prenotazione effettuata con successo"));
                    response.sendRedirect(request.getContextPath()+"/ClienteHome");
                    return;
                }
                request.getSession().setAttribute("notify",new Notify(2,"Errore","Abbiamo riscontrato un errore"));
                response.sendRedirect(request.getContextPath());
                return;
            } catch(Exception e) {
                e.printStackTrace();
                request.getSession().setAttribute("notify",new Notify(2,"Errore","Abbiamo riscontrato un errore"));
                response.sendRedirect(request.getContextPath());
                return;
            }
        }
    }

}
