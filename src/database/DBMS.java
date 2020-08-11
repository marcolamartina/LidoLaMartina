package database;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.JSONConverter;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



/**
 * Questa classe modella la connessione con il database. Gestisce tutti i metodi che 
 * interagiscono con il database, consentendo di effettuare interrogazioni per inserire, 
 * cancellare, aggiornare o ritornare i dati delle tabelle.
 * @author Marco La Martina
 */
public class DBMS {
	
	/**
	 * Dichiarazione delle variabili necessarie a gestire la connessione con il database e
	 * l'esecuzione delle query.
	 */
	private static Context context = null;
	private static DataSource dataSource = null;
	private static Connection connection = null;
	private static PreparedStatement statement = null;
	private static ResultSet result = null;
	
	/**
	 * Inizializza il context ed il data source per la comunicazione con il database.
	 */
	static {
		try {
			context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/LidoLaMartina");
			}
		catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Inizia una connessione con il database.
	 * @throws SQLException
	 */
	public static void startConnection() throws SQLException {
		connection = dataSource.getConnection();
	}
	
	/**
	 * Chiude il ResultSet (se contiene dei valori), il PreparedStatement e la connessione
	 * con il database.
	 * @throws SQLException
	 */
	public static void closeConnection() throws SQLException {
		if(result != null)
			result.close();
		statement.close();
		connection.close();
	}
	
	
	
	/**
	 * Inserisce all'interno del database i dati del cliente che effettua la registrazione 
	 * @param nome
	 * @param cognome
	 * @param email
	 * @param password
	 */
	public static void registraUtente(String nome, String cognome, String email, String password, String cellulare) throws SQLException{
		String query1 = "INSERT INTO Utente (Nome, Cognome, Email, Password, Cellulare) " + 
					    "VALUES (?, ?, ?, MD5(?), ?)";
		startConnection();
		statement = connection.prepareStatement(query1);
		statement.setString(1, nome);
		statement.setString(2, cognome);
		statement.setString(3, email);
		statement.setString(4, password);
		statement.setString(5, cellulare);
		statement.executeUpdate();
		closeConnection(); 
			
	}
	
	/**
	 * Inserisce all'interno del database i dati del cliente che effettua la registrazione 
	 * @param nome
	 * @param cognome
	 * @param email
	 * @param password
	 */
	public static int getIDUtente(String email) throws SQLException{
		String query1 = "SELECT IDUtente FROM Utente WHERE Email=?";
		int id=-1;
		startConnection();
		statement = connection.prepareStatement(query1);
		statement.setString(1, email);
		result = statement.executeQuery();
		if(result.next()) {
			id=result.getInt("IDUtente");
		}
		closeConnection(); 
		return id;
		
	}
	
	/**
	 * Effettua il login di un utente 
	 * @param email
	 * @param password
	 */
	public static Account login(String email, String password) throws SQLException{
		Account account=null;
		Utente utente=null;
		Map<String, Integer> ruoli=new LinkedHashMap<String, Integer>(); 
		String query1 = "SELECT IDUtente, Nome, Cognome, Email, Cellulare FROM Utente WHERE Email=? AND Password=MD5(?)";
		startConnection();
		statement = connection.prepareStatement(query1);
		statement.setString(1, email);
		statement.setString(2, password);
		result = statement.executeQuery();
		if(result.next()) {
			utente=new Utente(result.getInt("IDUtente"), result.getString("Nome"), result.getString("Cognome"), result.getString("Email"), result.getString("Cellulare"));
		}else {
			closeConnection();
			return null;
		}
		ruoli.put("Cliente", utente.getIdUtente());
		String query2="SELECT IDAccountAziendale, Ruolo FROM AccountAziendale WHERE Utente_IDUtente=?";
		statement = connection.prepareStatement(query2);
		statement.setInt(1, utente.getIdUtente());
		result = statement.executeQuery();
		
		//ciclo per creare la mappa dei ruoli
		while(result.next()) {
			ruoli.put(result.getString("Ruolo"), result.getInt("IDAccountAziendale"));
		}
		account=new Account(utente, ruoli);
		closeConnection(); 
		return account;
		
	}
	
	
	/**
	 * Modifica il nome dell'utente
	 * @param IDUtente
	 * @param nome
	 */
	public static void aggiornaNome(int IDUtente, String nome) throws SQLException{
		String query = "UPDATE Utente SET Nome=? WHERE IDUtente=?";
		startConnection();
		statement = connection.prepareStatement(query);
		statement.setString(1, nome);
		statement.setInt(2, IDUtente);
		statement.executeUpdate();
		closeConnection(); 
			
	}
	
	/**
	 * Modifica il cognome dell'utente
	 * @param IDUtente
	 * @param cognome
	 */
	public static void aggiornaCognome(int IDUtente, String cognome) throws SQLException{
		String query = "UPDATE Utente SET Cognome=? WHERE IDUtente=?";
		startConnection();
		statement = connection.prepareStatement(query);
		statement.setString(1, cognome);
		statement.setInt(2, IDUtente);
		statement.executeUpdate();
		closeConnection(); 
			
	}

	/**
	 * Modifica l'indirizzo email dell'utente
	 * @param IDUtente
	 * @param email
	 */
	public static void aggiornaEmail(int IDUtente, String email) throws SQLException{
		String query = "UPDATE Utente SET Email=? WHERE IDUtente=?";
		startConnection();
		statement = connection.prepareStatement(query);
		statement.setString(1, email);
		statement.setInt(2, IDUtente);
		statement.executeUpdate();
		closeConnection(); 
			
	}
	
	/**
	 * Modifica il cellulare dell'utente
	 * @param IDUtente
	 * @param cellulare
	 */
	public static void aggiornaCellulare(int IDUtente, String cellulare) throws SQLException{
		String query = "UPDATE Utente SET Cellulare=? WHERE IDUtente=?";
		startConnection();
		statement = connection.prepareStatement(query);
		statement.setString(1, cellulare);
		statement.setInt(2, IDUtente);
		statement.executeUpdate();
		closeConnection(); 
			
	}
	
	/**
	 * Ritorna i dati dell'utente tramite il suo ID
	 * @param IDUtente
	 */
	public static Utente getUtente(int IDUtente) throws SQLException{
		Utente utente=null;
		String query = "SELECT IDUtente, Nome, Cognome, Email, Cellulare FROM Utente WHERE IDUtente=?";
		startConnection();
		statement = connection.prepareStatement(query);
		statement.setInt(1, IDUtente);
		result = statement.executeQuery();
		if(result.next()) {
			utente=new Utente(result.getInt("IDUtente"), result.getString("Nome"), result.getString("Cognome"), result.getString("Email"), result.getString("Cellulare"));
		}
		closeConnection(); 
		return utente;
			
	}
	
	/**
	 * Ritorna i dati dell'utente tramite l'ID di un conto a lui associato
	 * @param IDUtente
	 */
	public static Utente getUtenteFromConto(int IDConto) throws SQLException{
		Utente utente=null;
		String query = "SELECT IDUtente, Nome, Cognome, Email, Cellulare FROM Utente JOIN Conto ON Conto.Ref_IDUtente=Utente.IDUtente WHERE IDConto=?";
		startConnection();
		statement = connection.prepareStatement(query);
		statement.setInt(1, IDConto);
		result = statement.executeQuery();
		if(result.next()) {
			utente=new Utente(result.getInt("IDUtente"), result.getString("Nome"), result.getString("Cognome"), result.getString("Email"), result.getString("Cellulare"));
		}
		closeConnection(); 
		return utente;
			
	}
	
	/**
	 * Ritorna i dati dell'utente tramite il suo indirizzo email
	 * @param IDUtente
	 */
	public static Utente getUtente(String email) throws SQLException{
		Utente utente=null;
		String query = "SELECT IDUtente, Nome, Cognome, Email, Cellulare FROM Utente WHERE Email=?";
		startConnection();
		statement = connection.prepareStatement(query);
		statement.setString(1, email);
		result = statement.executeQuery();
		if(result.next()) {
			utente=new Utente(result.getInt("IDUtente"), result.getString("Nome"), result.getString("Cognome"), result.getString("Email"), result.getString("Cellulare"));
		}
		closeConnection(); 
		return utente;
			
	}
	
	/**
	 * Ritorna l'indirizzo email dell'utente tramite il suo ID
	 * @param IDUtente
	 * @return email
	 */
	public static String getEmail(int IDUtente) throws SQLException{
		String email=null;
		String query = "SELECT Email FROM Utente WHERE IDUtente=?";
		startConnection();
		statement = connection.prepareStatement(query);
		statement.setInt(1, IDUtente);
		result = statement.executeQuery();
		if(result.next()) {
			email=result.getString("Email");
		}
		closeConnection(); 
		return email;
			
	}
	
	/**
	 * Modifica la password dell'utente
	 * @param IDUtente
	 * @param password
	 * @param password_attuale
	 */
	public static int aggiornaPassword(int IDUtente, String password, String password_attuale) throws SQLException{
		String query = "UPDATE Utente SET Password=MD5(?) WHERE IDUtente=? AND Password=MD5(?)";
		startConnection();
		statement = connection.prepareStatement(query);
		statement.setString(1, password);
		statement.setInt(2, IDUtente);
		statement.setString(3, password_attuale);
		int result=statement.executeUpdate();
		closeConnection(); 
		
		return result;	
	}
	

	/**
	 * Setta il token generato randomicamente per reimpostare la password dell'utente 
	 * @param idCliente
	 * @param token
	 */
	public static void setToken(int idCliente, int token) throws SQLException{
		String query = "UPDATE Utente SET TokenResetPassword = ? WHERE IDUtente = ?";
		startConnection();
		statement = connection.prepareStatement(query);
		statement.setInt(1, token);
		statement.setInt(2, idCliente);
		statement.executeUpdate();
		closeConnection();
		
	}
	
	/**
	 * Verifica se l'id ed il token dell'utente che richiede la reimpostazione della password
	 * sono valori validi 
	 * @param idCliente
	 * @param token
	 * @return flag
	 */
	public static boolean verificaToken(int idCliente, int token) throws SQLException{
		boolean flag = false;
		String query = "SELECT TokenResetPassword FROM Utente WHERE IDUtente = ? ";

		startConnection();
		statement = connection.prepareStatement(query);
		statement.setInt(1, idCliente);
		result = statement.executeQuery();
		if(result.next()) {
			flag = result.getInt("TokenResetPassword")==token;
		}
		closeConnection();
		return flag;
	}
	
	/**
	 * Reimposta la password dell'utente
	 * @param IDUtente
	 * @param password
	 * @param token
	 */
	public static int reimpostaPassword(int IDUtente, String password, int tokenResetPassword) throws SQLException{
		String query1 = "UPDATE Utente SET Password=MD5(?) WHERE IDUtente=? AND TokenResetPassword=?";
		startConnection();
		statement = connection.prepareStatement(query1);
		statement.setString(1, password);
		statement.setInt(2, IDUtente);
		statement.setInt(3, tokenResetPassword);
		int result=statement.executeUpdate();
		if(result!=0) {
			String query2 = "UPDATE Utente SET TokenResetPassword=0 WHERE IDUtente=? " ;
			statement = connection.prepareStatement(query2);
			statement.setInt(1, IDUtente);
			statement.executeUpdate();
		}
		closeConnection(); 
		
		return result;	
	}

	/**
	 * Ritorna il JSONArray contenente le categorie dei prodotti disponibili
	 * @return categorie
	 */
	public static JSONArray getCategorie() throws SQLException{
		JSONArray categorie=null;
		String query = "SELECT DISTINCT Categoria FROM Prodotto WHERE Disponibile=true";
		startConnection();
		statement = connection.prepareStatement(query);
		result = statement.executeQuery();
		categorie = JSONConverter.convertToJSONArray(result);
		closeConnection(); 
		return categorie;
			
	}
	
	/**
	 * Ritorna il JSONArray contenente le categorie dei prodotti
	 * @return categorie
	 */
	public static JSONArray getAllCategorie() throws SQLException{
		JSONArray categorie=null;
		String query = "SELECT DISTINCT Categoria FROM Prodotto";
		startConnection();
		statement = connection.prepareStatement(query);
		result = statement.executeQuery();
		categorie = JSONConverter.convertToJSONArray(result);
		closeConnection(); 
		return categorie;
			
	}
	
	/**
	 * Ritorna il JSONArray contenente i prodotti di una categoria
	 * @param categoria
	 * @return prodotti
	 */
	public static JSONArray getProdotti(String categoria) throws SQLException{
		JSONArray prodotti=null;
		String query = "SELECT * FROM Prodotto WHERE Categoria=? AND Disponibile=true";
		startConnection();
		statement = connection.prepareStatement(query);
		statement.setString(1, categoria);
		result = statement.executeQuery();
		prodotti = JSONConverter.convertToJSONArray(result);
		closeConnection(); 
		return prodotti;
			
	}

	/**
	 * Ritorna il JSONArray contenente tutti i prodotti disponibili
	 * @return prodotti
	 */
	public static JSONArray getProdotti() throws SQLException{
		JSONArray prodotti=null;
		String query = "SELECT * FROM Prodotto WHERE Disponibile=true";
		startConnection();
		statement = connection.prepareStatement(query);
		result = statement.executeQuery();
		prodotti = JSONConverter.convertToJSONArray(result);
		closeConnection(); 
		return prodotti;
			
	}
	
	/**
	 * Ritorna il JSONArray contenente tutti i prodotti 
	 * @return prodotti
	 */
	public static JSONArray getAllProdotti() throws SQLException{
		JSONArray prodotti=null;
		String query = "SELECT * FROM Prodotto";
		startConnection();
		statement = connection.prepareStatement(query);
		result = statement.executeQuery();
		prodotti = JSONConverter.convertToJSONArray(result);
		closeConnection(); 
		return prodotti;
			
	}
	
	/**
	 * Ritorna il prodotto relativo all'IDProdotto
	 * @param IDProdotto
	 * @return prodotto
	 */
	public static Prodotto getProdotto(int IDProdotto) throws SQLException{
		Prodotto prodotto=null;
		String query = "SELECT * FROM Prodotto WHERE Disponibile=true AND IDProdotto=?";
		startConnection();
		statement = connection.prepareStatement(query);
		statement.setInt(1, IDProdotto);
		result = statement.executeQuery();
		if(result.next()) {
			prodotto=new Prodotto(result.getInt("IDProdotto"), result.getString("Nome"), result.getString("Categoria"),result.getString("Ingredienti"),result.getDouble("Prezzo"),result.getBoolean("Disponibile"));
		}
		closeConnection(); 
		return prodotto;
			
	}
	
	/**
	 * Inserisce una nuova ordinazione, associandola ad un conto che può essere creato se ancora non è stato fatto
	 * @param carrello
	 * @param IDUtente
	 */
	public static void inserisciOrdinazione(Carrello carrello, int IDUtente, int tavolo) throws SQLException{
		//Verifica che esista già un conto non ancora pagato per la data corrente associata all'utente
		Date data=Date.valueOf(LocalDate.now().toString());
		int IDConto=0;
		String query1 = "SELECT IDConto FROM Conto WHERE Data=? AND Pagato=false AND Ref_IDUtente=?";
		startConnection();
		statement = connection.prepareStatement(query1);
		statement.setDate(1, data);
		statement.setInt(2, IDUtente);
		result = statement.executeQuery();
		if(result.next()) {
			IDConto=result.getInt("IDConto");
		}else {
			String query2 = "INSERT INTO Conto (Data, Pagato, Ref_IDUtente) VALUES (?, false, ?)";
			statement = connection.prepareStatement(query2);
			statement.setDate(1, data);
			statement.setInt(2, IDUtente);
			statement.executeUpdate();
			
			statement = connection.prepareStatement(query1);
			statement.setDate(1, data);
			statement.setInt(2, IDUtente);
			result = statement.executeQuery();
			if(result.next()) {
				IDConto=result.getInt("IDConto");
			}
		}
		for (Map.Entry<Prodotto, Ordinazione> entry : carrello.getProdotti().entrySet()) {
			String query3 = "INSERT INTO Ordinazione (Quantita, Consegnata, Ref_IDConto, Tavolo, Prodotto_IDProdotto, Note)"+
					"VALUES (?, false, ?, ?, ?, ?)";
			statement = connection.prepareStatement(query3);
			statement.setInt(1, entry.getValue().getQuantita());
			statement.setInt(2, IDConto);
			statement.setInt(3, tavolo);
			statement.setInt(4, entry.getKey().getIdProdotto());
			statement.setString(5, entry.getValue().getNote());
			statement.executeUpdate();
		}
		
		closeConnection();
	}
	
	/**
	 * Ritorna un JSONArray contenente i dati dei pagamenti
	 * @param IDUtente
	 * @param tipo
	 * @param periodo
	 * @param pagato
	 * @return pagamenti
	 */
	public static JSONArray getPagamenti(int IDUtente, boolean pagato) throws SQLException{
		JSONArray pagamenti=new JSONArray();
		JSONArray ordinazioni=null;
		JSONArray prenotazioni=null;
		List <Date> conti=new ArrayList<>();
		String query, query1, query2;

		query = "SELECT DISTINCT Data FROM Conto WHERE Pagato=? AND Ref_IDUtente=? ORDER BY Data DESC";
		startConnection();
		statement = connection.prepareStatement(query);
		statement.setBoolean(1, pagato);
		statement.setInt(2, IDUtente);
		result = statement.executeQuery();
		while(result.next()) {
			conti.add(result.getDate("Data"));
		}
		result.close(); 
		
		
		for(Date data : conti) {
			query1 = "SELECT IDOrdinazione, Quantita, IDConto, Data, Totale, Nome, Prezzo, Categoria FROM Ordinazione INNER JOIN Conto ON Conto.IDConto=Ordinazione.Ref_IDConto INNER JOIN Prodotto ON Ordinazione.Prodotto_IDProdotto=Prodotto.IDProdotto WHERE Data=? AND Pagato=? AND Ref_IDUtente=?";
			statement = connection.prepareStatement(query1);
			statement.setDate(1, data);
			statement.setBoolean(2, pagato);
			statement.setInt(3, IDUtente);
			result = statement.executeQuery();
			ordinazioni = JSONConverter.convertToJSONArray(result);
			result.close();
			
			
			query2 = "SELECT IDPrenotazione, Conto.Data AS Data, IDConto, Sdraio, Prezzo, Postazione_Numero, Totale FROM Prenotazione INNER JOIN Conto ON Conto.IDConto=Prenotazione.Conto_IDConto WHERE Prenotazione.Data=? AND Pagato=? AND Utente_IDUtente=?";
			statement = connection.prepareStatement(query2);
			statement.setDate(1, data);
			statement.setBoolean(2, pagato);
			statement.setInt(3, IDUtente);
			result = statement.executeQuery();
			prenotazioni = JSONConverter.convertToJSONArray(result);
			result.close();
			pagamenti.put(JSONConverter.contoToJSONArray(ordinazioni,prenotazioni));
		}
		
		closeConnection(); 
		return pagamenti;
			
	}
	
	
	/**
	 * Ritorna un JSONArray contenente i dati degli account aziendali
	 * @return accounts
	 */
	public static JSONArray getAccountAziendali() throws SQLException{
		JSONArray accounts=null;
		String query = "SELECT IDAccountAziendale, Ruolo, IDUtente, Nome, Cognome, Email, Cellulare FROM AccountAziendale INNER JOIN Utente ON AccountAziendale.Utente_IDUtente=Utente.IDUtente ORDER BY Cognome, Nome ASC";
		startConnection();
		statement = connection.prepareStatement(query);
		result = statement.executeQuery();
		accounts = JSONConverter.convertToJSONArray(result);
		closeConnection(); 
		return accounts;
			
	}
	
	
	/**
	 * Rimuove un account aziendale e ritorna una flag che indica se la query è andata a buon fine
	 * @param IDUtente
	 * @param ruolo
	 * @throws SQLException
	 */
	public static boolean rimuoviRuolo(int IDUtente, String ruolo) throws SQLException{
		String query = "DELETE FROM AccountAziendale WHERE Utente_IDUtente=? AND Ruolo=?";
		startConnection();
		statement = connection.prepareStatement(query);
		statement.setInt(1, IDUtente);
		statement.setString(2, ruolo);
		int result=statement.executeUpdate();
		closeConnection(); 
		
		return result>0;

	}
	
	/**
	 * Aggiunge un account aziendale e ritorna una flag che indica se la query è andata a buon fine
	 * @param IDUtente
	 * @param ruolo
	 * @throws SQLException
	 */
	public static boolean aggiungiRuolo(int IDUtente, String ruolo) throws SQLException{
		String query = "INSERT INTO AccountAziendale (Utente_IDUtente, Ruolo) VALUES (?, ?)";
		startConnection();
		statement = connection.prepareStatement(query);
		statement.setInt(1, IDUtente);
		statement.setString(2, ruolo);
		int result=statement.executeUpdate();
		closeConnection(); 
		
		return result>0;	
	}
	
	/**
	 * Ritorna un JSONArray contenente i dati degli account 
	 * @param nome
	 * @param cognome
	 * @return accounts
	 * @throws SQLException
	 */
	public static JSONArray searchAccount(String nome, String cognome) throws SQLException{
		JSONArray accounts=null;
		String query = "SELECT IDUtente, Nome, Cognome, Email, Cellulare FROM Utente LEFT JOIN AccountAziendale ON AccountAziendale.Utente_IDUtente=Utente.IDUtente WHERE Nome=? AND Cognome=? AND IDAccountAziendale IS NULL";
		startConnection();
		statement = connection.prepareStatement(query);
		statement.setString(1, nome);
		statement.setString(2, cognome);
		result = statement.executeQuery();
		accounts = JSONConverter.convertToJSONArray(result);
		closeConnection(); 
		return accounts;
			
	}
	
	/**
	 * Ritorna un JSONArray contenente i dati dei clienti 
	 * @param nome
	 * @param cognome
	 * @return accounts
	 * @throws SQLException
	 */
	public static JSONArray searchClient(String nome, String cognome) throws SQLException{
		JSONArray accounts=null;
		String query = "SELECT IDUtente, Nome, Cognome, Email, Cellulare FROM Utente WHERE Nome=? AND Cognome=?";
		startConnection();
		statement = connection.prepareStatement(query);
		statement.setString(1, nome);
		statement.setString(2, cognome);
		result = statement.executeQuery();
		accounts = JSONConverter.convertToJSONArray(result);
		closeConnection(); 
		return accounts;
			
	}
	
	/**
	 * Ritorna un JSONArray contenente i dati dei pagamenti
	 * @param IDUtente
	 * @param tipo
	 * @param periodo
	 * @param pagato
	 * @return JSONconti
	 */
	public static JSONArray getConti(boolean pagato) throws SQLException{
		JSONArray JSONconti=new JSONArray();
		Map <Integer, List<Date>> conti=new LinkedHashMap<>();
		String query1, query2, query3;

		query1 = "SELECT DISTINCT Ref_IDUtente, Data FROM Conto WHERE Pagato=? ORDER BY Data DESC";
		startConnection();
		statement = connection.prepareStatement(query1);
		statement.setBoolean(1, pagato);
		result = statement.executeQuery();
		while(result.next()) {
			int id=result.getInt("Ref_IDUtente");
			Date d=result.getDate("Data");
			if(conti.get(id)!=null) {
				conti.get(id).add(d);
			}else{
				List <Date> list=new ArrayList<>();
				list.add(d);
				conti.put(id,list);
			};
		}
		result.close(); 
		
		for (Integer IDUtente: conti.keySet()) {
			List <Date> list=conti.get(IDUtente);
			JSONArray pagamenti=new JSONArray();
			JSONArray ordinazioni=null;
			JSONArray prenotazioni=null;
			JSONObject conto=new JSONObject();
			for (Date data: list) {
				query2 = "SELECT Utente.Nome AS Utente_Nome, Cognome, IDUtente, IDOrdinazione, Quantita, IDConto, Data, Totale, Prodotto.Nome AS Nome, Prezzo, Categoria FROM Ordinazione INNER JOIN Conto ON Conto.IDConto=Ordinazione.Ref_IDConto INNER JOIN Prodotto ON Ordinazione.Prodotto_IDProdotto=Prodotto.IDProdotto INNER JOIN Utente ON Utente.IDUtente=Conto.Ref_IDUtente WHERE Data=? AND Pagato=? AND Ref_IDUtente=?";
				statement = connection.prepareStatement(query2);
				statement.setDate(1, data);
				statement.setBoolean(2, pagato);
				statement.setInt(3, IDUtente);
				result = statement.executeQuery();
				ordinazioni = JSONConverter.convertToJSONArray(result);
				result.close();
				
				
				query3 = "SELECT Utente.Nome AS Utente_Nome, Cognome, IDUtente, IDPrenotazione, Conto.Data, IDConto, Sdraio, Prezzo, Postazione_Numero, Totale FROM Prenotazione INNER JOIN Conto ON Conto.IDConto=Prenotazione.Conto_IDConto INNER JOIN Utente ON Utente.IDUtente=Conto.Ref_IDUtente WHERE Prenotazione.Data=? AND Pagato=? AND Utente_IDUtente=?";
				statement = connection.prepareStatement(query3);
				statement.setDate(1, data);
				statement.setBoolean(2, pagato);
				statement.setInt(3, IDUtente);
				result = statement.executeQuery();
				prenotazioni = JSONConverter.convertToJSONArray(result);
				result.close();
				pagamenti.put(JSONConverter.contoToJSONArray(ordinazioni,prenotazioni));
			}
			conto.put("conto", pagamenti);
			JSONconti.put(conto);
		}
		
		closeConnection(); 
		return JSONconti;
			
	}
	
	/**
	 * Effettua il pagamento di un conto e ritorna true se è andato tutto correttamente, false altrimenti
	 * @param IDConto
	 * @param sconto
	 * @param supplemento 
	 * @throws SQLException
	 */
	public static boolean paga(int IDConto, double sconto, double supplemento, double totale) throws SQLException{
		String query1 = "SELECT Quantita, Prezzo FROM Ordinazione INNER JOIN Conto ON Conto.IDConto=Ordinazione.Ref_IDConto INNER JOIN Prodotto ON Ordinazione.Prodotto_IDProdotto=Prodotto.IDProdotto WHERE IDConto=?";
		double tot_database=0;
		startConnection();
		statement = connection.prepareStatement(query1);
		statement.setInt(1, IDConto);
		result=statement.executeQuery();
		while(result.next()) {
			tot_database+=result.getDouble("Prezzo")*result.getInt("Quantita");
		}
		result.close();
		String query2 = "SELECT Sdraio, Prezzo, Postazione_Numero FROM Prenotazione INNER JOIN Conto ON Conto.IDConto=Prenotazione.Conto_IDConto WHERE IDConto=?";
		statement = connection.prepareStatement(query2);
		statement.setInt(1, IDConto);
		result=statement.executeQuery();
		while(result.next()) {
			if(result.getInt("Sdraio")>0) {
				tot_database+=result.getDouble("Prezzo")*result.getInt("Sdraio");
			}else if(result.getInt("Postazione_Numero")>0) {
				tot_database+=result.getDouble("Prezzo");
			}
		}
		result.close();
		if(totale!=tot_database) {
			closeConnection(); 
			return false;
		}
		String query3="UPDATE Conto SET Pagato=true, Totale=? WHERE IDConto=?";
		statement = connection.prepareStatement(query3);
		statement.setDouble(1, totale-sconto+supplemento);
		statement.setInt(2, IDConto);
		int result=statement.executeUpdate();

		closeConnection(); 
		
		return result>0;
	}
	
	
	/**
	 * Ritorna un JSONArray contenente lo storico degli incassi
	 * @param data
	 * @return storico
	 * @throws SQLException
	 */
	public static JSONArray getStoricoIncassi(LocalDate data) throws SQLException{
		JSONArray storico=new JSONArray();
		LocalDate lunedi=data.minusDays(data.getDayOfWeek().getValue()-1);
        LocalDate domenica=data.plusDays(7-data.getDayOfWeek().getValue());
		
        String query="SELECT SUM(Totale) AS Giorno FROM Conto WHERE Data=?";
		startConnection();
		statement = connection.prepareStatement(query);
		statement.setDate(1, Date.valueOf(data.toString()));
		result = statement.executeQuery();
		if(result.next()) {
			storico.put(JSONConverter.convertToJSONObject(result));
		}
		result.close();
		
		String query2="SELECT SUM(Totale) AS Settimana FROM Conto WHERE Data BETWEEN ? AND ?";
		statement = connection.prepareStatement(query2);
		statement.setDate(1, Date.valueOf(lunedi.toString()));
		statement.setDate(2, Date.valueOf(domenica.toString()));
		result = statement.executeQuery();
		if(result.next()) {
			storico.put(JSONConverter.convertToJSONObject(result));
		}
		result.close();
		
		
		String query3="SELECT SUM(Totale) AS Mese FROM Conto WHERE EXTRACT(MONTH FROM Data)=EXTRACT(MONTH FROM ?)";
		statement = connection.prepareStatement(query3);
		statement.setDate(1, Date.valueOf(data.toString()));
		result = statement.executeQuery();
		if(result.next()) {
			storico.put(JSONConverter.convertToJSONObject(result));
		}
		result.close();
		
		
		String query4="SELECT SUM(Totale) AS Anno FROM Conto WHERE EXTRACT(YEAR FROM Data)=EXTRACT(YEAR FROM ?)";
		startConnection();
		statement = connection.prepareStatement(query4);
		statement.setDate(1, Date.valueOf(data.toString()));
		result = statement.executeQuery();
		if(result.next()) {
			storico.put(JSONConverter.convertToJSONObject(result));
		}
		result.close();
		
		closeConnection(); 
		return storico;
			
	}
	
	/**
	 * Libera una postazione e ritorna un boolean che indica la correttezza dell'operazione
	 * @param IDPrenotazione
	 * @throws SQLException
	 */
	public static boolean liberaPostazione(int IDPrenotazione) throws SQLException{
		String query = "UPDATE Prenotazione SET Liberata=true WHERE IDPrenotazione=?";
		startConnection();
		statement = connection.prepareStatement(query);
		statement.setInt(1, IDPrenotazione);
		int result=statement.executeUpdate();
		closeConnection(); 
		return result>0;
	}
	
	/**
	 * Ritorna un JSONArray contenente le postazioni da poter liberare
	 * @return postazioni
	 * @throws SQLException
	 */
	public static JSONArray getPostazioniDaLiberare() throws SQLException{
		JSONArray postazioni=null;
		
		Date oggi=Date.valueOf(LocalDate.now().toString());
		String query1 = "SELECT IDPrenotazione, IDUtente, Nome, Cognome, Postazione_Numero FROM Utente JOIN Prenotazione ON Prenotazione.Utente_IDUtente=Utente.IDUtente WHERE Postazione_Numero IS NOT NULL AND Postazione_Numero>0 AND Liberata=false AND Data=? ORDER BY Cognome, Nome ASC";
		startConnection();
		statement = connection.prepareStatement(query1);
		statement.setDate(1, oggi);
		result = statement.executeQuery();

		postazioni = JSONConverter.convertToJSONArray(result);
		
		result.close();
		
		closeConnection(); 
		return postazioni;

	}
	
	/**
	 * Ritorna un JSONArray contenente i dati delle ordinazioni
	 * @return ordini
	 */
	public static JSONArray getOrdini() throws SQLException{
		JSONArray ordini=null;
		Date oggi=Date.valueOf(LocalDate.now().toString());
		String query1;

		query1 = "SELECT Utente.Nome AS Utente_Nome, Cognome, IDUtente, IDOrdinazione, Quantita, IDConto, Tavolo, Note, Prodotto.Nome AS Nome, Ingredienti, Categoria FROM Ordinazione INNER JOIN Conto ON Conto.IDConto=Ordinazione.Ref_IDConto INNER JOIN Prodotto ON Ordinazione.Prodotto_IDProdotto=Prodotto.IDProdotto INNER JOIN Utente ON Utente.IDUtente=Conto.Ref_IDUtente WHERE Data=? AND Consegnata=false ORDER BY IDOrdinazione ASC";
		startConnection();
		statement = connection.prepareStatement(query1);
		statement.setDate(1, oggi);
		result = statement.executeQuery();
		ordini = JSONConverter.convertToJSONArray(result);
		
		closeConnection(); 
		return ordini;
			
	}
	
	/**
	 * Imposta una ordinazione come consegnata
	 * @param IDOrdinazione
	 */
	public static boolean setConsegnata(int IDOrdinazione) throws SQLException{
		String query = "UPDATE Ordinazione SET Consegnata=true WHERE IDOrdinazione=?";
		startConnection();
		statement = connection.prepareStatement(query);
		statement.setInt(1, IDOrdinazione);
		int result=statement.executeUpdate();
		closeConnection(); 
		return result>0;	
	}
	
	
	/**
	 * Modifica il flag disponibile di un prodotto
	 * @param IDUtente
	 * @param cognome
	 */
	public static void setDisponibile(int IDProdotto, boolean flag) throws SQLException{
		String query = "UPDATE Prodotto SET Disponibile=? WHERE IDProdotto=?";
		startConnection();
		statement = connection.prepareStatement(query);
		statement.setBoolean(1, flag);
		statement.setInt(2, IDProdotto);
		statement.executeUpdate();
		closeConnection(); 
			
	}
}