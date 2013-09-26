package dao;

import java.util.List;

import model.Usuario;

public interface IDAOUsuario extends IDaoGenerico<Usuario> {
	
	public List<Usuario> searchUsuario(Usuario usuario);
	public List<Usuario> searchUsuarioCurso(String curso);
	public boolean logar(String usuario, String senha);

}
