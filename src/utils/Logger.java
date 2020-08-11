package utils;

import java.io.IOException;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;


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
		System.out.println("resource: " + req.getRequestURL());
		for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
		    System.out.println("param: "+entry.getKey());
		}
		System.out.println("Method: " + req.getMethod() + "\n");
		System.out.println("---------------------------------------------------------------------------------------------\n");
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}