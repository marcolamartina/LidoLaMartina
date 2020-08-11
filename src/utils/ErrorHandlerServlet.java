package utils;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Questa servlet si occupa di gestire gli errori. Ogni volta che viene generato un 
 * errore la richiesta viene smistata a questa servlet, la quale provvede a 
 * recuperarne i dettagli e successivamente informare l'utente mostrando una pagina
 * di errore.
 * @author Marco La Martina
 */
@WebServlet("/Errore")
public class ErrorHandlerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		request.setAttribute("codiceDiStato", statusCode);
		response.setStatus(statusCode);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/utils/Errore.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
