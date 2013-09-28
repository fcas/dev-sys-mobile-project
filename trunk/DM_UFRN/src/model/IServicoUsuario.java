package model;

import android.content.ContentValues;
import android.database.Cursor;

public interface IServicoUsuario {

	public void editUsuario(long id, ContentValues values);

	public void deleteUsuario(Usuario usuario, long id);

	public Cursor listUsuarios(String where, String[] colunas);

	public Cursor listUsuariosCurso(String where, String[] colunas);

	public boolean logar(String usuario, String senha);

	void addUsuario(Usuario usuario);

}
