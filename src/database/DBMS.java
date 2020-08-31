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
	public static Connection connect() throws SQLException {
		return dataSource.getConnection();
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
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query1)) {

			statement.setString(1, nome);
			statement.setString(2, cognome);
			statement.setString(3, email);
			statement.setString(4, password);
			statement.setString(5, cellulare);
			statement.executeUpdate();
		}
			
	}
	
	/**
	 * Ritorna l'IDUtente a partire dall'indirizzo email
	 * @param email
	 */
	public static int getIDUtente(String email) throws SQLException{
		String query1 = "SELECT IDUtente FROM Utente WHERE Email=?";
		int id=-1;
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query1)) {
			statement.setString(1, email);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				id = result.getInt("IDUtente");
			}
			result.close();
			return id;
		}
	}
	
	/**
	 * Effettua il login di un utente 
	 * @param email
	 * @param password
	 */
	public static Account login(String email, String password) throws SQLException{
		Account account;
		Utente utente;
		Map<String, Integer> ruoli=new LinkedHashMap<String, Integer>(); 
		String query1 = "SELECT IDUtente, Nome, Cognome, Email, Cellulare FROM Utente WHERE Email=? AND Password=MD5(?)";
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query1)) {

			statement.setString(1, email);
			statement.setString(2, password);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				utente = new Utente(result.getInt("IDUtente"), result.getString("Nome"), result.getString("Cognome"), result.getString("Email"), result.getString("Cellulare"));
			} else {

				return null;
			}
			result.close();
			ruoli.put("Cliente", utente.getIdUtente());


			String query2 = "SELECT IDAccountAziendale, Ruolo FROM AccountAziendale WHERE Utente_IDUtente=? ORDER BY Ruolo ASC";
			try (PreparedStatement statement2 = connection.prepareStatement(query2)) {

				statement2.setInt(1, utente.getIdUtente());
				ResultSet result2 = statement2.executeQuery();

				//ciclo per creare la mappa dei ruoli
				while (result2.next()) {
					ruoli.put(result2.getString("Ruolo"), result2.getInt("IDAccountAziendale"));
				}
				account = new Account(utente, ruoli);
				result2.close();
				return account;
			}
		}
		
	}
	
	
	/**
	 * Modifica il nome dell'utente
	 * @param IDUtente
	 * @param nome
	 */
	public static void aggiornaNome(int IDUtente, String nome) throws SQLException{
		String query = "UPDATE Utente SET Nome=? WHERE IDUtente=?";
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setString(1, nome);
			statement.setInt(2, IDUtente);
			statement.executeUpdate();
		}
			
	}
	
	/**
	 * Modifica il cognome dell'utente
	 * @param IDUtente
	 * @param cognome
	 */
	public static void aggiornaCognome(int IDUtente, String cognome) throws SQLException{
		String query = "UPDATE Utente SET Cognome=? WHERE IDUtente=?";
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setString(1, cognome);
			statement.setInt(2, IDUtente);
			statement.executeUpdate();
		}
			
	}

	/**
	 * Modifica l'indirizzo email dell'utente
	 * @param IDUtente
	 * @param email
	 */
	public static boolean aggiornaEmail(int IDUtente, String email) throws SQLException{
		String query = "UPDATE Utente SET Email=? WHERE IDUtente=?";
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setString(1, email);
			statement.setInt(2, IDUtente);
			int result=statement.executeUpdate();
			return result>0;
		}catch (SQLIntegrityConstraintViolationException e ){
			return false;
		}
			
	}
	
	/**
	 * Modifica il cellulare dell'utente
	 * @param IDUtente
	 * @param cellulare
	 */
	public static void aggiornaCellulare(int IDUtente, String cellulare) throws SQLException{
		String query = "UPDATE Utente SET Cellulare=? WHERE IDUtente=?";
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setString(1, cellulare);
			statement.setInt(2, IDUtente);
			statement.executeUpdate();
		}
			
	}
	
	/**
	 * Ritorna i dati dell'utente tramite il suo ID
	 * @param IDUtente
	 */
	public static Utente getUtente(int IDUtente) throws SQLException{
		Utente utente=null;
		String query = "SELECT IDUtente, Nome, Cognome, Email, Cellulare FROM Utente WHERE IDUtente=?";
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setInt(1, IDUtente);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				utente = new Utente(result.getInt("IDUtente"), result.getString("Nome"), result.getString("Cognome"), result.getString("Email"), result.getString("Cellulare"));
			}
			result.close();
			return utente;
		}
			
	}
	
	/**
	 * Ritorna i dati dell'utente tramite l'ID di un conto a lui associato
	 * @param IDConto
	 */
	public static Utente getUtenteFromConto(int IDConto) throws SQLException{
		Utente utente=null;
		String query = "SELECT IDUtente, Nome, Cognome, Email, Cellulare FROM Utente JOIN Conto ON Conto.Ref_IDUtente=Utente.IDUtente WHERE IDConto=?";
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setInt(1, IDConto);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				utente = new Utente(result.getInt("IDUtente"), result.getString("Nome"), result.getString("Cognome"), result.getString("Email"), result.getString("Cellulare"));
			}
			result.close();
			return utente;
		}
			
	}
	
	/**
	 * Ritorna i dati dell'utente tramite il suo indirizzo email
	 * @param email
	 */
	public static Utente getUtente(String email) throws SQLException{
		Utente utente=null;
		String query = "SELECT IDUtente, Nome, Cognome, Email, Cellulare FROM Utente WHERE Email=?";
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setString(1, email);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				utente = new Utente(result.getInt("IDUtente"), result.getString("Nome"), result.getString("Cognome"), result.getString("Email"), result.getString("Cellulare"));
			}
			result.close();
			return utente;
		}
			
	}
	
	/**
	 * Ritorna l'indirizzo email dell'utente tramite il suo ID
	 * @param IDUtente
	 * @return email
	 */
	public static String getEmail(int IDUtente) throws SQLException{
		String email=null;
		String query = "SELECT Email FROM Utente WHERE IDUtente=?";
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setInt(1, IDUtente);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				email = result.getString("Email");
			}
			result.close();
			return email;
		}
			
	}
	
	/**
	 * Modifica la password dell'utente
	 * @param IDUtente
	 * @param password
	 * @param password_attuale
	 */
	public static int aggiornaPassword(int IDUtente, String password, String password_attuale) throws SQLException{
		String query = "UPDATE Utente SET Password=MD5(?) WHERE IDUtente=? AND Password=MD5(?)";
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setString(1, password);
			statement.setInt(2, IDUtente);
			statement.setString(3, password_attuale);
			int result = statement.executeUpdate();
			return result;
		}
	}
	

	/**
	 * Setta il token generato randomicamente per reimpostare la password dell'utente 
	 * @param idCliente
	 * @param token
	 */
	public static void setToken(int idCliente, int token) throws SQLException{
		String query = "UPDATE Utente SET TokenResetPassword = ? WHERE IDUtente = ?";
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, token);
			statement.setInt(2, idCliente);
			statement.executeUpdate();
		}
		
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

		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setInt(1, idCliente);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				flag = result.getInt("TokenResetPassword") == token;
			}
			result.close();
			return flag;
		}
	}

	/**
	 * Reimposta la password dell'utente
	 * @param IDUtente
	 * @param password
	 * @param tokenResetPassword
	 * @throws SQLException
	 */
	public static int reimpostaPassword(int IDUtente, String password, int tokenResetPassword) throws SQLException{
		String query1 = "UPDATE Utente SET Password=MD5(?) WHERE IDUtente=? AND TokenResetPassword=?";
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query1)) {

			statement.setString(1, password);
			statement.setInt(2, IDUtente);
			statement.setInt(3, tokenResetPassword);
			int result = statement.executeUpdate();
			if (result != 0) {
				String query2 = "UPDATE Utente SET TokenResetPassword=0 WHERE IDUtente=? ";
				try (PreparedStatement statement2 = connection.prepareStatement(query2)) {
					statement2.setInt(1, IDUtente);
					statement2.executeUpdate();
				}
			}
			return result;
		}
	}

	/**
	 * Ritorna il JSONArray contenente le categorie dei prodotti disponibili
	 * @return categorie
	 */
	public static JSONArray getCategorie() throws SQLException{
		JSONArray categorie;
		String query = "SELECT DISTINCT Categoria FROM Prodotto WHERE Disponibile=true";
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

			ResultSet result = statement.executeQuery();
			categorie = JSONConverter.convertToJSONArray(result);
			result.close();
			return categorie;
		}
	}


	/**
	 * Ritorna il JSONArray contenente le categorie dei prodotti del bar
	 * @return categorie
	 */
	public static JSONArray getAllCategorieBar() throws SQLException{
		JSONArray categorie;
		String query = "SELECT DISTINCT Categoria FROM Prodotto WHERE Categoria NOT IN ('Antipasti', 'Panini', 'Piadine', 'Toast', 'Insalate', 'Pasta')";
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

			ResultSet result = statement.executeQuery();
			categorie = JSONConverter.convertToJSONArray(result);
			result.close();
			return categorie;
		}
	}

	/**
	 * Ritorna il JSONArray contenente le categorie dei prodotti della cucina
	 * @return categorie
	 */
	public static JSONArray getAllCategorieCucina() throws SQLException{
		JSONArray categorie;
		String query = "SELECT DISTINCT Categoria FROM Prodotto WHERE Categoria IN ('Antipasti', 'Panini', 'Piadine', 'Toast', 'Insalate', 'Pasta')";
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

			ResultSet result = statement.executeQuery();
			categorie = JSONConverter.convertToJSONArray(result);
			result.close();
			return categorie;
		}
	}
	
	/**
	 * Ritorna il JSONArray contenente i prodotti di una categoria
	 * @param categoria
	 * @return prodotti
	 */
	public static JSONArray getProdotti(String categoria) throws SQLException{
		JSONArray prodotti;
		String query = "SELECT * FROM Prodotto WHERE Categoria=? AND Disponibile=true";
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setString(1, categoria);
			ResultSet result = statement.executeQuery();
			prodotti = JSONConverter.convertToJSONArray(result);
			result.close();
			return prodotti;
		}
	}

	/**
	 * Ritorna il JSONArray contenente tutti i prodotti disponibili
	 * @return prodotti
	 */
	public static JSONArray getProdotti() throws SQLException{
		JSONArray prodotti;
		String query = "SELECT * FROM Prodotto WHERE Disponibile=true";
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

			ResultSet result = statement.executeQuery();
			prodotti = JSONConverter.convertToJSONArray(result);
			result.close();
			return prodotti;
		}
	}
	
	/**
	 * Ritorna il JSONArray contenente tutti i prodotti del bar
	 * @return prodotti
	 */
	public static JSONArray getAllProdottiBar() throws SQLException{
		JSONArray prodotti;
		String query = "SELECT * FROM Prodotto WHERE Categoria NOT IN ('Antipasti', 'Panini', 'Piadine', 'Toast', 'Insalate', 'Pasta')";
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

			ResultSet result = statement.executeQuery();
			prodotti = JSONConverter.convertToJSONArray(result);
			result.close();
			return prodotti;
		}
	}

	/**
	 * Ritorna il JSONArray contenente tutti i prodotti della cucina
	 * @return prodotti
	 */
	public static JSONArray getAllProdottiCucina() throws SQLException{
		JSONArray prodotti;
		String query = "SELECT * FROM Prodotto WHERE Categoria IN ('Antipasti', 'Panini', 'Piadine', 'Toast', 'Insalate', 'Pasta')";
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

			ResultSet result = statement.executeQuery();
			prodotti = JSONConverter.convertToJSONArray(result);
			result.close();
			return prodotti;
		}
	}
	
	/**
	 * Ritorna il prodotto relativo all'IDProdotto
	 * @param IDProdotto
	 * @return prodotto
	 */
	public static Prodotto getProdotto(int IDProdotto) throws SQLException{
		Prodotto prodotto=null;
		String query = "SELECT * FROM Prodotto WHERE Disponibile=true AND IDProdotto=?";
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setInt(1, IDProdotto);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				prodotto = new Prodotto(result.getInt("IDProdotto"), result.getString("Nome"), result.getString("Categoria"), result.getString("Ingredienti"), result.getDouble("Prezzo"), result.getBoolean("Disponibile"));
			}
			result.close();
			return prodotto;
		}
	}
	
	/**
	 * Inserisce una nuova ordinazione, associandola ad un conto che può essere creato se ancora non è stato fatto
	 * @param carrello
	 * @param IDUtente
	 */
	public static void inserisciOrdinazione(Carrello carrello, int IDUtente, int tavolo) throws SQLException{
		//Verifica che esista già un conto non ancora pagato associato all'utente per la data corrente
		Date data=Date.valueOf(LocalDate.now().toString());
		int IDConto=0;
		String query1 = "SELECT IDConto FROM Conto WHERE Data=? AND Pagato=false AND Ref_IDUtente=?";
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query1)) {
			statement.setDate(1, data);
			statement.setInt(2, IDUtente);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				IDConto = result.getInt("IDConto");
			} else {
				String query2 = "INSERT INTO Conto (Data, Pagato, Ref_IDUtente) VALUES (?, false, ?)";
				try(PreparedStatement statement2 = connection.prepareStatement(query2)) {
					statement2.setDate(1, data);
					statement2.setInt(2, IDUtente);
					statement2.executeUpdate();
				}
				try(PreparedStatement statement3 = connection.prepareStatement(query1)) {
					statement3.setDate(1, data);
					statement3.setInt(2, IDUtente);
					ResultSet result2 = statement3.executeQuery();
					if (result2.next()) {
						IDConto = result2.getInt("IDConto");
					}
					result2.close();
				}
			}
			result.close();

			for (Map.Entry<Prodotto, Ordinazione> entry : carrello.getProdotti().entrySet()) {
				String query3 = "INSERT INTO Ordinazione (Quantita, Consegnata, Ref_IDConto, Tavolo, Prodotto_IDProdotto, Note)" +
						"VALUES (?, false, ?, ?, ?, ?)";
				try(PreparedStatement statement3 = connection.prepareStatement(query3)) {
					statement3.setInt(1, entry.getValue().getQuantita());
					statement3.setInt(2, IDConto);
					statement3.setInt(3, tavolo);
					statement3.setInt(4, entry.getKey().getIdProdotto());
					statement3.setString(5, entry.getValue().getNote());
					statement3.executeUpdate();
				}
			}

		}
	}
	
	/**
	 * Ritorna un JSONArray contenente i dati dei pagamenti
	 * @param IDUtente
	 * @param pagato
	 * @return pagamenti
	 */
	public static JSONArray getPagamenti(int IDUtente, boolean pagato) throws SQLException{
		JSONArray pagamenti=new JSONArray();
		JSONArray ordinazioni;
		JSONArray prenotazioni;
		Date oggi=Date.valueOf(LocalDate.now().toString());
		List <Date> conti=new ArrayList<>();
		String query, query1, query2;

		query = "SELECT DISTINCT Data FROM Conto WHERE Pagato=? AND Ref_IDUtente=? AND Data<=? ORDER BY Data DESC";
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setBoolean(1, pagato);
			statement.setInt(2, IDUtente);
			statement.setDate(3,oggi);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				conti.add(result.getDate("Data"));
			}
			result.close();


			for (Date data : conti) {
				query1 = "SELECT IDOrdinazione, Quantita, IDConto, Data, Totale, Nome, Prezzo, Categoria FROM Ordinazione INNER JOIN Conto ON Conto.IDConto=Ordinazione.Ref_IDConto INNER JOIN Prodotto ON Ordinazione.Prodotto_IDProdotto=Prodotto.IDProdotto WHERE Data=? AND Pagato=? AND Ref_IDUtente=?";
				try (PreparedStatement statement1 = connection.prepareStatement(query1)) {
					statement1.setDate(1, data);
					statement1.setBoolean(2, pagato);
					statement1.setInt(3, IDUtente);
					ResultSet result1 = statement1.executeQuery();
					ordinazioni = JSONConverter.convertToJSONArray(result1);
					result1.close();
				}

				query2 = "SELECT IDPrenotazione, Conto.Data AS Data, IDConto, Sdraio, Prezzo, Postazione_Numero, Totale FROM Prenotazione INNER JOIN Conto ON Conto.IDConto=Prenotazione.Conto_IDConto WHERE Prenotazione.Data=? AND Pagato=? AND Utente_IDUtente=?";
				try (PreparedStatement statement2 = connection.prepareStatement(query2)) {
					statement2.setDate(1, data);
					statement2.setBoolean(2, pagato);
					statement2.setInt(3, IDUtente);
					ResultSet result2 = statement2.executeQuery();
					prenotazioni = JSONConverter.convertToJSONArray(result2);
					result2.close();
					pagamenti.put(JSONConverter.contoToJSONArray(ordinazioni, prenotazioni));
				}
			}

			return pagamenti;
		}
	}
	
	
	/**
	 * Ritorna un JSONArray contenente i dati degli account aziendali
	 * @return accounts
	 */
	public static JSONArray getAccountAziendali() throws SQLException{
		JSONArray accounts;
		String query = "SELECT IDAccountAziendale, Ruolo, IDUtente, Nome, Cognome, Email, Cellulare FROM AccountAziendale INNER JOIN Utente ON AccountAziendale.Utente_IDUtente=Utente.IDUtente ORDER BY Cognome, Nome ASC";
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

			ResultSet result = statement.executeQuery();
			accounts = JSONConverter.convertToJSONArray(result);
			result.close();
			return accounts;
		}
	}
	
	
	/**
	 * Rimuove un account aziendale e ritorna una flag che indica se la query è andata a buon fine
	 * @param IDUtente
	 * @param ruolo
	 * @throws SQLException
	 */
	public static boolean rimuoviRuolo(int IDUtente, String ruolo) throws SQLException{
		String query = "DELETE FROM AccountAziendale WHERE Utente_IDUtente=? AND Ruolo=?";
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setInt(1, IDUtente);
			statement.setString(2, ruolo);
			int result = statement.executeUpdate();

			return result > 0;
		}
	}
	
	/**
	 * Aggiunge un account aziendale e ritorna una flag che indica se la query è andata a buon fine
	 * @param IDUtente
	 * @param ruolo
	 * @throws SQLException
	 */
	public static boolean aggiungiRuolo(int IDUtente, String ruolo) throws SQLException{
		String query = "INSERT INTO AccountAziendale (Utente_IDUtente, Ruolo) VALUES (?, ?)";
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setInt(1, IDUtente);
			statement.setString(2, ruolo);
			int result = statement.executeUpdate();
			return result > 0;
		}
	}
	
	/**
	 * Ritorna un JSONArray contenente i dati degli account 
	 * @param nome
	 * @param cognome
	 * @return accounts
	 * @throws SQLException
	 */
	public static JSONArray searchAccount(String nome, String cognome) throws SQLException{
		JSONArray accounts;
		String query = "SELECT IDUtente, Nome, Cognome, Email, Cellulare FROM Utente LEFT JOIN AccountAziendale ON AccountAziendale.Utente_IDUtente=Utente.IDUtente WHERE Nome=? AND Cognome=? AND IDAccountAziendale IS NULL";
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setString(1, nome);
			statement.setString(2, cognome);
			ResultSet result = statement.executeQuery();
			accounts = JSONConverter.convertToJSONArray(result);
			result.close();
			return accounts;
		}
	}
	
	/**
	 * Ritorna un JSONArray contenente i dati dei clienti 
	 * @param nome
	 * @param cognome
	 * @return accounts
	 * @throws SQLException
	 */
	public static JSONArray searchClient(String nome, String cognome) throws SQLException{
		JSONArray accounts;
		String query = "SELECT IDUtente, Nome, Cognome, Email, Cellulare FROM Utente WHERE Nome=? AND Cognome=?";
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setString(1, nome);
			statement.setString(2, cognome);
			ResultSet result = statement.executeQuery();
			accounts = JSONConverter.convertToJSONArray(result);
			result.close();
			return accounts;
		}
			
	}
	
	/**
	 * Ritorna un JSONArray contenente i dati dei conti
	 * @param pagato
	 * @return JSONconti
	 */
	public static JSONArray getConti(boolean pagato) throws SQLException{
		JSONArray JSONconti=new JSONArray();
		Date oggi=Date.valueOf(LocalDate.now().toString());
		Map <Integer, List<Date>> conti=new LinkedHashMap<>();
		String query1, query2, query3;

		query1 = "SELECT DISTINCT Ref_IDUtente, Data FROM Conto WHERE Pagato=? AND Data<=? ORDER BY Data DESC";
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query1)) {
			statement.setBoolean(1, pagato);
			statement.setDate(2,oggi);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				int id = result.getInt("Ref_IDUtente");
				Date d = result.getDate("Data");
				if (conti.get(id) != null) {
					conti.get(id).add(d);
				} else {
					List<Date> list = new ArrayList<>();
					list.add(d);
					conti.put(id, list);
				}
			}
			result.close();

			for (Integer IDUtente : conti.keySet()) {
				List<Date> list = conti.get(IDUtente);
				JSONArray pagamenti = new JSONArray();
				JSONArray ordinazioni ;
				JSONArray prenotazioni ;
				JSONObject conto = new JSONObject();
				for (Date data : list) {
					query2 = "SELECT Utente.Nome AS Utente_Nome, Cognome, IDUtente, IDOrdinazione, Quantita, IDConto, Data, Totale, Prodotto.Nome AS Nome, Prezzo, Categoria FROM Ordinazione INNER JOIN Conto ON Conto.IDConto=Ordinazione.Ref_IDConto INNER JOIN Prodotto ON Ordinazione.Prodotto_IDProdotto=Prodotto.IDProdotto INNER JOIN Utente ON Utente.IDUtente=Conto.Ref_IDUtente WHERE Data=? AND Pagato=? AND Ref_IDUtente=?";
					try (PreparedStatement statement2 = connection.prepareStatement(query2)) {
						statement2.setDate(1, data);
						statement2.setBoolean(2, pagato);
						statement2.setInt(3, IDUtente);
						ResultSet result2 = statement2.executeQuery();
						ordinazioni = JSONConverter.convertToJSONArray(result2);
						result2.close();
					}

					query3 = "SELECT Utente.Nome AS Utente_Nome, Cognome, IDUtente, IDPrenotazione, Conto.Data, IDConto, Sdraio, Prezzo, Postazione_Numero, Totale FROM Prenotazione INNER JOIN Conto ON Conto.IDConto=Prenotazione.Conto_IDConto INNER JOIN Utente ON Utente.IDUtente=Conto.Ref_IDUtente WHERE Prenotazione.Data=? AND Pagato=? AND Utente_IDUtente=?";
					try (PreparedStatement statement3 = connection.prepareStatement(query3)) {
						statement3.setDate(1, data);
						statement3.setBoolean(2, pagato);
						statement3.setInt(3, IDUtente);
						ResultSet result3 = statement3.executeQuery();
						prenotazioni = JSONConverter.convertToJSONArray(result3);
						result3.close();
						pagamenti.put(JSONConverter.contoToJSONArray(ordinazioni, prenotazioni));
					}
				}
				conto.put("conto", pagamenti);
				JSONconti.put(conto);
			}

			return JSONconti;
		}
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
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query1)) {
			statement.setInt(1, IDConto);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				tot_database += result.getDouble("Prezzo") * result.getInt("Quantita");
			}
			result.close();
			String query2 = "SELECT Sdraio, Prezzo, Postazione_Numero FROM Prenotazione INNER JOIN Conto ON Conto.IDConto=Prenotazione.Conto_IDConto WHERE IDConto=?";
			try (PreparedStatement statement2 = connection.prepareStatement(query2)) {
				statement2.setInt(1, IDConto);
				ResultSet result2 = statement2.executeQuery();
				while (result2.next()) {
					if (result2.getInt("Sdraio") > 0) {
						tot_database += result2.getDouble("Prezzo") * result2.getInt("Sdraio");
					} else if (result2.getInt("Postazione_Numero") > 0) {
						tot_database += result2.getDouble("Prezzo");
					}
				}
				result2.close();
				if (totale != tot_database) {
					return false;
				}
			}

			String query3 = "UPDATE Conto SET Pagato=true, Totale=? WHERE IDConto=?";
			try (PreparedStatement statement3 = connection.prepareStatement(query3)) {

				statement3.setDouble(1, totale - sconto + supplemento);
				statement3.setInt(2, IDConto);
				int risultato = statement3.executeUpdate();
				return risultato > 0;
			}
		}
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
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setDate(1, Date.valueOf(data.toString()));
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				storico.put(JSONConverter.convertToJSONObject(result));
			}
			result.close();

			String query2 = "SELECT SUM(Totale) AS Settimana FROM Conto WHERE Data BETWEEN ? AND ?";
			try (PreparedStatement statement2 = connection.prepareStatement(query2)) {
				statement2.setDate(1, Date.valueOf(lunedi.toString()));
				statement2.setDate(2, Date.valueOf(domenica.toString()));
				ResultSet result2 = statement2.executeQuery();
				if (result2.next()) {
					storico.put(JSONConverter.convertToJSONObject(result2));
				}
				result2.close();
			}

			String query3 = "SELECT SUM(Totale) AS Mese FROM Conto WHERE EXTRACT(MONTH FROM Data)=EXTRACT(MONTH FROM ?)";
			try (PreparedStatement statement3 = connection.prepareStatement(query3)) {
				statement3.setDate(1, Date.valueOf(data.toString()));
				ResultSet result3 = statement3.executeQuery();
				if (result3.next()) {
					storico.put(JSONConverter.convertToJSONObject(result3));
				}
				result3.close();
			}

			String query4 = "SELECT SUM(Totale) AS Anno FROM Conto WHERE EXTRACT(YEAR FROM Data)=EXTRACT(YEAR FROM ?)";
			try (PreparedStatement statement4 = connection.prepareStatement(query4)) {
				statement4.setDate(1, Date.valueOf(data.toString()));
				ResultSet result4 = statement4.executeQuery();
				if (result4.next()) {
					storico.put(JSONConverter.convertToJSONObject(result4));
				}
				result4.close();

				return storico;
			}
		}
	}
	
	/**
	 * Libera una postazione e ritorna un boolean che indica la correttezza dell'operazione
	 * @param IDPrenotazione
	 * @throws SQLException
	 */
	public static boolean liberaPostazione(int IDPrenotazione) throws SQLException{
		String query = "UPDATE Prenotazione SET Liberata=true WHERE IDPrenotazione=?";
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setInt(1, IDPrenotazione);
			int result = statement.executeUpdate();
			return result > 0;
		}
	}
	
	/**
	 * Ritorna un JSONArray contenente le postazioni da poter liberare
	 * @return postazioni
	 * @throws SQLException
	 */
	public static JSONArray getPostazioniDaLiberare() throws SQLException{
		JSONArray postazioni;
		
		Date oggi=Date.valueOf(LocalDate.now().toString());
		String query = "SELECT IDPrenotazione, IDUtente, Nome, Cognome, Postazione_Numero FROM Utente JOIN Prenotazione ON Prenotazione.Utente_IDUtente=Utente.IDUtente WHERE Postazione_Numero IS NOT NULL AND Postazione_Numero>0 AND Liberata=false AND Data=? ORDER BY Cognome, Nome ASC";
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setDate(1, oggi);
			ResultSet result = statement.executeQuery();

			postazioni = JSONConverter.convertToJSONArray(result);

			result.close();
			return postazioni;
		}
	}
	
	/**
	 * Ritorna un JSONArray contenente i dati delle ordinazioni per un cameriere
	 * @return ordini
	 */
	public static JSONArray getOrdiniCameriere() throws SQLException{
		JSONArray ordini;
		Date oggi=Date.valueOf(LocalDate.now().toString());
		String query = "SELECT Utente.Nome AS Utente_Nome, Cognome, IDUtente, IDOrdinazione, Quantita, IDConto, Tavolo, Note, Prodotto.Nome AS Nome, Ingredienti, Categoria FROM Ordinazione INNER JOIN Conto ON Conto.IDConto=Ordinazione.Ref_IDConto INNER JOIN Prodotto ON Ordinazione.Prodotto_IDProdotto=Prodotto.IDProdotto INNER JOIN Utente ON Utente.IDUtente=Conto.Ref_IDUtente WHERE Data=? AND Consegnata=false ORDER BY IDOrdinazione ASC";
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setDate(1, oggi);
			ResultSet result = statement.executeQuery();
			ordini = JSONConverter.convertToJSONArray(result);

			result.close();
			return ordini;
		}
	}

	/**
	 * Ritorna un JSONArray contenente i dati delle ordinazioni per la cucina
	 * @return ordini
	 */
	public static JSONArray getOrdiniCucina() throws SQLException{
		JSONArray ordini;
		Date oggi=Date.valueOf(LocalDate.now().toString());
		String query = "SELECT Utente.Nome AS Utente_Nome, Cognome, IDUtente, IDOrdinazione, Quantita, IDConto, Tavolo, Note, Prodotto.Nome AS Nome, Ingredienti, Categoria FROM Ordinazione INNER JOIN Conto ON Conto.IDConto=Ordinazione.Ref_IDConto INNER JOIN Prodotto ON Ordinazione.Prodotto_IDProdotto=Prodotto.IDProdotto INNER JOIN Utente ON Utente.IDUtente=Conto.Ref_IDUtente WHERE Data=? AND Consegnata=false AND Categoria IN ('Antipasti', 'Panini', 'Piadine', 'Toast', 'Insalate', 'Pasta') ORDER BY IDOrdinazione ASC";
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setDate(1, oggi);
			ResultSet result = statement.executeQuery();
			ordini = JSONConverter.convertToJSONArray(result);

			result.close();
			return ordini;
		}
	}

	/**
	 * Ritorna un JSONArray contenente i dati delle ordinazioni per il bar
	 * @return ordini
	 */
	public static JSONArray getOrdiniBar() throws SQLException{
		JSONArray ordini;
		Date oggi=Date.valueOf(LocalDate.now().toString());
		String query = "SELECT Utente.Nome AS Utente_Nome, Cognome, IDUtente, IDOrdinazione, Quantita, IDConto, Tavolo, Note, Prodotto.Nome AS Nome, Ingredienti, Categoria FROM Ordinazione INNER JOIN Conto ON Conto.IDConto=Ordinazione.Ref_IDConto INNER JOIN Prodotto ON Ordinazione.Prodotto_IDProdotto=Prodotto.IDProdotto INNER JOIN Utente ON Utente.IDUtente=Conto.Ref_IDUtente WHERE Data=? AND Consegnata=false AND Categoria NOT IN ('Antipasti', 'Panini', 'Piadine', 'Toast', 'Insalate', 'Pasta') ORDER BY IDOrdinazione ASC";
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setDate(1, oggi);
			ResultSet result = statement.executeQuery();
			ordini = JSONConverter.convertToJSONArray(result);

			result.close();
			return ordini;
		}
	}
	
	/**
	 * Imposta una ordinazione come consegnata
	 * @param IDOrdinazione
	 */
	public static boolean setConsegnata(int IDOrdinazione) throws SQLException{
		String query = "UPDATE Ordinazione SET Consegnata=true WHERE IDOrdinazione=?";
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setInt(1, IDOrdinazione);
			int result = statement.executeUpdate();
			return result > 0;
		}
	}


	/**
	 * Modifica il flag disponibile di un prodotto
	 * @param IDProdotto
	 * @param flag
	 * @throws SQLException
	 */
	public static void setDisponibile(int IDProdotto, boolean flag) throws SQLException{
		String query = "UPDATE Prodotto SET Disponibile=? WHERE IDProdotto=?";
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setBoolean(1, flag);
			statement.setInt(2, IDProdotto);
			statement.executeUpdate();

		}
	}

	/**
	 * Ritorna un JSONArray contenente i dati delle postazioni
	 * @return postazioni
	 */
	public static JSONArray getPostazioni() throws Exception{
		JSONArray postazioni;
		String query = "SELECT * FROM Postazione";
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

			ResultSet result = statement.executeQuery();
			postazioni = JSONConverter.convertToJSONArray(result);
			result.close();
			return postazioni;
		}
	}

	/**
	 * Ritorna un JSONArray contenente i dati delle prenotazioni per la data selezionata
	 * @return ordini
	 */
	public static JSONArray getPrenotazioni(LocalDate data) throws SQLException{
		JSONArray prenotazioni;
		String query= "SELECT IDPrenotazione, Sdraio, Postazione_Numero AS Numero, Occupata FROM Prenotazione WHERE Data=? AND Liberata=false ORDER BY Numero";
		if(LocalDate.now().isAfter(data)){
			return new JSONArray();
		}
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setDate(1, Date.valueOf(data.toString()));
			ResultSet result = statement.executeQuery();
			prenotazioni = JSONConverter.convertToJSONArray(result);
			result.close();
			return prenotazioni;
		}

	}

	/**
	 * Ritorna un JSONArray contenente i dati delle prenotazioni per la data selezionata con i dati di chi ha prenotato
	 * @return ordini
	 */
	public static JSONArray getPrenotazioniBagnino(LocalDate data) throws SQLException{
		JSONArray prenotazioni;
		if(LocalDate.now().isAfter(data)){
			return new JSONArray();
		}
		String query = "SELECT Nome, Cognome, Email, Cellulare, IDUtente, IDPrenotazione, Sdraio, Postazione_Numero AS Numero, Occupata FROM Prenotazione JOIN Utente ON Prenotazione.Utente_IDUtente = Utente.IDUtente WHERE Data=? AND Liberata=false ORDER BY Numero";
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setDate(1, Date.valueOf(data.toString()));
			ResultSet result = statement.executeQuery();
			prenotazioni = JSONConverter.convertToJSONArray(result);
			result.close();
			return prenotazioni;
		}
	}

	/**
	 * Effettua una prenotazione per delle sdraio singole
	 * @param sdraio
	 * @param date
	 * @param IDUtente
	 * @param prezzo
	 */
	public static boolean prenotaSdraio(int sdraio, LocalDate date, int IDUtente, double prezzo) throws SQLException, RuntimeException{
		if(date.getMonthValue()<6 || date.getMonthValue()>9)throw new RuntimeException();
		int IDConto=0;
		Date data=Date.valueOf(date.toString());

		//Verifica che ci siano ancora sdraio disponibili per la data selezionata
		String query = "SELECT SUM(Sdraio) AS sdraioOccupate FROM Prenotazione WHERE Data=? AND Liberata=false";
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setDate(1, data);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				if (result.getInt("sdraioOccupate") + sdraio > Prenotazione.getSdraioMax()) return false;
			}
			result.close();
			//Verifica che esista già un conto non ancora pagato per la data selezionata associato all'utente

			String query1 = "SELECT IDConto FROM Conto WHERE Data=? AND Pagato=false AND Ref_IDUtente=?";
			try(PreparedStatement statement1 = connection.prepareStatement(query1)) {
				statement1.setDate(1, data);
				statement1.setInt(2, IDUtente);
				ResultSet result1 = statement1.executeQuery();
				if (result1.next()) {
					IDConto = result1.getInt("IDConto");
				} else {
					String query2 = "INSERT INTO Conto (Data, Pagato, Ref_IDUtente) VALUES (?, false, ?)";
					try(PreparedStatement statement2 = connection.prepareStatement(query2)) {
						statement2.setDate(1, data);
						statement2.setInt(2, IDUtente);
						statement2.executeUpdate();
					}

					try(PreparedStatement statement3 = connection.prepareStatement(query1)) {
						statement3.setDate(1, data);
						statement3.setInt(2, IDUtente);
						ResultSet result3 = statement3.executeQuery();
						if (result3.next()) {
							IDConto = result3.getInt("IDConto");
						}
						result3.close();
					}
				}
				result1.close();

				String query4 = "INSERT INTO Prenotazione (Data, Utente_IDUtente, Conto_IDConto, Sdraio, Prezzo, Postazione_Numero) " +
						"VALUES (?, ?, ?, ?, ?, NULL)";

				try(PreparedStatement statement4 = connection.prepareStatement(query4)) {
					statement4.setDate(1, data);
					statement4.setInt(2, IDUtente);
					statement4.setInt(3, IDConto);
					statement4.setInt(4, sdraio);
					statement4.setDouble(5, prezzo);
					statement4.executeUpdate();

				}
				return true;
			}
		}
	}

	/**
	 * Effettua una prenotazione per una postazione
	 * @param numero
	 * @param date
	 * @param IDUtente
	 * @param prezzo
	 */
	public static boolean prenotaPostazione(int numero, LocalDate date, int IDUtente, double prezzo) throws SQLException, RuntimeException{
		if(date.getMonthValue()<6 || date.getMonthValue()>9)throw new RuntimeException();
		int IDConto=0;
		Date data=Date.valueOf(date.toString());

		//Verifica che la postazione sia veramente disponibile per la data selezionata
		String query = "SELECT IDPrenotazione FROM Prenotazione WHERE Data=? AND Liberata=false AND Postazione_Numero=?";
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setDate(1, data);
			statement.setInt(2, numero);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				if (result.getInt("IDPrenotazione") != 0) return false;
			}
			result.close();

			//Verifica che esista già un conto non ancora pagato per la data selezionata associato all'utente
			String query1 = "SELECT IDConto FROM Conto WHERE Data=? AND Pagato=false AND Ref_IDUtente=?";
			try (PreparedStatement statement1 = connection.prepareStatement(query1)) {
				statement1.setDate(1, data);
				statement1.setInt(2, IDUtente);
				ResultSet result1 = statement1.executeQuery();
				if (result1.next()) {
					IDConto = result1.getInt("IDConto");
				} else {
					String query2 = "INSERT INTO Conto (Data, Pagato, Ref_IDUtente) VALUES (?, false, ?)";
					try (PreparedStatement statement2 = connection.prepareStatement(query2)) {
						statement2.setDate(1, data);
						statement2.setInt(2, IDUtente);
						statement2.executeUpdate();
					}


					try (PreparedStatement statement3 = connection.prepareStatement(query1)) {
						statement3.setDate(1, data);
						statement3.setInt(2, IDUtente);
						ResultSet result3 = statement3.executeQuery();
						if (result3.next()) {
							IDConto = result3.getInt("IDConto");
						}
						result3.close();
					}
				}
				result1.close();
				String query4 = "INSERT INTO Prenotazione (Data, Utente_IDUtente, Conto_IDConto, Sdraio, Prezzo, Postazione_Numero) " +
						"VALUES (?, ?, ?, ?, ?, ?)";

				try (PreparedStatement statement4 = connection.prepareStatement(query4)) {
					statement4.setDate(1, data);
					statement4.setInt(2, IDUtente);
					statement4.setInt(3, IDConto);
					statement4.setInt(4, 0);
					statement4.setDouble(5, prezzo);
					statement4.setInt(6, numero);
					statement4.executeUpdate();
				}
			}
		}
		return true;

	}

	/**
	 * Ritorna un JSONArray contenente i dati delle prenotazioni dell'utente
	 * @param IDUtente
	 * @throws SQLException
	 */
	public static JSONArray getPrenotazioniUtente(int IDUtente) throws SQLException{
		JSONArray prenotazioni;
		Date data=Date.valueOf(LocalDate.now().toString());
		String query = "SELECT IDPrenotazione, Sdraio, Prezzo, Prenotazione.Data, IDConto, Postazione_Numero AS Numero FROM Prenotazione JOIN Conto on Prenotazione.Conto_IDConto = Conto.IDConto WHERE Prenotazione.Utente_IDUtente=? AND Prenotazione.Data>=? AND Liberata=false AND Occupata=false AND Pagato=false";
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setInt(1, IDUtente);
			statement.setDate(2, Date.valueOf(data.toString()));
			ResultSet result = statement.executeQuery();
			prenotazioni = JSONConverter.convertToJSONArray(result);
			result.close();
			return prenotazioni;
		}
	}

	/**
	 * Rimuove una prenotazione e ritorna un boolean che indica se l'operazione è riuscita correttamente
	 * @param IDPrenotazione
	 * @throws SQLException
	 */
	public static boolean rimuoviPrenotazione(int IDPrenotazione, int IDUtente, int IDConto) throws SQLException{
		Date data=Date.valueOf(LocalDate.now().toString());
		String query = "DELETE FROM Prenotazione WHERE IDPrenotazione=? AND Utente_IDUtente=? AND Data>=? AND Liberata=false AND Occupata=false AND Conto_IDConto=?";
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setInt(1, IDPrenotazione);
			statement.setInt(2, IDUtente);
			statement.setDate(3, Date.valueOf(data.toString()));
			statement.setInt(4, IDConto);
			int result = statement.executeUpdate();

			String query2 = "DELETE FROM Conto WHERE IDConto=? AND (SELECT COUNT(Prenotazione.IDPrenotazione) AS Prenotazioni FROM Prenotazione WHERE Conto_IDConto=?)=0 AND (SELECT COUNT(IDOrdinazione) AS Ordinazioni FROM Ordinazione WHERE Ref_IDConto=?)=0";
			try(PreparedStatement statement2 = connection.prepareStatement(query2)) {
				statement2.setInt(1, IDConto);
				statement2.setInt(2, IDConto);
				statement2.setInt(3, IDConto);
				statement2.executeUpdate();
			}
			return result > 0;
		}
	}

	/**
	 * Ritorna un JSONArray contenente i dati delle prenotazioni per la data selezionata con i dati di chi ha prenotato
	 * @return ordini
	 */
	public static JSONArray getPrenotazioniReception(LocalDate data) throws SQLException{
		return getPrenotazioniBagnino(data);
	}

	/**
	 * Imposta il flag occupata della prenotazione specificata tramite ID e ritorna un boolean che indica la corretta esecuzione dell'operazione
	 * @param IDPrenotazione
	 * @param occupata
	 * @throws SQLException
	 */
	public static boolean setOccupata(int IDPrenotazione, boolean occupata) throws SQLException{
		Date data=Date.valueOf(LocalDate.now().toString());
		String query = "UPDATE Prenotazione SET Occupata = ? WHERE IDPrenotazione = ? AND Data=? ";
		try(Connection connection=dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {

			statement.setBoolean(1, occupata);
			statement.setInt(2, IDPrenotazione);
			statement.setDate(3, data);
			int result = statement.executeUpdate();
			return result > 0;
		}
	}

}