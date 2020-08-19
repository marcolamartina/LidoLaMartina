package generale;

import azienda.cameriere.CameriereHome;
import database.DBMS;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;


/**
 * Questa servlet intercetta le richieste relative alla spiaggia.
 * @author Marco La Martina
 */
@WebServlet({ "/SpiaggiaMappa", "/spiaggiamappa"})
public class SpiaggiaMappa extends CameriereHome {
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
        try {
            if(request.getParameter("postazioni")!=null && request.getParameter("postazioni").compareTo("true")==0){
                response.setContentType("application/json");
                response.getWriter().println(DBMS.getPostazioni());
                return;
            } else if(request.getParameter("data")!=null){
                LocalDate data=LocalDate.parse(request.getParameter("data"));
                response.setContentType("application/json");
                response.getWriter().println(DBMS.getPrenotazioni(data));
                return;
            }
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
    }

}
