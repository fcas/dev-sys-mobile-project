package model;

import java.util.List;
import dao.DAOUsuario;

public class ServicoUsuario implements IServicoUsuario{
	
	private static ServicoUsuario singleton = null;
	private DAOUsuario daoUsuario;

	public ServicoUsuario() {
		this.daoUsuario = new DAOUsuario();
	}

	public static ServicoUsuario getInstance() {
		if (singleton == null) {
			singleton = new ServicoUsuario();
		}
		return singleton;
	}

	@Override
	public void addUsuario(Usuario usuario) {
		daoUsuario.add(usuario);
	}

	@Override
	public void editUsuario(Usuario usuario) {
		daoUsuario.edit(usuario);
	}

	@Override
	public void deleteUsuario(Usuario usuario) {
		daoUsuario.delete(usuario);
	}

	@Override
	public List<Usuario> searchUsuario(Usuario usuario) {
		return daoUsuario.searchUsuario(usuario);
	}

	@Override
	public List<Usuario> searchUsuarioCurso(String curso) {
		return daoUsuario.searchUsuarioCurso(curso);
	}

	@Override
	public boolean logar(String usuario, String senha) {
		return daoUsuario.logar(usuario, senha);
	}

}
