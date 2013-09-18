package dimap.ufrn.dm;

import java.util.ArrayList;

public interface ModelFachadaInterface {

	//crud lugar
	public void addLugar(Lugar lugar);
	public void editLugar(Lugar lugar);
	public void deleteLugar (Lugar lugar);
	public ArrayList<Lugar> searchLugar(String nome);
	
	//crud usuario
	public void addUsuario(Usuario usuario);
	public void editUsuario(Usuario usuario);
	public void deleteUsuario (Usuario usuario);
	public ArrayList<Usuario> searchUsuario(String nome);
	public ArrayList<Usuario> searchUsuarioCurso(String curso);
	
	//crud comentario
	public void addComentario(Comentario comentario);
	public void editComentario(Comentario comentario);
	public void deleteComentario (Comentario comentario);
	public ArrayList<Comentario> searchComentario(String Lugar);

	//crud tarefa
	public void addTarefas(Tarefas tarefa);
	public void editTarefas(Tarefas tarefas);
	public void deleteTarefas (Tarefas tarefas);
	public ArrayList<Tarefas> searchTarefas(String Lugar);
	
	//Login
	public boolean logar(String usuario, String senha);
	
	
	
	
}
