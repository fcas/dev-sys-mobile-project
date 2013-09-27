package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import dao.DAOUsuario;

public class ServicoUsuario implements IServicoUsuario{
	
	private static ServicoUsuario singleton = null;
	private DAOUsuario daoUsuario;
	private static final String DATABASE_TABLE = "USUARIOS";
	static Context context; 

	public ServicoUsuario() {
		this.daoUsuario = new DAOUsuario(context);
	}

	public static ServicoUsuario getInstance() {
		if (singleton == null) {
			singleton = new ServicoUsuario();
		}
		return singleton;
	}

	@Override
	public void addUsuario(ContentValues values) {
		daoUsuario.add(DATABASE_TABLE, values);
	}

	@Override
	public void editUsuario(long id, ContentValues values) {
		daoUsuario.edit(DATABASE_TABLE, id, values);
	}

	@Override
	public void deleteUsuario(Usuario usuario, long id) {
		daoUsuario.delete(DATABASE_TABLE, id);
	}

	@Override
	public Cursor listUsuarios(String where, String[] colunas) {
		return daoUsuario.list(DATABASE_TABLE, colunas, where);
	}

	@Override
	public Cursor listUsuariosCurso(String where, String[] colunas) {
		return daoUsuario.list(DATABASE_TABLE, colunas, where);
	}

	@Override
	public boolean logar(String usuario, String senha) {
		return daoUsuario.logar(usuario, senha);
	}

}
