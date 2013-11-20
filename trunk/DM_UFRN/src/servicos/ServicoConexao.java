package servicos; 

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Comentarios;
import model.Lugar;
import model.Tarefas;
import model.Usuario;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.*;

import dao.DAOLugar;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class ServicoConexao extends Service {
	 
	 public static final String IP_SERVIDOR = "http://192.168.0.183:8080";
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
		 Log.w("Servico", "Startado");
		 try {
			getUsuarios();
			getComentarios();
			getLugares();
			getTarefas();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 stopSelf();
	 }
	 
	 
	 @Override
	 public IBinder onBind(Intent intent) {
		 return mBinder;
	 }

	public String getHello() throws IOException{
		String url = "http://192.168.0.183:8080/AnimeList/resources/ola-mundo";
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
				return texto;
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
				
				connection.disconnect();
				return texto;
	}
	
	public String getComentarios() throws IOException{
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
				connection.disconnect();
				return texto;
	}
	
	//NAO FUNCIONA AINDA
	public String insertUsuario(Usuario u) throws IOException{			
				HttpPost httpPost = new HttpPost("http://192.168.0.183:8080/WebServiceMobile/resources/usuario/createUsuario");
				HttpClient httpClient = new DefaultHttpClient();
				
				//String urlParameters = usuarioToJson(u);
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				//params.add(new BasicNameValuePair("usuario", urlParameters));
				
//				/httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		
				HttpResponse response = httpClient.execute(httpPost);
				HttpEntity entity = response.getEntity();
				InputStream is = entity.getContent();
				
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
				List<String> lista = jsonP.JSONToLugar(texto);
				connection.disconnect();
				
				DAOLugar dao = new DAOLugar(this);
				dao.atualizarLugares(lista);
				dao.close();

				
				return texto;
	}
	
	public String getTarefas() throws IOException{
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
				connection.disconnect();
				return texto;
	}
}