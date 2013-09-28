package dao;

import android.content.Context;
import model.Usuario;

public class DAOUsuario extends DAOGenerico<Usuario> implements IDAOUsuario {

	public DAOUsuario(Context context) {
		super(context, nomeTabela, nomeTabela);
		// TODO Auto-generated constructor stub
	}

	public boolean logar(String usuario, String senha) {
		return false;
	}

}
