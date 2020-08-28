package utils;

import database.DBMS;
import model.Carrello;
import model.Comande;
import model.Prodotto;
import model.Utente;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;


/**
 * Questa classe di utility contiene dei metodi che permettono di convertire un oggetto
 * di tipo ResultSet (esito di una query al database) o un oggetto di tipo Carrello (per
 * la gestione dei prodotti ordinati in sessione) in un array di oggetti JSON.
 * @author La Martina Marco
 */
 public class JSONConverter {
	 
    /**
     * Dato un oggetto ResultSet in input, contenente il risultato di una query, restituisce
     * un oggetto JSON con tutti i dati restituiti dalla query.
     * @param resultSet
     * @return object
     * @throws SQLException
     */
    public static JSONObject convertToJSONObject(ResultSet resultSet) throws SQLException {
    	JSONObject object = new JSONObject();
    	try {
    		int totalColumns = resultSet.getMetaData().getColumnCount();
	            for (int i = 0; i < totalColumns; i++) {
	           		object.put(resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase(), resultSet.getObject(i + 1));
	           	}
    	}
    	catch(JSONException e) {
    		e.printStackTrace();
    	}
        return object;
    }
    
	 /**
	  * Dato un oggetto ResultSet in input, contenente il risultato di una query, restituisce 
	  * un array di oggetti JSON, in cui ogni oggetto JSON è relativo ad una riga della tabella
	  * risultante dalla query.
	  * @param resultSet
	  * @return jsonArray
      * @throws SQLException
	  */
	 public static JSONArray convertToJSONArray(ResultSet resultSet) throws SQLException {
		 JSONArray jsonArray = new JSONArray();
		 try {
			 while (resultSet.next())
				 jsonArray.put(convertToJSONObject(resultSet));
		 }
		 catch(JSONException e) {
			 e.printStackTrace();
		 }
		 return jsonArray;
		 }
	 
	 /**
	  * Dato un oggetto Carrello in input, contenente i prodotti inseriti dall'utente, restituisce
	  * un array di oggetti JSON, in cui ogni oggetto JSON contiene i dati di un prodotto e la quantità
	  * dello stesso nel carrello.
	  * @param carrello
	  * @return jsonArray
	  */
	 
	 public static JSONArray carrelloToJSONArray(Carrello carrello) {
		 JSONArray jsonArray = new JSONArray();
		 if(carrello == null) {
			 return jsonArray;
		 }
		 try {
			 for (Prodotto p : carrello.getProdotti().keySet()) {
				 JSONObject object = new JSONObject();
				 object.put("idprodotto", p.getIdProdotto());
				 object.put("prezzo", p.getPrezzo());
				 object.put("nome", p.getNome());
				 object.put("categoria", p.getCategoria());
				 object.put("ingredienti", p.getIngredienti());
				 object.put("disponibile", p.isDisponibile());
				 object.put("quantita", carrello.getProdotti().get(p).getQuantita());
				 object.put("note", carrello.getProdotti().get(p).getNote());
				 jsonArray.put(object);
				 }
			 }
		 catch(JSONException e) {
			 e.printStackTrace();
			 }
		 return jsonArray;
		 }
	 
	 /**
	  * Dato un oggetto Utente in input, contenente i dati dell'utente, restituisce
	  * un oggetto JSON
	  * @param utente
	  * @return jsonobj
	  */
	 
	 public static JSONObject utenteToJSONArray(Utente utente) {
		 JSONObject jsonobj = new JSONObject();
		 if(utente == null) {
			 return jsonobj;
		 }
		 try {
			 jsonobj.put("idutente", utente.getIdUtente());
			 jsonobj.put("nome", utente.getNome());
			 jsonobj.put("cognome", utente.getCognome());
			 jsonobj.put("email", utente.getEmail());
			 jsonobj.put("cellulare", utente.getCellulare());
			
			 }
		 catch(JSONException e) {
			 e.printStackTrace();
			 }
		 return jsonobj;
		 }
	 
	 
	 /**
	  * Dati due JSONArray in input, contenente le ordinazioni e le prenotazioni di un utente, restituisce
	  * un JSONObject composto dai due oggetti JSON
	  * @param ordinazioni
	  * @param prenotazioni
	  * @return obj
	  */
	 
	 public static JSONObject contoToJSONArray(JSONArray ordinazioni, JSONArray prenotazioni) throws SQLException {
		 JSONObject obj=new JSONObject();
		 
		 try {
			 obj.put("ordinazioni", ordinazioni);
			 obj.put("prenotazioni", prenotazioni);
			 
			 }
		 catch(JSONException e) {
			 e.printStackTrace();
			 }
		 return obj;
		 }
	 
	 
	 /**
	  * Dato un oggetto Comande in input, contenente i prodotti inseriti dal cameriere, restituisce
	  * un array di oggetti JSON, in cui ogni oggetto JSON contiene i dati delle ordinazioni.
	  * @param comande
	  * @return jsonArray
	  */
	 
	 public static JSONArray comandeToJSONArray(Comande comande) throws SQLException{
		 JSONArray jsonArray = new JSONArray();
		 if(comande == null) {
			 return jsonArray;
		 }
		 try {
			 for (Map.Entry<Integer, Carrello> entry : comande.getCarrelli().entrySet()) {
				 JSONObject object = new JSONObject();
				 Utente utente=DBMS.getUtente(entry.getKey());
				 object.put("utente", utenteToJSONArray(utente));
				 object.put("carrello", carrelloToJSONArray(entry.getValue()));
				 
				 jsonArray.put(object);
				 }
			 }
		 catch(JSONException e) {
			 e.printStackTrace();
			 }
		 return jsonArray;
		 }
}