package model;

import java.util.List;

public interface IServicoUsuario {
	
	public void addUsuario(Usuario usuario);
	public void editUsuario(Usuario usuario);
	public void deleteUsuario (Usuario usuario);
	public List<Usuario> searchUsuarioCurso(String curso);
	public boolean logar(String usuario, String senha);
	List<Usuario> searchUsuario(Usuario usuario);

}
