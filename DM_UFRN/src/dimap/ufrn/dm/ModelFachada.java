package dimap.ufrn.dm;

import java.util.ArrayList;

public class ModelFachada implements ModelFachadaInterface {

	public void addLugar(Lugar lugar){
		
	}
	
	public void editLugar(Lugar lugar){
		
	}
	
	public void deleteLugar (Lugar lugar){
		
	}
	
	public ArrayList<Lugar> searchLugar(String nome){
		return new ArrayList<Lugar>();
	}

	@Override
	public void addUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Usuario> searchUsuario(String nome) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Usuario> searchUsuarioCurso(String curso) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addComentario(Comentario comentario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editComentario(Comentario comentario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteComentario(Comentario comentario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Comentario> searchComentario(String Lugar) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addTarefas(Tarefas tarefa) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editTarefas(Tarefas tarefas) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteTarefas(Tarefas tarefas) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Tarefas> searchTarefas(String Lugar) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean logar(String usuario, String senha) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
