package azienda.reception;

import database.DBMS;
import model.Notify;
import model.Prenotazione;
import model.Utente;
import utils.Mailer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Questa servlet intercetta le richieste relative alla pagina per prenotare una postazione da parte del personale della reception.
 * @author Marco La Martina
 */
@WebServlet({ "/Prenota", "/prenota"})
public class Prenota extends ReceptionHome {
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
                if(request.getParameter("idutente")!= null && request.getParameter("posti")!= null && request.getParameter("sdraio")!=null && request.getParameter("date")!=null) {
                    int sdraio=Integer.parseInt(request.getParameter("sdraio"));
                    String[] postazioni = request.getParameter("posti").split("-");
                    LocalDate data=LocalDate.parse(request.getParameter("date"));
                    int IDUtente=Integer.parseInt(request.getParameter("idutente"));
                    if(sdraio>0){
                        if(!DBMS.prenotaSdraio(sdraio, data, IDUtente, Prenotazione.getPrezzoSdraio())){

                            request.getSession().setAttribute("notify",new Notify(2,"Errore","Abbiamo riscontrato un errore"));
                            response.sendRedirect(request.getContextPath()+"/ReceptionHome");
                            return;
                        }
                    }
                    if(request.getParameter("posti").length()>0){
                        for(String p:postazioni){
                            if(!DBMS.prenotaPostazione(Integer.parseInt(p), data, IDUtente, Prenotazione.getPrezzoPostazione(data))){
                                request.getSession().setAttribute("notify",new Notify(2,"Errore","Abbiamo riscontrato un errore"));
                                response.sendRedirect(request.getContextPath()+"/ReceptionHome");
                                return;
                            }
                        }
                    }

                    // Invia una mail di avvenuta prenotazione delle postazioni e/o sdraio.
                    DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd/MM/yyyy' alle ore 'HH:mm");
                    DateTimeFormatter formatter_data=DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    Utente utente=DBMS.getUtente(IDUtente);
                    String now= LocalDateTime.now().format(formatter);
                    String messaggio = "<h1>Prenotazione registrata con successo!</h1> <p>Gentile " + utente.getNome()
                            + " " + utente.getCognome() + ", <br>grazie per la tua prenotazione. Abbiamo ricevuto una richiesta"
                            + " di prenotazione di una postazione in data " + now + ". <br>Qui"
                            + " di seguito riportiamo i dettagli della prenotazione, visibili anche selezionando l'opzione \"Account"
                            + " -> Prenotazioni\" nel nostro sito.</p><h2>Dettagli prenotazione:</h2><p>"
                            + " Data prenotazione: " + data.format(formatter_data) + "<br><hr style='width:40%; float:left'></hr>";

                    if(request.getParameter("posti").length()>0){
                        messaggio+="<br>Numero postazioni: " + request.getParameter("posti") + "<br><hr style='width:40%; float:left'></hr>";
                    }
                    if(sdraio>0){
                        messaggio+="<br>Sdraio: " + sdraio + "<br><hr style='width:40%; float:left'></hr>";
                    }
                    messaggio+="<br><br>Lo staff del lido</p>";
                    Mailer mailer = new Mailer( utente.getEmail(), "Lido Gorgo Beach - Prenotazione", messaggio);
                    Thread thread = new Thread(mailer);
                    thread.start();

                    request.getSession().setAttribute("notify",new Notify(1,"Completato","Prenotazione effettuata con successo"));
                    response.sendRedirect(request.getContextPath()+"/ReceptionHome");
                    return;
                }
                request.getSession().setAttribute("notify",new Notify(2,"Errore","Abbiamo riscontrato un errore"));
                response.sendRedirect(request.getContextPath()+"/ReceptionHome");
                return;
            } catch(RuntimeException e) {
                request.getSession().setAttribute("notify",new Notify(0,"Informazione","Per la data selezionata il nostro lido è chiuso"));
                response.sendRedirect(request.getContextPath()+"/ReceptionHome");
                return;
            } catch(Exception e) {
                e.printStackTrace();
                request.getSession().setAttribute("notify",new Notify(2,"Errore","Abbiamo riscontrato un errore"));
                response.sendRedirect(request.getContextPath()+"/ReceptionHome");
                return;
            }
        }
    }


}