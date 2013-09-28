package dao;

import android.content.ContentValues;
import android.database.Cursor;

public interface IDaoGenerico {

	public void add(String table, ContentValues values);

	public void edit(String table, long id, ContentValues values);

	public void delete(String table, long id);

	public Cursor list(String tabela, String[] colunas);
	
	public Cursor listWhere(String tabela, String[] colunas, String where);
	
	public boolean logar(String usuario, String senha);
	
}
