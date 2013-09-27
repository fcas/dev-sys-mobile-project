package dao;

import model.Usuario;

public interface IDAOUsuario extends IDaoGenerico<Usuario> {
	
	public boolean logar(String usuario, String senha);

}
