package servicos; 

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Comentarios;
import model.Lugar;
import model.Tarefas;
import model.Usuario;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.*;

import dao.DAOComentario;
import dao.DAOLugar;
import dao.DAOTarefa;
import dao.DAOUsuario;
import excecoes.ConexaoException;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class ServicoConexao extends Service {
	 
    public static final String IP_SERVIDOR = "http://192.168.0.183:8080";
	//public static final String IP_SERVIDOR = "http://10.9.99.97:8080";

		public static final String CATEGORIA = "servico";
	 JSONParser jsonP;
	 private final IBinder mBinder = new LocalBinder();
	    // Random number generator
	    private final Random mGenerator = new Random();
	 
	     
	    public class LocalBinder extends Binder {
	    	public ServicoConexao getService() {
	            // Return this instance of LocalService so clients can call public methods
	            return ServicoConexao.this;
	        }
	    }	    
	 	    
	 @Override
	 public void onStart(Intent intent, int startId) {
		 Log.w("Servico Startado", "Servico Startado");
     }
	 
	 
	 @Override
	 public IBinder onBind(Intent intent) {
		 return mBinder;
	 }

	 
	
	public String getUsuarios() throws IOException{
		String url = IP_SERVIDOR+"/WebServiceMobile/resources/usuario/getAll";
		URL serverAddress = new 
				URL(url);
				HttpURLConnection connection;
				connection = (HttpURLConnection) serverAddress.openConnection();
				connection.setRequestMethod("GET");
				connection.connect();

				BufferedReader rd = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
				//line = rd.readLine();
				String texto = "";
				texto = rd.readLine();
				Log.w("JSON", texto);
				
				jsonP = new JSONParser();
				List<Usuario> lista = jsonP.JSONToUsuario(texto);
				
				DAOUsuario dao = new DAOUsuario(this);
				dao.atualizarUsuario(lista);
				dao.close();
				connection.disconnect();
				return texto;
	}
	
	public String getComentarios() throws IOException{
		Log.w("X", "Chamando Get Comentarios");
		String url = IP_SERVIDOR+"/WebServiceMobile/resources/comentario/getAll";	
		URL serverAddress = new 
				URL(url);
				HttpURLConnection connection;
				connection = (HttpURLConnection) serverAddress.openConnection();
				connection.setRequestMethod("GET");
				connection.connect();
				
				BufferedReader rd = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
				String texto = "";
				texto = rd.readLine();
				Log.w("JSON", texto);
				jsonP = new JSONParser();
				List<Comentarios> lista = jsonP.JSONToComentarios(texto);
				DAOComentario dao = new DAOComentario(this);
				dao.atualizarComentarios(lista);
				dao.close();
				connection.disconnect();
				return texto;
	}
	
	public String insertUsuario(Usuario u) throws ConexaoException{			
			URL url = null;
			try {
				url = new URL(IP_SERVIDOR+"/WebServiceMobile/resources/usuario/createUsuario");
			} catch (MalformedURLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}	
			HttpPost httpPost = null;
			try {
				httpPost = new HttpPost(url.toURI());
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			}
				
				HttpClient httpClient = new DefaultHttpClient();
				Gson gson = new Gson();
				HttpResponse response; 
				String result = "";
				try{
			    	Log.w("JSON", gson.toJson(u));
			        StringEntity se = new StringEntity(gson.toJson(u), HTTP.UTF_8);
			        httpPost.setHeader("Content-type", "application/json");
			        httpPost.setEntity(se);
			        response = httpClient.execute(httpPost);
			        Log.w("Codigo de Erro", String.valueOf(response.getStatusLine().getStatusCode()));
			        if(response!=null){
			        	BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
			        	result = reader.readLine();
			        }
			    }
			    catch (IOException e) {
			        throw new ConexaoException();
			    }
			    return result;
	}
	
	public String updateUsuario(Usuario u) throws ConexaoException{			
		URL url = null;
		try {
			url = new URL(IP_SERVIDOR+"/WebServiceMobile/resources/usuario/updateUsuario");
		} catch (MalformedURLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}	
			HttpPut httpPut = null;
			String result = "";
			try {
				httpPut = new HttpPut(url.toURI());
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			}
				
				HttpClient httpClient = new DefaultHttpClient();
				Gson gson = new Gson();
				HttpResponse response; 
			    try{
			    	Log.w("JSON", gson.toJson(u));
			        StringEntity se = new StringEntity(gson.toJson(u), HTTP.UTF_8);
			        httpPut.setHeader("Content-type", "application/json");
			        httpPut.setEntity(se);
			        response = httpClient.execute(httpPut);
			        Log.w("Codigo de Erro", String.valueOf(response.getStatusLine().getStatusCode()));
			        if(response!=null){
			        	BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
			        	result = reader.readLine();
                    }
			    }
			    catch (IOException e) {
			        throw new ConexaoException();
			    }
			    return result;
	}
	
	
	public String insertComentario(Comentarios c) throws ConexaoException{			
		URL url; //= new URL(IP_SERVIDOR+"/WebServiceMobile/resources/comentario/createComentario");	
			HttpPost httpPost = null;
			String result = "0";
			try {
				url = new URL(IP_SERVIDOR+"/WebServiceMobile/resources/comentario/createComentario");	
				httpPost = new HttpPost(url.toURI());
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
				HttpClient httpClient = new DefaultHttpClient();
				Gson gson = new Gson();
				HttpResponse response; 
			    try{
			    	Log.w("JSON - "+String.valueOf(c.getLugar().getId_local()), gson.toJson(c));
			        StringEntity se = new StringEntity(gson.toJson(c), HTTP.UTF_8);
			        httpPost.setHeader("Content-type", "application/json");
			        httpPost.setEntity(se);
			        response = httpClient.execute(httpPost);
			        Log.w("Codigo de Erro", String.valueOf(response.getStatusLine().getStatusCode()));
			        if(response!=null){
			        	BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
			        	result = reader.readLine();			        
			        }
			    }
			    catch (IOException e) {
			        throw new ConexaoException();
			    }
			    return result;
	}
	
	public String updateComentario(Comentarios c) throws ConexaoException{			
		URL url = null;
		try {
			url = new URL(IP_SERVIDOR+"/WebServiceMobile/resources/comentario/updateComentario");
		} catch (MalformedURLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}	
			HttpPut httpPut = null;
			try {
				httpPut = new HttpPut(url.toURI());
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			}
				HttpClient httpClient = new DefaultHttpClient();
				Gson gson = new Gson();
				HttpResponse response; 
			    try{
			    	Log.w("JSON-COMENTARIO", gson.toJson(c));
			        StringEntity se = new StringEntity(gson.toJson(c), HTTP.UTF_8);
			        httpPut.setHeader("Content-type", "application/json");
			        httpPut.setEntity(se);
			        response = httpClient.execute(httpPut);
			        Log.w("Codigo de Erro", String.valueOf(response.getStatusLine().getStatusCode()));
			        if(response!=null){
                        InputStream in = response.getEntity().getContent(); //Get the data in the entity
			        }
			    }
			    catch (Exception e) {
			        throw new ConexaoException();
			    }
			    return "";
	}
	
	public String deleteComentario(long id) throws IOException{			
		URL url = new URL(IP_SERVIDOR+"/WebServiceMobile/resources/comentario/deleteComentario/"+String.valueOf(id));	
			HttpDelete httpDelete = null;
			try {
				httpDelete = new HttpDelete(url.toURI());
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			}
				HttpClient httpClient = new DefaultHttpClient();
				Gson gson = new Gson();
				HttpResponse response; 
			    try{
			    	//Log.w("JSON-Tarefa", gson.toJson(t));
			        httpDelete.setHeader("Content-type", "application/json");
			        
			        response = httpClient.execute(httpDelete);
			        Log.w("Codigo de Erro", String.valueOf(response.getStatusLine().getStatusCode()));
			        if(response!=null){
                        InputStream in = response.getEntity().getContent(); //Get the data in the entity
			        }
			    }
			    catch (Exception e) {
			        e.printStackTrace();
			    }
			    return "";
	}	
	
	
	public String insertLugar(Lugar lugar) throws ConexaoException{			
			URL url = null;
			try {
				url = new URL(IP_SERVIDOR+"/WebServiceMobile/resources/lugar/createLugar");
			} catch (MalformedURLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}	
			HttpPost httpPost = null;
			String result = "0";
			try {
				httpPost = new HttpPost(url.toURI());
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			}
				
				HttpClient httpClient = new DefaultHttpClient();
				Gson gson = new Gson();
				HttpResponse response; 
			    try{
			    	Log.w("JSON", gson.toJson(lugar));
			        StringEntity se = new StringEntity(gson.toJson(lugar), HTTP.UTF_8);
			        httpPost.setHeader("Content-type", "application/json");
			        httpPost.setEntity(se);
			        response = httpClient.execute(httpPost);
			        Log.w("Codigo de Erro", String.valueOf(response.getStatusLine().getStatusCode()));
			        if(response!=null){
			        	BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
			        	result = reader.readLine();		
			        }
			    }
			    catch (IOException e) {
			        throw new ConexaoException();
			    }
			    return result;
	}	
	
	public String updateLugar(Lugar lugar){			
			URL url = null;
			try {
				url = new URL(IP_SERVIDOR+"/WebServiceMobile/resources/lugar/updateLugar");
			} catch (MalformedURLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}	
			HttpPut httpPut = null;
			try {
				httpPut = new HttpPut(url.toURI());
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			}
				
				HttpClient httpClient = new DefaultHttpClient();
				Gson gson = new Gson();
				HttpResponse response; 
			    try{
			    	Log.w("JSON", gson.toJson(lugar));
			        StringEntity se = new StringEntity(gson.toJson(lugar), HTTP.UTF_8);
			        httpPut.setHeader("Content-type", "application/json");
			        httpPut.setEntity(se);
			        response = httpClient.execute(httpPut);
			        Log.w("Codigo de Erro", String.valueOf(response.getStatusLine().getStatusCode()));
			        if(response!=null){
                        InputStream in = response.getEntity().getContent(); //Get the data in the entity
			        }
			    }
			    catch (Exception e) {
			        e.printStackTrace();
			    }
			    return "";
	}	
	
	
	public String insertTarefa(Tarefas t) throws ConexaoException{			
			URL url = null;
			try {
				url = new URL(IP_SERVIDOR+"/WebServiceMobile/resources/tarefa/createTarefa");
			} catch (MalformedURLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}	
			HttpPost httpPost = null;
			String result = "0";
			try {
				httpPost = new HttpPost(url.toURI());
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			}
				
				HttpClient httpClient = new DefaultHttpClient();
				Gson gson = new Gson();
				HttpResponse response; 
			    try{
			    	Log.w("JSON-Tarefa", gson.toJson(t));
			        StringEntity se = new StringEntity(gson.toJson(t), HTTP.UTF_8);
			        httpPost.setHeader("Content-type", "application/json");
			        httpPost.setEntity(se);
			        response = httpClient.execute(httpPost);
			        Log.w("Codigo de Erro", String.valueOf(response.getStatusLine().getStatusCode()));
			        if(response!=null){
			        	BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
			        	result = reader.readLine();		
			        }
			    }
			    catch (IOException e) {
			        throw new ConexaoException();
			    }
			    return "";
	}	
	
	public String updateTarefa(Tarefas t) throws ConexaoException{			
		URL url = null;
		String result = "0";
		try {
			url = new URL(IP_SERVIDOR+"/WebServiceMobile/resources/tarefa/updateTarefa");
		} catch (MalformedURLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}	
			HttpPut httpPut = null;
			try {
				httpPut = new HttpPut(url.toURI());
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			}
				
				HttpClient httpClient = new DefaultHttpClient();
				Gson gson = new Gson();
				HttpResponse response; 
			    try{
			    	Log.w("JSON", gson.toJson(t));
			        StringEntity se = new StringEntity(gson.toJson(t), HTTP.UTF_8);
			        httpPut.setHeader("Content-type", "application/json");
			        httpPut.setEntity(se);
			        response = httpClient.execute(httpPut);
			        Log.w("Codigo de Erro", String.valueOf(response.getStatusLine().getStatusCode()));
			        if(response!=null){
			        	BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
			        	result = reader.readLine();		
			        }
			    }
			    catch (Exception e) {
			       throw new ConexaoException();
			    }
			    return result;
	}	
	
	public String deleteTarefa(long id) throws ConexaoException{			
		URL url = null;
		String result = "0";
		try {
			url = new URL(IP_SERVIDOR+"/WebServiceMobile/resources/tarefa/deleteTarefa/"+String.valueOf(id));
		} catch (MalformedURLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}	
			HttpDelete httpDelete = null;
			try {
				httpDelete = new HttpDelete(url.toURI());
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			}
				HttpClient httpClient = new DefaultHttpClient();
				Gson gson = new Gson();
				HttpResponse response; 
			    try{
			    	//Log.w("JSON-Tarefa", gson.toJson(t));
			        httpDelete.setHeader("Content-type", "application/json");
			        
			        response = httpClient.execute(httpDelete);
			        Log.w("Codigo de Erro", String.valueOf(response.getStatusLine().getStatusCode()));
			        if(response!=null){
			        	//BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
			        	//result = reader.readLine();		
			        }
			    }
			    catch (IOException e) {
			       throw new ConexaoException();
			    }
			    return "";
	}	
	
				
	public String getLugares() throws IOException{
		String url = IP_SERVIDOR+"/WebServiceMobile/resources/lugar/getAll";	
		URL serverAddress = new 
				URL(url);
				HttpURLConnection connection;
				connection = (HttpURLConnection) serverAddress.openConnection();
				connection.setRequestMethod("GET");
				connection.connect();
				
				BufferedReader rd = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
				String texto = "";
				texto = rd.readLine();
				Log.w("JSON", texto);
				jsonP = new JSONParser();
				List<Lugar> lista = jsonP.JSONToLugar(texto);
				connection.disconnect();
				
				DAOLugar dao = new DAOLugar(this);
				dao.atualizarLugares(lista);
				dao.close();
				
				return texto;
	}
	
	public String getTarefas() throws IOException{
		Log.w("X", "Chamando Get Tarefas");
		String url = IP_SERVIDOR+"/WebServiceMobile/resources/tarefa/getAll";	
		URL serverAddress = new 
				URL(url);
				HttpURLConnection connection;
				connection = (HttpURLConnection) serverAddress.openConnection();
				connection.setRequestMethod("GET");
				connection.connect();
				
				BufferedReader rd = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
				String texto = "";
				texto = rd.readLine();
				Log.w("JSON", texto);
				jsonP = new JSONParser();
				List<Tarefas> lista = jsonP.JSONToTarefas(texto);
				DAOTarefa dao = new DAOTarefa(this);
				dao.atualizarTarefas(lista);
				dao.close();
				connection.disconnect();
				return texto;
	}
}