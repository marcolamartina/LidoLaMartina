package utils;

import model.Account;
import model.Utente;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;


/**
 * Questa servlet effettua il log dei dati ad ogni richiesta in arrivo.
 * @author Marco La Martina
 *
 */
@WebFilter("/Logger")
public class Logger implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	/**
	 * Scrive sul log l'ip di chi ha effettuato la richiesta, l'intero url richiesto ed il metodo
	 * della richiesta.
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;

		System.out.println("IP: " + req.getRemoteAddr());
		if(req.getSession().getAttribute("account")!=null){
			Account account=(Account)req.getSession().getAttribute("account");
			Utente utente=account.getUtente();
			System.out.println("user: " + utente.getNome() + " " + utente.getCognome() + " - " + utente.getEmail());
		}
		System.out.println("resource: " + req.getRequestURL());
		for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
			if (entry.getKey().compareTo("password_conf")!=0 &&  entry.getKey().compareTo("password")!=0) {
				System.out.print("param: "+entry.getKey()+"=");
				for(String s:entry.getValue()){
					System.out.print(s);
					if(entry.getValue().length>1)System.out.print(" - ");
				}
				System.out.println();
			}
		}
		System.out.println("Method: " + req.getMethod() + "\n");
		System.out.println("---------------------------------------------------------------------------------------------\n");
		System.out.flush();
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}