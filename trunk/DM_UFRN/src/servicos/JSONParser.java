package servicos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import model.Comentarios;
import model.Lugar;
import model.Tarefas;
import model.Usuario;

public class JSONParser {
	public JSONParser(){
		
	}
	public List<Usuario> JSONToUsuario(String json){
		Gson gson = new Gson();
		Usuario[] usuarioArray;
		usuarioArray = gson.fromJson(json, Usuario[].class);
		List<Usuario> lista = Arrays.asList(usuarioArray);		
		return lista;
	}
	
	public List<Comentarios> JSONToComentarios(String json){
		Gson gson = new Gson();
		Comentarios[] comentarios;
		comentarios = gson.fromJson(json, Comentarios[].class);
		List<Comentarios> lista = Arrays.asList(comentarios);		
		return lista;
	}
	
	public List<Tarefas> JSONToTarefas(String json){
		Gson gson = new Gson();
		Tarefas[] tarefas;
		tarefas = gson.fromJson(json, Tarefas[].class);
		List<Tarefas> lista = Arrays.asList(tarefas);		
		return lista;
	}
	
	public List<Lugar> JSONToLugar(String json){
		Gson gson = new Gson();
		Lugar[] lugares;
		lugares = gson.fromJson(json, Lugar[].class);
		List<Lugar> lista = Arrays.asList(lugares);		
		return lista;
	}
	
}
